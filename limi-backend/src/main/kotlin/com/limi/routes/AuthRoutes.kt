package com.limi.routes

import com.limi.routes.UserLoginRequest
import com.limi.repositories.UserRepository
import com.limi.config.JwtConfig
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.http.*
import io.ktor.server.routing.*



data class UserLoginRequest(val email: String, val senha: String)

fun Route.authRoutes(userRepository: UserRepository) {

    post("/login") {
        val credentials = call.receive<UserLoginRequest>()

        val user = userRepository.buscarPorEmail(credentials.email)
        if (user == null || user.senha != credentials.senha) {
            call.respond(HttpStatusCode.Unauthorized, "Credenciais inválidas")
            return@post
        }

        val token = JwtConfig.generateToken(user.id)
        call.respond(mapOf("token" to token))
    }
}
