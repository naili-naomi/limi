package com.limi.repositories

import com.limi.models.User
import com.limi.models.UserEntity
import com.limi.models.Users
import org.jetbrains.exposed.sql.transactions.transaction

class UserRepository {
    fun addUser(user: User): User {
        transaction {
            UserEntity.new {
                nome = user.nome
                email = user.email
            }
        }
        return user
    }

    fun buscarPorEmail(email: String): User? {
        return transaction {
            UserEntity.find { Users.email eq email }
                .firstOrNull()
                ?.let { User(it.id.value, it.nome, it.email, it.username, it.senha) }
        }
    }

    fun getAllUsers(): List<User> {
        return transaction {
            UserEntity.all().map {
                User(
                    id = it.id.value,
                    nome = it.nome,
                    email = it.email,
                    username = it.username,
                    senha = it.senha
                )
            }
        }
    }
}
