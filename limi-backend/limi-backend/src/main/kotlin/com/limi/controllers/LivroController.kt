package controllers

import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import com.limi.models.Livro
import services.LivroService

fun Route.livroRoutes(livroService: LivroService) {

    route("/livros") {

        get {
            call.respond(livroService.listarLivros())
        }

        get("/{id}") {
            val id = call.parameters["id"]?.toIntOrNull()
            if (id == null) {
                call.respondText("ID inválido")
                return@get
            }
            val livro = livroService.buscarLivroPorId(id)
            if (livro != null) {
                call.respond(livro)
            } else {
                call.respondText("Livro não encontrado")
            }
        }

        post {
            val novoLivro = call.receive<Livro>()
            livroService.adicionarLivro(novoLivro)
            call.respondText("Livro adicionado!")
        }
    }
}
