package com.limi.controllers

import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import com.limi.models.Autor
import services.AutorService

fun Route.autorRoutes(autorService: AutorService) {

    route("/autores") {

        get {
            call.respond(autorService.listarAutores())
        }

        post {
            val novoAutor = call.receive<Autor>()
            autorService.adicionarAutor(novoAutor)
            call.respondText("Autor adicionado!")
        }
    }
}
