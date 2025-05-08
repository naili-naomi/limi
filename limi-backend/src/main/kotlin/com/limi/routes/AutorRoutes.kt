package com.limi.routes

import com.limi.models.Autor
import com.limi.services.AutorService
import io.ktor.server.application.*
import io.ktor.server.routing.*
import io.ktor.server.response.*
import io.ktor.server.request.*
import io.ktor.http.*

//val autores = mutableListOf<Autor>()

fun Route.autorRoutes(autorService: AutorService) {
    route("/autores") {
        get {
            call.respond(autorService.listarAutores())
        }

        post {
            val novoAutor = call.receive<Autor>()
            autorService.adicionarAutor(novoAutor)
            call.respond(HttpStatusCode.Created, novoAutor)
        }
    }
}
