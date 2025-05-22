package com.limi.models

import kotlinx.serialization.Serializable

@Serializable
data class UserResponse(
    val id: Int,
    val nome: String,
    val username: String,
    val email: String
) {
    companion object {
        fun of(user: com.limi.models.User) = UserResponse(
            id = user.id,
            nome = user.nome,
            username = user.username,
            email = user.email
        )
    }
}
