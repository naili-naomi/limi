package com.limi.models

import org.jetbrains.exposed.dao.id.IntIdTable

object UserFavorites : IntIdTable() {
    val user = reference("user_id", Users)
    val livro = reference("livro_id", Livros)
}