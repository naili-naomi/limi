package com.limi.controllers  // Pacote corrigido para manter a estrutura

import com.limi.models.Livro
import com.limi.services.LivroService
import io.ktor.server.application.*
import io.ktor.http.HttpStatusCode
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.JWTPrincipal

fun Route.livroRoutes(livroService: LivroService) {

    route("/livros") {

        get {
            try {
                val livros = livroService.listarLivros()
                call.respond(livros)
            } catch (e: Exception) {
                call.respond(HttpStatusCode.InternalServerError, "Erro ao buscar livros")
            }
        }

        get("/{id}") {
            val id = call.parameters["id"]?.toIntOrNull()
            if (id == null) {
                call.respond(HttpStatusCode.BadRequest, "ID inválido")
                return@get
            }

            try {
                val livro = livroService.buscarLivroPorId(id)
                livro?.let {
                    call.respond(it)
                } ?: call.respond(HttpStatusCode.NotFound, "Livro com ID $id não encontrado")
            } catch (e: Exception) {
                call.respond(HttpStatusCode.InternalServerError, "Erro na busca")
            }
        }

        post {
            try {
                val novoLivro = call.receive<Livro>()
                val livroSalvo = livroService.adicionarLivro(novoLivro)
                call.respond(HttpStatusCode.Created, livroSalvo)
            } catch (e: Exception) {
                call.respond(
                    HttpStatusCode.InternalServerError,
                    "Erro ao adicionar livro: ${e.localizedMessage}"
                )
            }
        }

        authenticate("auth-jwt") {
            delete("/{id}") {
                val principal = call.principal<JWTPrincipal>()
                val userEmail = principal?.payload?.getClaim("email")?.asString()

                if (userEmail != "adminlimi@gmail.com") {
                    call.respond(HttpStatusCode.Forbidden, "Acesso negado. Somente administradores podem deletar livros.")
                    return@delete
                }

                val id = call.parameters["id"]?.toIntOrNull()
                if (id == null) {
                    call.respond(HttpStatusCode.BadRequest, "ID inválido")
                    return@delete
                }

                try {
                    val deleted = livroService.deletarLivro(id)
                    if (deleted) {
                        call.respond(HttpStatusCode.NoContent)
                    } else {
                        call.respond(HttpStatusCode.NotFound, "Livro não encontrado")
                    }
                } catch (e: Exception) {
                    call.respond(HttpStatusCode.InternalServerError, "Erro ao deletar livro: ${e.localizedMessage}")
                }
            }
        }
    }
}