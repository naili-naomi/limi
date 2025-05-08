package com.limi.services

import com.limi.models.Livro
import com.limi.repositories.LivroRepository
import io.mockk.every
import io.mockk.mockk
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class CatalogoServiceTest {
    private val livroRepository = mockk<LivroRepository>() // Mock
    private val catalogoService = CatalogoService(livroRepository)

    @Test
    fun `pesquisa por titulo deve retornar livros corretos`() {
        // Configura mock
        every { livroRepository.getAllLivros()  } returns listOf(
            Livro(
                id = 1,
                titulo = "O Senhor dos Anéis",
                autor = "Tolkien",
                anoPublicacao = 1954,
                sinopse = "Trilogia épica",
                generos = listOf("Fantasia")
            )
        )

        val resultado = catalogoService.pesquisarPorTitulo("Senhor")

        assertEquals(1, resultado.size)
        assertEquals("O Senhor dos Anéis", resultado[0].titulo)
    }
}