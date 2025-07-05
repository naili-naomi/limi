package com.limi.repositories

import com.limi.models.*
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.deleteWhere
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.and
import org.jetbrains.exposed.sql.transactions.transaction

class LivroRepository {

    fun getAllLivros(): List<Livro> = transaction {
        LivroEntity.all().map { it.toLivro() }
    }

    fun getLivroById(id: Int): Livro? = transaction {
        LivroEntity.findById(id)?.toLivro()
    }

    fun getLivrosByGenero(genero: String): List<Livro> = transaction {
        GeneroEntity.find { Generos.nome eq genero }.firstOrNull()?.livros?.map { it.toLivro() } ?: emptyList()
    }

    fun getAllGeneros(): List<String> = transaction {
        GeneroEntity.all().map { it.nome }
    }

    fun addLivro(livro: Livro): Livro = transaction {
        // Passo 1: Encontrar ou criar o autor
        val autorEntity = AutorEntity.find { Autores.nome eq livro.autor }.firstOrNull()
            ?: AutorEntity.new { nome = livro.autor }

        // Passo 2: Criar o livro
        val livroEntity = LivroEntity.new {
            titulo = livro.titulo
            autor = autorEntity
            anoPublicacao = livro.anoPublicacao
            sinopse = livro.sinopse
        }

        // Passo 3: Vincular gêneros
        livro.generos.forEach { generoNome ->
            val generoEntity = GeneroEntity.find { Generos.nome eq generoNome }.firstOrNull()
                ?: GeneroEntity.new { nome = generoNome }

            LivroGenero.insert {
                it[LivroGenero.livro] = livroEntity.id
                it[LivroGenero.genero] = generoEntity.id
            }
        }

        livroEntity.toLivro()
    }

    fun updateLivro(id: Int, livro: Livro): Livro? = transaction {
        LivroEntity.findById(id)?.apply {
            // Atualizar autor
            val novoAutor = AutorEntity.find { Autores.nome eq livro.autor }.firstOrNull()
                ?: AutorEntity.new { nome = livro.autor }
            autor = novoAutor

            // Atualizar campos básicos
            titulo = livro.titulo
            anoPublicacao = livro.anoPublicacao
            sinopse = livro.sinopse

            // Atualizar gêneros
            LivroGenero.deleteWhere { LivroGenero.livro eq id }
            livro.generos.forEach { generoNome ->
                val genero = GeneroEntity.find { Generos.nome eq generoNome }.firstOrNull()
                    ?: GeneroEntity.new { nome = generoNome }
                LivroGenero.insert {
                    it[LivroGenero.livro] = this@apply.id
                    it[LivroGenero.genero] = genero.id
                }
            }
        }?.toLivro()
    }

    fun deleteLivro(id: Int): Boolean = transaction {
        LivroEntity.findById(id)?.run {
            LivroGenero.deleteWhere { LivroGenero.livro eq id }
            delete()
            true
        } ?: false
    }
}