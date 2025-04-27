package com.limi.config

import com.limi.models.Autores
import com.limi.models.Generos
import com.limi.models.LivroGenero
import com.limi.models.Livros
import com.limi.models.Reviews
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.StdOutSqlLogger
import org.jetbrains.exposed.sql.addLogger
import org.jetbrains.exposed.sql.transactions.transaction

object DatabaseFactory {
    fun init() {
        val url = "jdbc:sqlite:/home/naili/IdeaProjects/limi/catalogo.db"
        val driver = "org.sqlite.JDBC"

        Database.connect(url, driver)

        transaction {
            addLogger(StdOutSqlLogger)
            SchemaUtils.create(Autores, Generos, Livros, LivroGenero, Reviews)
            commit()
        }

        transaction {
            DatabaseSeed.seedLivrosIniciais()
        }
    }
}