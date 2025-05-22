package com.limi.services

import com.limi.models.Livro
import com.limi.repositories.LivroRepository
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import io.mockk.coEvery
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals
import kotlinx.coroutines.runBlocking // adicione esse import
import kotlin.test.*
import com.limi.exceptions.*
import kotlin.test.assertTrue

class LivroServiceTest {
    private val livroRepository = mockk<LivroRepository>() // Mock
    private val externalBookService = mockk<ExternalBookService>()
    private val livroService = LivroService(livroRepository, externalBookService)


    @Test
    fun `deve adicionar um livro corretamente`() = runBlocking {
        val livro = Livro(
            id = 1,
            titulo = "1984",
            autor = "George Orwell",
            anoPublicacao = 1949,
            sinopse = "Distopia",
            generos = listOf("Ficção")
        )

        every { livroRepository.addLivro(any()) } returns livro
        coEvery { externalBookService.existsByTitle(any()) } returns true

        val resultado = livroService.adicionarLivro(livro)

        verify(exactly = 1) { livroRepository.addLivro(livro) }

        assertEquals("1984", resultado.titulo)
        assertEquals("George Orwell", resultado.autor)
    }
    @Test
    fun `adicionarLivro falha quando não existe externamente`() = runBlocking {
        val livro = Livro(0, "Inexistente", "Autor X", 2020, "Sinopse", listOf("Ficção"))

        coEvery { externalBookService.existsByTitle(any()) } returns false

        val error = assertFailsWith<ValidationException> {
            livroService.adicionarLivro(livro)
        }
        assertTrue(error.errors.containsKey("titulo"))
    }

}