package com.limi.services

import com.limi.models.Livro
import com.limi.repositories.LivroRepository
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class LivroServiceTest {
    private val livroRepository = mockk<LivroRepository>() // Mock
    private val livroService = LivroService(livroRepository)

    @Test
    fun `deve adicionar um livro corretamente`() {

        val livro = Livro(
            id = 1,
            titulo = "1984",
            autor = "George Orwell",
            anoPublicacao = 1949,
            sinopse = "Distopia",
            generos = listOf("Ficção")
        )
        // Configura mock  para retornar o livro quando addLivro for chamado
        every { livroRepository.addLivro(any())  } returns livro


        val resultado = livroService.adicionarLivro(livro)

        verify(exactly = 1) { livroRepository.addLivro(livro)  }

        // Verifica se o retorno foi o esperado
        assertEquals("1984", resultado.titulo)
        assertEquals("George Orwell", resultado.autor)
    }
}