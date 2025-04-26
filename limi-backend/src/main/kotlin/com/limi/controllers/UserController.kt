package com.limi.controllers

import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import com.limi.models.User
import com.limi.services.UserService

fun Route.userRoutes(userService: UserService) {

    route("/usuarios") {

        get {
            call.respond(userService.listarUsers())
        }

        get("/{email}") {
            val email = call.parameters["email"] ?: return@get call.respondText("Email inválido")
            val usuario = userService.buscarPorEmail(email)
            if (usuario != null) {
                call.respond(usuario)
            } else {
                call.respondText("Usuário não encontrado")
            }
        }

        post {
            val novoUsuario = call.receive<User>()
            userService.adicionarUser(novoUsuario)
            call.respondText("Usuário cadastrado!")
        }
    }
}
