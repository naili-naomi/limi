package com.limi.controllers

import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import com.limi.models.Livro
import services.CatalogoService

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

        post("/livro") {
            val livro = call.receive<Livro>()
            catalogoService.adicionarLivro(livro)
            call.respondText("Livro adicionado ao catálogo!")
        }
    }
}
