package com.limi.config

import com.limi.models.Autores
import com.limi.models.Generos
import com.limi.models.Users
import com.limi.models.LivroGenero
import com.limi.models.Livros
import com.limi.models.Reviews
import com.limi.models.UserFavorites
import com.limi.models.ReviewLikes
import io.ktor.client.*
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.StdOutSqlLogger
import org.jetbrains.exposed.sql.addLogger
import org.jetbrains.exposed.sql.transactions.transaction

object DatabaseFactory {
    fun init(client: HttpClient, url: String? = null, driver: String? = null) {
        // Só conecta se vierem parâmetros explícitos
        if (url != null && driver != null) {
            Database.connect(url, driver)
        }
        transaction {
            addLogger(StdOutSqlLogger)
            SchemaUtils.create(Autores, Users, Generos, Livros,
                LivroGenero, Reviews, UserFavorites, ReviewLikes)
            commit()
        }

        transaction {
            DatabaseSeed.seedLivrosIniciais(client)
        }
    }
}