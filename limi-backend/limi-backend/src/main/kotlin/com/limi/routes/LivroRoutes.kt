package com.limi.routes

import com.limi.models.Livro
import io.ktor.server.application.*
import io.ktor.server.routing.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.http.*

val livros = mutableListOf<Livro>()

fun Route.livroRoutes() {
    route("/livros") {
        get {
            call.respond(livros)
        }

        get("/{id}") {
            val id = call.parameters["id"]?.toIntOrNull()
            val livro = livros.find { it.id == id }

            if (livro != null) {
                call.respond(livro)
            } else {
                call.respond(HttpStatusCode.NotFound, "Livro não encontrado")
            }
        }

        post {
            val novoLivro = call.receive<Livro>()
            livros.add(novoLivro)
            call.respond(HttpStatusCode.Created, novoLivro)
        }

        put("/{id}") {
            val id = call.parameters["id"]?.toIntOrNull()
            val atualizado = call.receive<Livro>()
            val index = livros.indexOfFirst { it.id == id }

            if (index != -1) {
                livros[index] = atualizado
                call.respond(HttpStatusCode.OK, atualizado)
            } else {
                call.respond(HttpStatusCode.NotFound, "Livro não encontrado para atualizar")
            }
        }

        delete("/{id}") {
            val id = call.parameters["id"]?.toIntOrNull()
            val removido = livros.removeIf { it.id == id }

            if (removido) {
                call.respond(HttpStatusCode.OK, "Livro removido com sucesso")
            } else {
                call.respond(HttpStatusCode.NotFound, "Livro não encontrado")
            }
        }
    }
}
