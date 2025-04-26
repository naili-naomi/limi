package com.limi.services

import com.limi.models.Livro
import com.limi.repositories.LivroRepository
import org.jetbrains.exposed.sql.transactions.transaction

class LivroService(private val livroRepository: LivroRepository) {  // Injeção de dependência
    fun adicionarLivro(livro: Livro): Livro {
        return livroRepository.addLivro(livro)
    }

    fun buscarLivroPorId(id: Int): Livro? {
        return livroRepository.getLivroById(id)
    }

    fun listarLivros(): List<Livro> {
        return livroRepository.getAllLivros()
    }

    // Adicione estas funções para completar o CRUD
    fun atualizarLivro(id: Int, livro: Livro): Livro? {
        return livroRepository.updateLivro(id, livro)
    }

    fun deletarLivro(id: Int): Boolean {
        return livroRepository.deleteLivro(id)
    }
}
