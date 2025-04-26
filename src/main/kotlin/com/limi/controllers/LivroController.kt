package com.limi.controllers  // Pacote corrigido para manter a estrutura

import com.limi.models.Livro
import com.limi.services.LivroService
import io.ktor.server.application.*
import io.ktor.http.HttpStatusCode
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

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
                } ?: call.respond(HttpStatusCode.NotFound, "Livro não encontrado")
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
    }
}