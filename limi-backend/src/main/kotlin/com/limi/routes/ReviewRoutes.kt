package com.limi.routes

import com.limi.models.Review
import io.ktor.server.application.*
import io.ktor.server.routing.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.http.*

val reviews = mutableListOf<Review>()

fun Route.reviewRoutes() {
    route("/reviews") {
        get {
            call.respond(reviews)
        }

        get("/{livro}") {
            val livro = call.parameters["livro"] ?: ""
            val filtradas = reviews.filter { it.livroId == livro }
            call.respond(filtradas)
        }

        post {
            val novaReview = call.receive<Review>()
            reviews.add(novaReview)
            call.respond(HttpStatusCode.Created, novaReview)
        }

        put("/{id}") {
            val id = call.parameters["id"]?.toIntOrNull()
            val nova = call.receive<Review>()

            val index = reviews.indexOfFirst { it.id == id && it.userId == nova.userId }
            if (index != -1) {
                reviews[index] = nova
                call.respond(HttpStatusCode.OK, nova)
            } else {
                call.respond(HttpStatusCode.NotFound, "Review não encontrada ou não pertence ao usuário")
            }
        }
    }
}
