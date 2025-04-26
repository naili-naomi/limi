package com.limi

import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import io.ktor.server.plugins.contentnegotiation.*
import io.ktor.serialization.kotlinx.json.*
import io.ktor.server.routing.*
import io.ktor.server.plugins.cors.routing.*
import io.ktor.http.*

import com.limi.controllers.*
import com.limi.services.*
import controllers.livroRoutes
import services.*

fun main() {
    embeddedServer(Netty, port = 8080, host = "0.0.0.0") {
        module()
    }.start(wait = true)
}

fun Application.module() {
    // Plugins
    install(ContentNegotiation) {
        json()
    }

    install(CORS) {
        anyHost() // ajuste para produção depois
        allowMethod(HttpMethod.Get)
        allowMethod(HttpMethod.Post)
        allowMethod(HttpMethod.Put)
        allowMethod(HttpMethod.Delete)
        allowHeader(HttpHeaders.ContentType)
    }

    // Instância dos serviços (simples, ainda sem banco)
    val catalogoService = CatalogoService()
    val livroService = LivroService()
    val userService = UserService()
    val reviewService = ReviewService()
    val autorService = AutorService()

    // Registro das rotas dos controllers
    routing {
        catalogoRoutes(catalogoService)
        livroRoutes(livroService)
        userRoutes(userService)
        reviewRoutes(reviewService)
        autorRoutes(autorService)
    }
}
