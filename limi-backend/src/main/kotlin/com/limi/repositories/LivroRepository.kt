package com.limi.repositories

import com.limi.models.Livro
import com.limi.models.LivroEntity
import org.jetbrains.exposed.sql.transactions.transaction

class LivroRepository {
    fun getAllLivros(): List<Livro> = transaction {
        LivroEntity.all().map { it.toLivro() }
    }

    fun getLivroById(id: Int): Livro? = transaction {
        LivroEntity.findById(id)?.toLivro()
    }

    fun addLivro(livro: Livro): Livro = transaction {
        LivroEntity.new {
            titulo = livro.titulo
            autor = livro.autor
            anoPublicacao = livro.anoPublicacao
            sinopse = livro.sinopse
            genero = livro.genero
        }.toLivro()
    }

    fun updateLivro(id: Int, livro: Livro): Livro? = transaction {
        LivroEntity.findById(id)?.apply {
            titulo = livro.titulo
            autor = livro.autor
            anoPublicacao = livro.anoPublicacao
            sinopse = livro.sinopse
            genero = livro.genero
        }?.toLivro()
    }

    fun deleteLivro(id: Int): Boolean = transaction {
        LivroEntity.findById(id)?.delete() != null
    }
}