package com.limi

import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import io.ktor.server.plugins.contentnegotiation.*
import io.ktor.serialization.kotlinx.json.*
import io.ktor.server.routing.*
import io.ktor.server.plugins.cors.routing.*
import io.ktor.http.*
import com.limi.config.DatabaseFactory
import com.limi.repositories.LivroRepository
import com.limi.repositories.ReviewRepository
import com.limi.controllers.* // Import corrigido
import com.limi.services.* // Import corrigido

fun main() {
    embeddedServer(Netty, port = 8080, host = "0.0.0.0") {
        module()
    }.start(wait = true)
}

fun Application.module() {
    // Inicialização do banco
    DatabaseFactory.init()

    // Plugins
    install(ContentNegotiation) { json() }

    install(CORS) {
        anyHost()
        allowMethod(HttpMethod.Get)
        allowMethod(HttpMethod.Post)
        allowMethod(HttpMethod.Put)
        allowMethod(HttpMethod.Delete)
        allowHeader(HttpHeaders.ContentType)
    }

    // Repositórios
    val livroRepository = LivroRepository()
    val reviewRepository = ReviewRepository()

    // Serviços com injeção de dependência correta
    val catalogoService = CatalogoService(livroRepository)
    val livroService = LivroService(livroRepository)
    val reviewService = ReviewService(reviewRepository)

    // Services que parecem estar incompletos (ajustar depois):
    val userService = UserService() // Precisa de UserRepository?
    val autorService = AutorService() // Precisa de AutorRepository?

    // Rotas
    routing {
        catalogoRoutes(catalogoService)
        livroRoutes(livroService)
        reviewRoutes(reviewService)
        //userRoutes(userService)
       // autorRoutes(autorService)
    }
}