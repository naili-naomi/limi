package com.limi.routes

import com.limi.models.Review
import com.limi.services.ReviewService
import io.ktor.server.application.*
import io.ktor.http.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.reviewRoutes(reviewService: ReviewService) {
    route("/reviews") {

        get("/{livroId}") {
            val livroId = call.parameters["livroId"]?.toIntOrNull()
            if (livroId == null) {
                call.respond(HttpStatusCode.BadRequest, "ID do livro inválido")
                return@get
            }
            val reviews = reviewService.buscarReviewsPorLivro(livroId)
            call.respond(reviews)
        }

        post {
            val novaReview = call.receive<Review>()
            val reviewSalva = reviewService.adicionarReview(novaReview)
            call.respond(HttpStatusCode.Created, reviewSalva)
        }

        put("/{id}") {
            val id = call.parameters["id"]?.toIntOrNull()
            val novaReview = call.receive<Review>()

            if (id == null) {
                call.respond(HttpStatusCode.BadRequest, "ID inválido")
                return@put
            }

            val reviewAtualizada = reviewService.atualizarReview(id, novaReview.comentario, novaReview.nota)
            reviewAtualizada?.let {
                call.respond(HttpStatusCode.OK, it)
            } ?: call.respond(HttpStatusCode.NotFound, "Review não encontrada")
        }
    }
}