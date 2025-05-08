package com.limi

import com.limi.config.DatabaseFactory
import com.limi.models.LivroEntity
import com.limi.models.ReviewEntity
import com.limi.models.AutorEntity
import com.limi.models.LivroGenero
import com.limi.models.Autores
import com.limi.models.Generos
import com.limi.models.UserEntity
import com.limi.models.GeneroEntity
import com.limi.services.UserService
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.transactions.transaction
import com.limi.repositories.UserRepository

fun main() {
    try {
        println("🔥 Iniciando aplicação...")
        DatabaseFactory.init()

        val userService = UserService(UserRepository())

        transaction {
            println("\n📦 Inserindo dados de teste...")


            // 1. Cria usuário de teste
            val usuarioTeste = UserEntity.new {
                nome = "Ana Silva"
                username = "aninha01"
                email = "ana@exemplo.com"
                senha = "senha_criptografada"
            }
            println("✅ Usuário inserido - ID: ${usuarioTeste.id.value}")

            // 2. Testa busca por email
            val usuarioBuscado = userService.buscarPorEmail("ana@exemplo.com")
            println("🔍 Usuário encontrado por email: ${usuarioBuscado?.nome ?: "Nenhum"}")

            val autorEntity = AutorEntity.find { Autores.nome eq "Machado de Assis" }
                .firstOrNull()
                ?: AutorEntity.new { nome = "Machado de Assis" }

            // Livro
            val livro = LivroEntity.new {
                titulo = "Dom Casmurro"
                autor = autorEntity
                anoPublicacao = 1899
                sinopse = "Clássico brasileiro"
            }
            println("✅ Livro inserido - ID: ${livro.id.value}")


            // Adiciona gêneros
            val genero = GeneroEntity.find { Generos.nome eq "Romance" }.firstOrNull()
                ?: GeneroEntity.new { nome = "Romance" }

            LivroGenero.insert {
                it[LivroGenero.livro] = livro.id
                it[LivroGenero.genero] = genero.id
            }

            // Review
            ReviewEntity.new {
                livroId = livro.id.value
                userId = "user_123"
                comentario = "Obra-prima!"
                nota = 5
            }
            println("✅ Review inserida")

            commit()
        }

        println("\n🎉 Dados inseridos com sucesso!")
    } catch (e: Exception) {
        println("\n❌ ERRO FATAL: ${e.message}")
        e.printStackTrace()
    }
}