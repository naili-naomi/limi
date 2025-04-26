package com.limi.routes

import com.limi.models.Catalogo
import com.limi.models.Livro
import io.ktor.server.application.*
import io.ktor.server.routing.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.http.*

val catalogo = Catalogo()

fun Route.catalogoRoutes() {
    route("/catalogo") {
        get {
            call.respond(catalogo.getLivros())
        }

        post("/adicionar") {
            val novoLivro = call.receive<Livro>()
            catalogo.adicionarLivro(novoLivro)
            call.respond(HttpStatusCode.Created, novoLivro)
        }

        get("/pesquisaLivro/{titulo}") {
            val titulo = call.parameters["titulo"] ?: ""
            val existe = catalogo.pesquisarTitulo(titulo)
            call.respond(mapOf("existe" to existe))
        }

        get("/pesquisaAutor/{autor}") {
            val autor = call.parameters["autor"] ?: ""
            val existe = catalogo.pesquisarAutor(autor)
            call.respond(mapOf("existe" to existe))
        }
    }
}
