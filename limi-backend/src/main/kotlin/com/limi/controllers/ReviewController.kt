package com.limi.controllers

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import com.limi.models.Review
import com.limi.services.ReviewService

fun Route.reviewRoutes(reviewService: ReviewService) {
   // Redundância aqui... ajeitar depois
    route("/reviews") {

        get("/livro/{livroId}") {
            val livroId = call.parameters["livroId"]?.toIntOrNull()
                ?: return@get call.respondText("ID de livro inválido", status = HttpStatusCode.BadRequest)

            val reviews = reviewService.buscarReviewsPorLivro(livroId)
            call.respond(reviews)
        }

        post {
            val novaReview = call.receive<Review>()
            reviewService.adicionarReview(novaReview)
            call.respondText("Review adicionada!", status = HttpStatusCode.Created)
        }
    }
}
