package com.limi.services

import com.limi.exceptions.NotFoundException
import com.limi.exceptions.ValidationException
import com.limi.models.Livro
import com.limi.repositories.LivroRepository
import org.jetbrains.exposed.sql.transactions.transaction
import com.limi.validation.validateForCreation

class LivroService(private val livroRepository: LivroRepository,
                   private val externalBookService: ExternalBookService ) {  // Injeção de dependência
    suspend fun adicionarLivro(livro: Livro): Livro {
        // 1) validações locais (já existentes)
        livro.validateForCreation()

        // 2) Verificação de duplicatas no banco de dados
        livroRepository.findByTitle(livro.titulo)?.let {
            throw ValidationException("titulo", "Livro já existe no catálogo")
        }

        // 3) validação externa
        val found = if (/* você tiver ISBN no DTO */ false) {
            // existsByIsbn(livro.isbn)
            false
        } else {
            externalBookService.existsByTitle(livro.titulo)
        }
        if (!found) {
            throw ValidationException("titulo", "Livro não encontrado em acervos oficiais")
        }

        // 4) persiste no banco
        return livroRepository.addLivro(livro)
    }

    fun buscarLivroPorId(id: Int): Livro? {
        return livroRepository.getLivroById(id)
            ?: throw NotFoundException("Livro com ID $id não encontrado")
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
