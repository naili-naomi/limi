package com.limi.controllers

import com.limi.models.Review
import com.limi.services.ReviewService
import com.limi.services.UserService
import io.ktor.http.* 
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import com.limi.DTO.ReviewCreateRequest
import com.limi.DTO.ReviewUpdateRequest

fun Route.reviewController(reviewService: ReviewService, userService: UserService) {
    route("/api/livros/{livroId}/reviews") {
        get {
            val livroId = call.parameters["livroId"]?.toIntOrNull()
            if (livroId == null) {
                call.respond(HttpStatusCode.BadRequest, "Invalid book ID")
                return@get
            }
            val reviews = reviewService.getReviewsByBookId(livroId)
            call.respond(reviews)
        }
        authenticate("auth-jwt") {
            post {
                val livroId = call.parameters["livroId"]?.toIntOrNull()
                if (livroId == null) {
                    call.respond(HttpStatusCode.BadRequest, "Invalid book ID")
                    return@post
                }
                val principal = call.principal<JWTPrincipal>()
                println("ReviewController: Principal: $principal")
                val userId = principal!!.payload.getClaim("userId").asInt()
                println("ReviewController: Extracted userId: $userId")
                val user = userService.getUserById(userId)
                println("ReviewController: User from service: $user")
                if (user == null) {
                    call.respond(HttpStatusCode.Unauthorized, "User not found")
                    return@post
                }

                val request = call.receive<ReviewCreateRequest>()
                val review = Review(
                    id = null,
                    livroId = livroId,
                    userId = user.id,
                    comentario = request.comentario,
                    nota = request.nota
                )
                val createdReview = reviewService.addReview(review)
                call.respond(HttpStatusCode.Created, createdReview)

            }

            put("/{reviewId}") {
                val reviewId = call.parameters["reviewId"]?.toIntOrNull()
                if (reviewId == null) {
                    call.respond(HttpStatusCode.BadRequest, "Invalid review ID")
                    return@put
                }
                val principal = call.principal<JWTPrincipal>()
                val authenticatedUserId = principal!!.payload.getClaim("userId").asInt()

                val existingReview = reviewService.getReviewById(reviewId)
                if (existingReview == null) {
                    call.respond(HttpStatusCode.NotFound, "Review not found")
                    return@put
                }

                if (existingReview.userId != authenticatedUserId) {
                    call.respond(HttpStatusCode.Forbidden, "You are not authorized to update this review")
                    return@put
                }

                val request = call.receive<ReviewUpdateRequest>()
                val reviewToUpdate = Review(
                    id = reviewId,
                    livroId = existingReview.livroId,
                    userId = authenticatedUserId,
                    comentario = request.comentario,
                    nota = request.nota
                )
                val updatedReview = reviewService.updateReview(reviewId, reviewToUpdate)
                if (updatedReview != null) {
                    call.respond(updatedReview)
                } else {
                    call.respond(HttpStatusCode.InternalServerError, "Failed to update review")
                }
            }

            delete("/{reviewId}") {
                val reviewId = call.parameters["reviewId"]?.toIntOrNull()
                if (reviewId == null) {
                    call.respond(HttpStatusCode.BadRequest, "Invalid review ID")
                    return@delete
                }
                val principal = call.principal<JWTPrincipal>()
                val authenticatedUserId = principal!!.payload.getClaim("userId").asInt()

                val existingReview = reviewService.getReviewById(reviewId)
                if (existingReview == null) {
                    call.respond(HttpStatusCode.NotFound, "Review not found")
                    return@delete
                }

                if (existingReview.userId != authenticatedUserId) {
                    call.respond(HttpStatusCode.Forbidden, "You are not authorized to delete this review")
                    return@delete
                }

                val deleted = reviewService.deleteReview(reviewId)
                if (deleted) {
                    call.respond(HttpStatusCode.NoContent)
                } else {
                    call.respond(HttpStatusCode.InternalServerError, "Failed to delete review")
                }
            }
        }
    }
}