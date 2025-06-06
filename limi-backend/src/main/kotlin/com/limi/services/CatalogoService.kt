package com.limi.services  // Pacote corrigido

import com.limi.models.Livro
import com.limi.repositories.LivroRepository  // Import do repositório

class CatalogoService(private val livroRepository: LivroRepository) {  // Recebe repositório via construtor

    fun pesquisarPorTitulo(titulo: String): List<Livro> {
        return livroRepository.getAllLivros()
            .filter { it.titulo.contains(titulo, ignoreCase = true) }
    }

    fun pesquisarPorAutor(nomeAutor: String): List<Livro> {
        return livroRepository.getAllLivros()
            .filter { it.autor.contains(nomeAutor, ignoreCase = true) }
    }
    fun listarTodosLivros(): List<Livro> {
        return livroRepository.getAllLivros()
    }
    fun adicionarLivro(livro: Livro) {
        livroRepository.addLivro(livro)
    }
}
