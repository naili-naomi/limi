package com.limi.controllers

import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import com.limi.models.Livro
import com.limi.services.CatalogoService

fun Route.catalogoRoutes(catalogoService: CatalogoService) {

    route("/catalogo") {

        get {
            call.respond(catalogoService.listarTodosLivros())
        }

        get("/titulo/{titulo}") {
            val titulo = call.parameters["titulo"] ?: return@get call.respondText("Título inválido")
            val livros = catalogoService.pesquisarPorTitulo(titulo)
            call.respond(livros)
        }

        get("/autor/{nomeAutor}") {
            val nomeAutor = call.parameters["nomeAutor"] ?: return@get call.respondText("Nome de autor inválido")
            val livros = catalogoService.pesquisarPorAutor(nomeAutor)
            call.respond(livros)
        }

        get("/genero/{genero}") {
            val genero = call.parameters["genero"] ?: return@get call.respondText("Gênero inválido", status = io.ktor.http.HttpStatusCode.BadRequest)
            val livros = catalogoService.pesquisarPorGenero(genero)
            call.respond(livros)
        }

        get("/generos") {
            val generos = catalogoService.listarTodosGeneros()
            call.respond(generos)
        }

        get("/{id}") {
            val id = call.parameters["id"]?.toIntOrNull() ?: return@get call.respondText("ID inválido", status = io.ktor.http.HttpStatusCode.BadRequest)
            val livro = catalogoService.getLivroById(id) ?: return@get call.respondText("Livro não encontrado", status = io.ktor.http.HttpStatusCode.NotFound)
            call.respond(livro)
        }

        get("/search") {
            val query = call.request.queryParameters["query"] ?: return@get call.respondText("Parâmetro de busca inválido", status = io.ktor.http.HttpStatusCode.BadRequest)
            val livros = catalogoService.pesquisarLivros(query)
            call.respond(livros)
        }

        post("/livro") {
            val livro = call.receive<Livro>()
            catalogoService.adicionarLivro(livro)
            call.respondText("Livro adicionado ao catálogo!")
        }
    }
}
