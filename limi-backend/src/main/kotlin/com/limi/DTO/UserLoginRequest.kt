package com.limi.DTO

import com.limi.repositories.UserRepository
import com.limi.config.JwtConfig
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import kotlinx.serialization.Serializable
import io.ktor.http.*
import io.ktor.server.routing.*
import org.mindrot.jbcrypt.BCrypt
import com.limi.services.ReviewService
import com.limi.models.Review


@Serializable
data class UserLoginRequest(val email: String, val senha: String)
