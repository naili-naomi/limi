package services

import com.limi.models.Livro

class LivroService {
    private val livros = mutableListOf<Livro>()

    fun adicionarLivro(livro: Livro) {
        livros.add(livro)
    }

    fun buscarLivroPorId(id: Int): Livro? {
        return livros.find { it.id == id }
    }

    fun listarLivros(): List<Livro> = livros
}
