package com.limi.services

import com.limi.models.Livro
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import services.CatalogoService

class CatalogoServiceTest {

    private lateinit var catalogoService: CatalogoService

    @BeforeEach
    fun setup() {
        catalogoService = CatalogoService()
    }

    @Test
    fun `pesquisa por titulo deve retornar livros corretos`() {
        val livro = Livro(1, "O Senhor dos Anéis", "Tolkien", 1984, "Livro sobre alguma coisa")
        catalogoService.adicionarLivro(livro)

        val resultado = catalogoService.pesquisarPorTitulo("Senhor")
        assertEquals(1, resultado.size)
    }

    @Test
    fun `pesquisa por autor deve ser case-insensitive`() {
        val livro = Livro(2, "Ensaio sobre a Cegueira", "Saramago", 1984, "Livro sobre alguma coisa")
        catalogoService.adicionarLivro(livro)

        val resultado = catalogoService.pesquisarPorAutor("saramago")
        assertEquals(1, resultado.size)
    }
}
