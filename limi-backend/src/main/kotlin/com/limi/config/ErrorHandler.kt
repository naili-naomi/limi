package com.limi.config

import com.limi.exceptions.ValidationException
import com.limi.exceptions.NotFoundException
import com.limi.exceptions.AuthenticationException
import io.ktor.server.application.*
import io.ktor.server.plugins.statuspages.*
import io.ktor.server.response.*
import io.ktor.http.*

fun Application.configureErrorHandling() {
    install(StatusPages) {
        exception<ValidationException> { call, cause ->
            call.respond(
                HttpStatusCode.UnprocessableEntity,
                mapOf("errors" to cause.errors)
            )
        }
        exception<IllegalArgumentException> { call, cause ->
            call.respond(
                HttpStatusCode.BadRequest,
                mapOf("error" to (cause.message ?: "Dados inválidos"))
            )
        }
        exception<NotFoundException> { call, cause ->
            call.respond(
                HttpStatusCode.NotFound,
                mapOf("error" to (cause.message ?: "Recurso não encontrado"))
            )
        }
        exception<AuthenticationException> { call, cause ->
            call.respond(
                HttpStatusCode.Unauthorized,
                mapOf("error" to (cause.message ?: "Não autorizado"))
            )
        }
        exception<Exception> { call, cause ->
            call.respond(HttpStatusCode.InternalServerError, mapOf("error" to "Erro interno"))
            println("ERRO INTERNO DETALHADO: ${cause.stackTraceToString()}")
        }
    }
}