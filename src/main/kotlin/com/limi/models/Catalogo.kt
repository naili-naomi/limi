package com.limi.models

class Catalogo {
    private val livros: MutableList<Livro> = mutableListOf()

    fun adicionarLivro(livro: Livro) {
        livros.add(livro)
    }

    fun pesquisarTitulo(titulo: String): List<Livro> {
        return livros.filter { it.titulo.contains(titulo, ignoreCase = true) }
    }

    fun pesquisarAutor(nomeAutor: String): List<Livro> {
        return livros.filter { it.autor.contains(nomeAutor, ignoreCase = true) }
    }

    fun getLivros(): List<Livro> = livros
}
