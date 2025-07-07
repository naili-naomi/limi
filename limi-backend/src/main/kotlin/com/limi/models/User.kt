package com.limi.models

import kotlinx.serialization.Serializable
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.IntIdTable

@Serializable
data class User(
    val id: Int = 0, // ou null se for Int?
    val nome: String,
    val username: String,
    val email: String,
    val senha: String,
    val resetToken: String? = null,
    val resetTokenExpiry: Long? = null
) {
    fun verifLogin(senha: String, email: String): Boolean {
        return this.senha == senha && this.email == email
    }
}
object Users : IntIdTable() {
    val nome = varchar("nome", 255)
    val username = varchar("username", 255).uniqueIndex()
    val email = varchar("email", 255).uniqueIndex()
    val senha = varchar("senha", 255)
    val resetToken = varchar("reset_token", 255).nullable()
    val resetTokenExpiry = long("reset_token_expiry").nullable()
}

// Entidade que representa a tabela no Kotlin
class UserEntity(id: EntityID<Int>) : IntEntity(id) {
    companion object : IntEntityClass<UserEntity>(Users)

    var nome by Users.nome
    var username by Users.username
    var email by Users.email
    var senha by Users.senha
    var resetToken by Users.resetToken
    var resetTokenExpiry by Users.resetTokenExpiry

    fun toUser() = User(
        id = id.value,
        nome = nome,
        username = username,
        email = email,
        senha = senha,
        resetToken = resetToken,
        resetTokenExpiry = resetTokenExpiry
    )
}

