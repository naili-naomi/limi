package com.limi

import com.limi.config.DatabaseFactory
import com.limi.models.LivroEntity
import com.limi.models.ReviewEntity
import org.jetbrains.exposed.sql.transactions.transaction

fun main() {
    try {
        println("🔥 Iniciando aplicação...")
        DatabaseFactory.init()

        transaction {
            println("\n📦 Inserindo dados de teste...")

            // Livro
            val livro = LivroEntity.new {
                titulo = "Dom Casmurro"
                autor = "Machado de Assis"
                anoPublicacao = 1899
                sinopse = "Clássico brasileiro"
                genero = "Romance"
            }
            println("✅ Livro inserido - ID: ${livro.id.value}")

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