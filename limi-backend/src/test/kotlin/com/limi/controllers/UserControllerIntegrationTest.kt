package com.limi.controllers

import com.limi.config.DatabaseFactory
import com.limi.models.UserLoginRequest
import com.limi.repositories.UserRepository
import com.limi.services.UserService
import io.ktor.client.* 
import io.ktor.client.engine.cio.* 
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation as ClientContentNegotiation
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.json
import io.ktor.server.application.*
import io.ktor.server.config.*
import io.ktor.server.plugins.contentnegotiation.*
import io.ktor.server.plugins.statuspages.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import io.ktor.server.testing.*
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.jsonObject
import kotlinx.serialization.json.jsonPrimitive
import kotlinx.serialization.json.int
import org.junit.jupiter.api.Test
import kotlin.test.*

class UserControllerIntegrationTest {

    private fun Application.testModule() {
        val client = HttpClient(CIO) {
            install(ClientContentNegotiation) {
                json()
            }
        }
        DatabaseFactory.init(
            client = client,
            url = "jdbc:h2:mem:test;DB_CLOSE_DELAY=-1", 
            driver = "org.h2.Driver"
        )

        install(ContentNegotiation) {
            json(Json { ignoreUnknownKeys = true })
        }
        install(StatusPages) {
            exception<com.limi.exceptions.ValidationException> { call, cause ->
                call.respond(HttpStatusCode.UnprocessableEntity, mapOf("errors" to cause.errors))
            }
            exception<com.limi.exceptions.NotFoundException> { call, cause ->
                call.respond(HttpStatusCode.NotFound, mapOf("error" to (cause.message ?: "Recurso não encontrado")))
            }
        }

        val repo = UserRepository()
        val svc  = UserService(repo)
        routing {
            userRoutes(svc)
        }
    }

    @Test
    fun `cadastro válido retorna 201 e não expõe senha`() = testApplication {
        environment {
            config = MapApplicationConfig("ktor.development" to "false")
        }
        application { testModule() }

        val resp = client.post("/usuarios/cadastro") {
            contentType(ContentType.Application.Json)
            setBody("""
                {
                  "id": 0,
                  "nome": "Ana Silva",
                  "username": "anasilva",
                  "email": "ana@example.com",
                  "senha": "minhasenha"
                }
            """.trimIndent())
        }

        assertEquals(HttpStatusCode.Created, resp.status)
        assertFalse(resp.bodyAsText().contains("senha"))
    }

    @Test
    fun `cadastro duplicado retorna 422`() = testApplication {
        environment {
            config = MapApplicationConfig("ktor.development" to "false")
        }
        application { testModule() }

        val body = """{
          "id":0,
          "nome":"Ana",
          "username":"ana",
          "email":"ana@ex.com",
          "senha":"123456"
        }"""
        client.post("/usuarios/cadastro") {
            contentType(ContentType.Application.Json); setBody(body)
        }
        val resp2 = client.post("/usuarios/cadastro") {
            contentType(ContentType.Application.Json); setBody(body)
        }
        assertEquals(HttpStatusCode.UnprocessableEntity, resp2.status)
        assertTrue(resp2.bodyAsText().contains("email"))
    }

    @Test
    fun `login válido retorna token e nome`() = testApplication {
        environment {
            config = MapApplicationConfig("ktor.development" to "false")
        }
        application { testModule() }

        client.post("/usuarios/cadastro") {
            contentType(ContentType.Application.Json)
            setBody("""{
            "id":0,
            "nome":"Carlos",
            "username":"carlos01",
            "email":"carlos@teste.com",
            "senha":"senha123"
        }""")
        }

        val loginResp = client.post("/usuarios/login") {
            contentType(ContentType.Application.Json)
            setBody("""{"email":"carlos@teste.com","senha":"senha123"}""")
        }

        assertEquals(HttpStatusCode.OK, loginResp.status)

        val body = loginResp.bodyAsText()
        assertTrue(body.contains("token"), "Resposta não contém token")
        assertTrue(body.contains("Carlos"), "Resposta não contém o nome do usuário")
    }


    @Test
    fun `login inválido retorna 401`() = testApplication {
        environment {
            config = MapApplicationConfig("ktor.development" to "false")
        }
        application { testModule() }

        val resp = client.post("/usuarios/login") {
            contentType(ContentType.Application.Json)
            setBody("""{"email":"no@one","senha":"123456"}""")
        }
        assertEquals(HttpStatusCode.Unauthorized, resp.status)
    }

    @Test
    fun `atualizacao valida retorna 200 e atualiza os dados`() = testApplication {
        environment {
            config = MapApplicationConfig("ktor.development" to "false")
        }
        application { testModule() }

        val cadastro = client.post("/usuarios/cadastro") {
            contentType(ContentType.Application.Json)
            setBody("""
      {"id":0,"nome":"José","username":"jose01","email":"jose@ex.com","senha":"senha123"}
    """.trimIndent())
        }
        assertEquals(HttpStatusCode.Created, cadastro.status)

        val cadastroJson = Json.parseToJsonElement(cadastro.bodyAsText()).jsonObject
        val userId = cadastroJson["id"]!!.jsonPrimitive.int

        val respPut = client.put("/usuarios/users/$userId") {
            contentType(ContentType.Application.Json)
            setBody("""
      {"id":$userId,"nome":"José Atualizado","username":"jose01","email":"jose@ex.com","senha":"novasenha123"}
    """.trimIndent())
        }
        assertEquals(HttpStatusCode.OK, respPut.status)

        val updatedJson = Json.parseToJsonElement(respPut.bodyAsText()).jsonObject
        assertEquals("José Atualizado", updatedJson["nome"]!!.jsonPrimitive.content)
        assertEquals("jose@ex.com", updatedJson["email"]!!.jsonPrimitive.content)
    }


    @Test
    fun `atualizacao com email duplicado retorna 422`() = testApplication {
        environment {
            config = MapApplicationConfig("ktor.development" to "false")
        }
        application { testModule() }

        val body1 = """{"id":0,"nome":"Ana","username":"ana01","email":"ana@ex.com","senha":"senha123"}"""
        val body2 = """{"id":0,"nome":"Bruno","username":"bruno01","email":"bruno@ex.com","senha":"senha123"}"""
        client.post("/usuarios/cadastro") {
            contentType(ContentType.Application.Json); setBody(body1)
        }
        val r2 = client.post("/usuarios/cadastro") {
            contentType(ContentType.Application.Json); setBody(body2)
        }
        val id2 = Json
            .parseToJsonElement(r2.bodyAsText())
            .jsonObject["id"]!!
            .jsonPrimitive
            .int

        val respPut = client.put("/usuarios/users/$id2") {
            contentType(ContentType.Application.Json)
            setBody("""
                {"id":$id2,"nome":"Bruno","username":"bruno01","email":"ana@ex.com","senha":"outrasenha"}
            """.trimIndent())
        }

        assertEquals(HttpStatusCode.UnprocessableEntity, respPut.status)
        assertTrue(respPut.bodyAsText().contains("email"))
    }

    @Test
    fun `atualizacao de usuario inexistente retorna 404`() = testApplication {
        environment {
            config = MapApplicationConfig("ktor.development" to "false")
        }
        application { testModule() }

        val respPut = client.put("/usuarios/users/9999") {
            contentType(ContentType.Application.Json)
            setBody("""
                {"id":9999,"nome":"Inexistente","username":"none","email":"none@ex.com","senha":"senha123"}
            """.trimIndent())
        }

        assertEquals(HttpStatusCode.NotFound, respPut.status)
    }
}