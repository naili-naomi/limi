package com.limi

import com.limi.models.*
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.transaction
import org.jetbrains.exposed.sql.deleteAll

object DatabaseConfig {
    fun init() {
        Database.connect("jdbc:h2:mem:test_db;DB_CLOSE_DELAY=-1", user = "root", driver = "org.h2.Driver")
        transaction {
            SchemaUtils.create(
                Users,
                Autores,
                Livros,
                Reviews,
                UserFavorites,
                ReviewLikes,
                Generos,
                LivroGenero
            )
        }
    }

    fun close() {
        transaction {
            SchemaUtils.drop(
                LivroGenero,
                Generos,
                ReviewLikes,
                UserFavorites,
                Reviews,
                Livros,
                Autores,
                Users
            )
        }
    }

    fun clearTables() {
        transaction {
            SchemaUtils.drop(
                LivroGenero,
                Generos,
                ReviewLikes,
                UserFavorites,
                Reviews,
                Livros,
                Autores,
                Users
            )
            SchemaUtils.create(
                Users,
                Autores,
                Livros,
                Reviews,
                UserFavorites,
                ReviewLikes,
                Generos,
                LivroGenero
            )
        }
    }
}