package com.limi.controllers

import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import com.limi.models.Review
import services.ReviewService

fun Route.reviewRoutes(reviewService: ReviewService) {

    route("/reviews") {

        get {
            call.respond(reviewService.listarReviews())
        }

        get("/livro/{livro}") {
            val livro = call.parameters["livro"] ?: return@get call.respondText("Livro inválido")
            val reviews = reviewService.buscarReviewsPorLivro(livro)
            call.respond(reviews)
        }

        post {
            val novaReview = call.receive<Review>()
            reviewService.adicionarReview(novaReview)
            call.respondText("Review adicionada!")
        }
    }
}
