package com.limi.routes

import com.limi.models.User
import io.ktor.server.application.*
import io.ktor.server.routing.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.http.*

val usuarios = mutableListOf<User>(
    User(1, "Naomi", "naomi01", "naomi@email.com", "1234")
)

fun Route.usuarioRoutes() {
    route("/usuarios") {
        get {
            call.respond(usuarios)
        }

        post("/login") {
            val credenciais = call.receive<User>()
            val user = usuarios.find {
                it.verifLogin(credenciais.senha, credenciais.email)
            }

            if (user != null) {
                call.respond(HttpStatusCode.OK, "Login bem-sucedido para ${user.username}")
            } else {
                call.respond(HttpStatusCode.Unauthorized, "Credenciais inválidas")
            }
        }

        post("/cadastro") {
            val novo = call.receive<User>()
            usuarios.add(novo)
            call.respond(HttpStatusCode.Created, novo)
        }
    }
}
