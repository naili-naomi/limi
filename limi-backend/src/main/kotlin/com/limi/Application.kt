package com.limi

import com.limi.config.DatabaseSeed
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
import com.limi.repositories.AutorRepository
import com.limi.repositories.UserRepository
import com.limi.controllers.* // Import corrigido
import com.limi.services.* // Import corrigido
import com.limi.config.configureErrorHandling
import com.limi.services.ExternalBookService
import io.ktor.client.*
import io.ktor.client.engine.cio.*
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation as ClientContentNegotiation
import io.ktor.serialization.kotlinx.json.json
import io.ktor.server.auth.*
import com.limi.config.JwtConfig
import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import io.ktor.server.auth.jwt.*


fun main() {
    embeddedServer(Netty, port = 8080, host = "0.0.0.0",
        module = Application::module)
    .start(wait = true)
}


fun Application.configureSecurity() {
    install(Authentication) {
        jwt("auth-jwt") {
            JwtConfig.configureJwt(this)
        }
    }
}
fun Application.module() {
    val client = HttpClient(CIO) {
        install(ClientContentNegotiation) {
            json()
        }
    }

    // Inicialização do banco
    DatabaseFactory.init(
        client = client,
        url = "jdbc:sqlite:/home/naili/IdeaProjects/limi/catalogo.db",
        driver = "org.sqlite.JDBC"
    )

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

    configureSecurity()

    // Repositórios
    val livroRepository = LivroRepository()
    val reviewRepository = ReviewRepository()
    val userRepository = UserRepository()
    val autorRepository = AutorRepository()
    val externalBookService = ExternalBookService(client)

    // Serviços com injeção de dependência correta
    val catalogoService = CatalogoService(livroRepository)
    val livroService = LivroService(livroRepository, externalBookService)
    val reviewService = ReviewService(reviewRepository)
    val userService = UserService(userRepository)
    val autorService = AutorService(autorRepository)

    // Rotas
    routing {
        catalogoRoutes(catalogoService)
        livroRoutes(livroService)
        reviewRoutes(reviewService)
        userRoutes(userService)
        autorRoutes(autorService)

        authenticate("auth-jwt") {
            reviewRoutes(ReviewService(ReviewRepository()))
            livroRoutes(LivroService(LivroRepository(), ExternalBookService(client)))
            // qualquer outra rota que deva exigir login
        }


      //  configureRoutes(externalBookService)
    }

    configureErrorHandling()
}