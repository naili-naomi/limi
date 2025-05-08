package com.limi.repositories

import com.limi.models.Autor
import com.limi.models.AutorEntity
import com.limi.models.Autores
import org.jetbrains.exposed.sql.transactions.transaction

class AutorRepository {
    fun addAutor(autor: Autor): Autor = transaction {
            AutorEntity.new {
                nome = autor.nome
            }.toAutor()
        }


    fun getAllAutores(): List<Autor> {
        return transaction {
            AutorEntity.all().map {
                Autor(
                    id = it.id.value,
                    nome = it.nome
                )
            }
        }
    }
    fun getAutorById(id: Int): Autor? = transaction {
        AutorEntity.findById(id)?.toAutor()
        }



    fun buscarPorNome(nome: String): Autor? {
        return transaction {
            AutorEntity.find { Autores.nome eq nome }
                .firstOrNull()
                ?.let { Autor(it.id.value, it.nome) }
        }
    }
}
