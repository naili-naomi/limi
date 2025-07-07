package com.limi.controllers

import com.limi.services.FavoriteService
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.favoriteRoutes(favoriteService: FavoriteService) {
    authenticate("auth-jwt") {
        route("/favorites") {
            get {
                val principal = call.principal<JWTPrincipal>()
                val userId = principal!!.payload.getClaim("userId").asInt()
                val favorites = favoriteService.getFavorites(userId)
                call.respond(favorites)
            }

            post("/{livroId}") {
                val principal = call.principal<JWTPrincipal>()
                val userId = principal!!.payload.getClaim("userId").asInt()
                val livroId = call.parameters["livroId"]?.toIntOrNull()

                if (livroId == null) {
                    call.respond(HttpStatusCode.BadRequest, "Invalid book ID")
                    return@post
                }

                favoriteService.addFavorite(userId, livroId)
                call.respond(HttpStatusCode.OK)
            }

            delete("/{livroId}") {
                val principal = call.principal<JWTPrincipal>()
                val userId = principal!!.payload.getClaim("userId").asInt()
                val livroId = call.parameters["livroId"]?.toIntOrNull()

                if (livroId == null) {
                    call.respond(HttpStatusCode.BadRequest, "Invalid book ID")
                    return@delete
                }

                favoriteService.removeFavorite(userId, livroId)
                call.respond(HttpStatusCode.OK)
            }

            get("/isFavorite/{livroId}") {
                val principal = call.principal<JWTPrincipal>()
                val userId = principal!!.payload.getClaim("userId").asInt()
                val livroId = call.parameters["livroId"]?.toIntOrNull()

                if (livroId == null) {
                    call.respond(HttpStatusCode.BadRequest, "Invalid book ID")
                    return@get
                }

                val isFav = favoriteService.isFavorite(userId, livroId)
                call.respond(isFav)
            }
        }
    }
}
