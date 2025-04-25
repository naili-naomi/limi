package com.limi.models

object Catalogo {
    private val Books = mutableListOf<Book>()

    fun adicionarBook(Book: Book) {
        Books.add(Book)
    }

    fun listarBooks(): List<Book> {
        return Books
    }

    fun pesquisarBook(titulo: String): List<Book> {
        return Books.filter { it.titulo.contains(titulo, ignoreCase = true) }
    }

    fun pesquisarAutor(nomeAutor: String): List<Book> {
        return Books.filter { it.autor.contains(nomeAutor, ignoreCase = true) }
    }

    fun getBookPorId(id: Int): Book? {
        return Books.find { it.id == id }
    }

    fun atualizarBook(id: Int, novoBook: Book): Boolean {
        val index = Books.indexOfFirst { it.id == id }
        return if (index != -1) {
            Books[index] = novoBook
            true
        } else {
            false
        }
    }

    fun removerBook(id: Int): Boolean {
        return Books.removeIf { it.id == id }
    }
}
