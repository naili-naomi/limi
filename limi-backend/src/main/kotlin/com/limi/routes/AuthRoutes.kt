package com.limi.routes

import com.limi.services.AuthService
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.authRoutes(authService: AuthService) {
    route("/auth") {
        post("/forgot-password") {
            val email = call.receive<String>()
            try {
                authService.forgotPassword(email)
                call.respond("Password reset email sent")
            } catch (e: Exception) {
                call.respond(e.message ?: "Error")
            }
        }

        post("/reset-password") {
            val params = call.receive<Map<String, String>>()
            val token = params["token"] ?: return@post call.respond("Token is missing")
            val newPassword = params["newPassword"] ?: return@post call.respond("New password is missing")

            try {
                authService.resetPassword(token, newPassword)
                call.respond("Password reset successfully")
            } catch (e: Exception) {
                call.respond(e.message ?: "Error")
            }
        }
    }
}
