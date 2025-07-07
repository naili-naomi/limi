package com.limi.controllers

import com.limi.services.LikeService
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.likeRoutes(likeService: LikeService) {
    authenticate("auth-jwt") {
        route("/reviews/{reviewId}/like") {
            post {
                val principal = call.principal<JWTPrincipal>()
                val userId = principal!!.payload.getClaim("userId").asInt()
                val reviewId = call.parameters["reviewId"]?.toIntOrNull()

                if (reviewId == null) {
                    call.respond(HttpStatusCode.BadRequest, "Invalid review ID")
                    return@post
                }

                likeService.addLike(userId, reviewId)
                call.respond(HttpStatusCode.OK)
            }

            delete {
                val principal = call.principal<JWTPrincipal>()
                val userId = principal!!.payload.getClaim("userId").asInt()
                val reviewId = call.parameters["reviewId"]?.toIntOrNull()

                if (reviewId == null) {
                    call.respond(HttpStatusCode.BadRequest, "Invalid review ID")
                    return@delete
                }

                likeService.removeLike(userId, reviewId)
                call.respond(HttpStatusCode.OK)
            }
        }
    }
}
