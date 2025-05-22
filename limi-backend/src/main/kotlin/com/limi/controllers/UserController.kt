package com.limi.controllers

import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import com.limi.models.User
import com.limi.services.UserService
import io.ktor.http.HttpStatusCode // Adicione esta linha
import com.limi.validation.validateForCreation

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

        post ("/cadastro"){
            val novoUsuario = call.receive<User>()
            novoUsuario.validateForCreation()
            val criado = userService.adicionarUser(novoUsuario)
            call.respond(HttpStatusCode.Created, criado)
        }
        put("/users/{id}") {
            val id = call.parameters["id"]?.toIntOrNull()
                ?: return@put call.respond(HttpStatusCode.BadRequest, "ID inválido")

            val user = call.receive<User>()
            val atualizado = userService.atualizarUser(id, user)
            call.respond(atualizado)
        }

    }
}
