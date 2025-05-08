package com.limi.services

import com.limi.models.Livro
import com.limi.repositories.LivroRepository
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class CatalogoServiceTest {
    private lateinit var catalogoService: CatalogoService
    private val livroRepository = LivroRepository()

    @BeforeEach
    fun setup() {
        catalogoService = CatalogoService(livroRepository)  // Inicialização correta
    }

    @Test
    fun `pesquisa por titulo deve retornar livros corretos`() {
        val livro = Livro(
            id = 1,
            titulo = "O Senhor dos Anéis",
            autor = "Tolkien",
            anoPublicacao = 1954,
            sinopse = "Trilogia épica",
            generos = listOf("Fantasia")  // Campo obrigatório adicionado
        )
        livroRepository.addLivro(livro)

        val resultado = catalogoService.pesquisarPorTitulo("Senhor")
        assertEquals(1, resultado.size)
    }
}