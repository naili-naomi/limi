package com.limi.routes

import com.limi.models.Book
import com.limi.models.Catalogo
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import io.ktor.http.*

fun Route.bookRoutes() {

    route("/books") {

        // GET /books - lista todos os livros
        get {
            call.respond(Catalogo.listarBooks())
        }

        // GET /books/{id} - obter livro por ID
        get("{id}") {
            val id = call.parameters["id"]?.toIntOrNull()
            val livro = id?.let { Catalogo.getBookPorId(it) }

            if (livro != null) {
                call.respond(livro)
            } else {
                call.respond(HttpStatusCode.NotFound, "Book não encontrado")
            }
        }

        // POST /books - adicionar novo livro
        post {
            val novoBook = call.receive<Book>()
            Catalogo.adicionarBook(novoBook)
            call.respond(HttpStatusCode.Created, novoBook)
        }

        // PUT /books/{id} - atualizar um livro
        put("{id}") {
            val id = call.parameters["id"]?.toIntOrNull()
            val livroAtualizado = call.receive<Book>()

            if (id != null && Catalogo.atualizarBook(id, livroAtualizado)) {
                call.respond(HttpStatusCode.OK, livroAtualizado)
            } else {
                call.respond(HttpStatusCode.NotFound, "Book não encontrado")
            }
        }

        // DELETE /books/{id} - remover livro
        delete("{id}") {
            val id = call.parameters["id"]?.toIntOrNull()

            if (id != null && Catalogo.removerBook(id)) {
                call.respond(HttpStatusCode.OK, "Book removido com sucesso")
            } else {
                call.respond(HttpStatusCode.NotFound, "Book não encontrado")
            }
        }

        // GET /books/search?titulo=...
        get("/search") {
            val titulo = call.request.queryParameters["titulo"]
            val autor = call.request.queryParameters["autor"]

            val resultado = when {
                titulo != null -> Catalogo.pesquisarBook(titulo)
                autor != null -> Catalogo.pesquisarAutor(autor)
                else -> emptyList()
            }

            call.respond(resultado)
        }
    }
}
