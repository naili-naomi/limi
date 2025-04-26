package com.limi.models

import kotlinx.serialization.Serializable

@Serializable
data class User(
    val id: Int,
    val nome: String,
    val username: String,
    val email: String,
    val senha: String
) {
    fun verifLogin(senha: String, email: String): Boolean {
        return this.senha == senha && this.email == email
    }
}
