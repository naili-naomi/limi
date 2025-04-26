package com.limi.config

import com.limi.models.Livros
import com.limi.models.Reviews
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.StdOutSqlLogger
import org.jetbrains.exposed.sql.addLogger
import org.jetbrains.exposed.sql.transactions.transaction

object DatabaseFactory {
    fun init() {
        val url = "jdbc:sqlite:/home/naili/IdeaProjects/limi/catalogo.db" //
        val driver = "org.sqlite.JDBC"

        Database.connect(
            url = url,
            driver = driver
        )

        transaction {
            addLogger(StdOutSqlLogger)
            // Cria todas as tabelas
            SchemaUtils.create(Livros, Reviews)
            commit() // Garante o commit
        }
    }
}