package com.limi.controllers

import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import com.limi.models.User
import com.limi.services.UserService
import io.ktor.http.HttpStatusCode // Adicione esta linha
import com.limi.validation.validateForCreation
import com.limi.DTO.UserLoginRequest
import com.limi.exceptions.AuthenticationException
import com.limi.exceptions.ValidationException
import com.limi.DTO.UserResponse
import com.limi.DTO.ErrorResponse // Adicione esta linha


fun Route.userRoutes(userService: UserService) {

    route("/usuarios") {

        get {
            call.respond(userService.listarUsers())
        }

        get("/{email}") {
            val email = call.parameters["email"] ?: return@get call.respondText("Email inválido")
            val usuario = userService.buscarPorEmail(email)
            if (usuario != null) {
                call.respond(UserResponse.of(usuario))
            } else {
                call.respondText("Usuário não encontrado")
            }
        }

        post ("/cadastro"){
            try {
                val novoUsuario = call.receive<User>()
                novoUsuario.validateForCreation()
                val criado = userService.adicionarUser(novoUsuario)
                call.respond(HttpStatusCode.Created, UserResponse.of(criado))
            } catch (e: ValidationException) {
                println("DEBUG: ValidationException capturada. Mensagem: ${e.message}")
                call.respond(HttpStatusCode.BadRequest, ErrorResponse(e.message ?: "Erro de validação"))
            } catch (e: Exception) {
                e.printStackTrace()
                call.respond(HttpStatusCode.InternalServerError, ErrorResponse(e.message ?: "Erro interno do servidor"))
            }
        }
        post("/login") {
            val loginRequest = call.receive<UserLoginRequest>()

            try {
                val token = userService.login(loginRequest)
                val usuario = userService.buscarPorEmail(loginRequest.email)
                    ?: throw AuthenticationException("Usuário não encontrado após login")

                call.respond(HttpStatusCode.OK, mapOf("token" to token, "nome" to usuario.nome))
            } catch (e: AuthenticationException) {
                call.respond(HttpStatusCode.Unauthorized, ErrorResponse(e.message ?: "Email ou senha inválidos"))
            } catch (e: Exception) {
                e.printStackTrace() // <- MOSTRA O ERRO REAL NO TESTE
                call.respond(HttpStatusCode.InternalServerError, ErrorResponse(e.message ?: "Erro interno do servidor"))
            }
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
