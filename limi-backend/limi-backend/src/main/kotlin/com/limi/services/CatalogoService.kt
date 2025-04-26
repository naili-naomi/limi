package services

import com.limi.models.Catalogo
import com.limi.models.Livro

class CatalogoService(private val catalogo: Catalogo = Catalogo()) {

    fun adicionarLivro(livro: Livro) {
        catalogo.adicionarLivro(livro)
    }

    fun pesquisarPorTitulo(titulo: String): List<Livro> {
        return catalogo.pesquisarTitulo(titulo)
    }

    fun pesquisarPorAutor(nomeAutor: String): List<Livro> {
        return catalogo.pesquisarAutor(nomeAutor)
    }

    fun listarTodosLivros(): List<Livro> {
        return catalogo.getLivros()
    }
}
