package com.limi.repositories

import com.limi.models.User
import com.limi.models.UserEntity
import com.limi.models.Users
import org.jetbrains.exposed.sql.transactions.transaction

class UserRepository {
    fun addUser(user: User): User =
        transaction {
            UserEntity.new {
                nome = user.nome
                username = user.username
                email = user.email
                senha = user.senha
            }.toUser()
        }



    fun buscarPorEmail(email: String): User? =
        transaction {
            UserEntity.find { Users.email eq email }
                .firstOrNull()
                ?.toUser()
        }

    fun findByUsername(username: String): User? = transaction {
        UserEntity.find { Users.username eq username }.firstOrNull()?.toUser()
    }


    fun getAllUsers(): List<User> = transaction {
            UserEntity.all().map {
               it.toUser()
            }
        }

    fun getUserById(id: Int): User? = transaction {
        UserEntity.findById(id)?.toUser()
    }

    fun updateUser(id: Int, updated: User): User? = transaction {
        UserEntity.findById(id)?.apply {
            nome = updated.nome
            username = updated.username
            email = updated.email
            senha = updated.senha
        }?.toUser()
    }



    fun deleteUser(id: Int): Boolean = transaction {
        UserEntity.findById(id)?.let {
            it.delete()
            true
        } ?: false
    }
}
