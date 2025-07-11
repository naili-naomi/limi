package com.limi.services  // Pacote corrigido

import com.limi.models.Livro
import com.limi.repositories.LivroRepository  // Import do repositório
import com.limi.clients.buscarImagemPorTitulo




class CatalogoService(private val livroRepository: LivroRepository) {  // Recebe repositório via construtor

    fun pesquisarPorTitulo(titulo: String): List<Livro> {
        return livroRepository.getAllLivros()
            .filter { it.titulo.contains(titulo, ignoreCase = true) }
    }

    fun pesquisarPorAutor(nomeAutor: String): List<Livro> {
        return livroRepository.getAllLivros()
            .filter { it.autor.contains(nomeAutor, ignoreCase = true) }
    }

    fun pesquisarPorGenero(genero: String): List<Livro> {
        return livroRepository.getLivrosByGenero(genero)
    }

    fun listarTodosGeneros(): List<String> {
        return livroRepository.getAllGeneros()
    }

    fun pesquisarLivros(query: String): List<Livro> {
        val livrosPorTitulo = livroRepository.getAllLivros()
            .filter { it.titulo.contains(query, ignoreCase = true) }
        val livrosPorAutor = livroRepository.getAllLivros()
            .filter { it.autor.contains(query, ignoreCase = true) }
        return (livrosPorTitulo + livrosPorAutor).distinctBy { it.id }
    }

    fun listarTodosLivros(): List<Livro> {
        return livroRepository.getAllLivros()
    }

    fun getLivroById(id: Int): Livro? {
        return livroRepository.getLivroById(id)
    }

    fun adicionarLivro(livro: Livro) {
        val urlImagem = buscarImagemPorTitulo(livro.titulo)
        val livroComImagem = livro.copy(urlImagem = urlImagem)
        livroRepository.addLivro(livroComImagem)
    }

}
