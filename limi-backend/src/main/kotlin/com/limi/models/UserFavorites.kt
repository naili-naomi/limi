package com.limi.models

import org.jetbrains.exposed.sql.Table

object UserFavorites : Table("user_favorites") {
    val user = reference("user_id", Users)
    val livro = reference("livro_id", Livros)
    override val primaryKey = PrimaryKey(user, livro, name = "PK_User_Favorites")
}
