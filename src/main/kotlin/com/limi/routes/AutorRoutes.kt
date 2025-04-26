package com.limi.routes

import com.limi.models.Autor
import io.ktor.server.application.*
import io.ktor.server.routing.*
import io.ktor.server.response.*
import io.ktor.server.request.*
import io.ktor.http.*

val autores = mutableListOf<Autor>()

fun Route.autorRoutes() {
    route("/autores") {
        get {
            call.respond(autores)
        }

        post {
            val novoAutor = call.receive<Autor>()
            autores.add(novoAutor)
            call.respond(HttpStatusCode.Created, novoAutor)
        }
    }
}
