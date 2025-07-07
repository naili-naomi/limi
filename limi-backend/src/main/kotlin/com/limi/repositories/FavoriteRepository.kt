package com.limi.repositories

import com.limi.models.Livro
import com.limi.models.LivroEntity
import com.limi.models.UserFavorites
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.and
import org.jetbrains.exposed.sql.deleteWhere
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.transactions.transaction

class FavoriteRepository {

    fun addFavorite(userId: Int, livroId: Int): Boolean = transaction {
        val existing = UserFavorites.select(
            (UserFavorites.user eq userId) and (UserFavorites.livro eq livroId)
        ).count()

        if (existing == 0L) {
            UserFavorites.insert {
                it[user] = userId
                it[livro] = livroId
            }
            true
        } else {
            false // Already a favorite
        }
    }

    fun removeFavorite(userId: Int, livroId: Int): Boolean = transaction {
        UserFavorites.deleteWhere {
            (user eq userId) and (livro eq livroId)
        } > 0
    }

    fun getFavorites(userId: Int): List<Livro> = transaction {
        UserFavorites.select(UserFavorites.user eq userId)
            .map { it[UserFavorites.livro] }
            .mapNotNull { LivroEntity.findById(it)?.toLivro() }
    }

    fun isFavorite(userId: Int, livroId: Int): Boolean = transaction {
        UserFavorites.select(
            (UserFavorites.user eq userId) and (UserFavorites.livro eq livroId)
        ).count() > 0
    }
}
