
package com.limi.services

import com.limi.exceptions.NotFoundException
import com.limi.exceptions.ValidationException
import com.limi.models.Livro
import com.limi.repositories.LivroRepository
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class LivroServiceTest {

    private val livroRepository: LivroRepository = mockk()
    private val externalBookService: ExternalBookService = mockk()
    private val livroService = LivroService(livroRepository, externalBookService)

    @Test
    fun `adicionarLivro should add livro when valid`() = runBlocking {
        // Given
        val livro = Livro(id = 1, titulo = "Test Livro", autor = "Test Autor", anoPublicacao = 2023, sinopse = "Test Sinopse", generos = listOf("Test Genero"))
        every { livroRepository.findByTitle(livro.titulo) } returns null
        coEvery { externalBookService.existsByTitle(livro.titulo) } returns true
        every { livroRepository.addLivro(livro) } returns livro

        // When
        val result = livroService.adicionarLivro(livro)

        // Then
        assertEquals(livro, result)
        verify(exactly = 1) { livroRepository.addLivro(livro) }
    }

    @Test
    fun `adicionarLivro should throw ValidationException when livro already exists`() = runBlocking {
        // Given
        val livro = Livro(id = 1, titulo = "Test Livro", autor = "Test Autor", anoPublicacao = 2023, sinopse = "Test Sinopse", generos = listOf("Test Genero"))
        every { livroRepository.findByTitle(livro.titulo) } returns livro

        // When & Then
        val exception = assertThrows<ValidationException> {
            livroService.adicionarLivro(livro)
        }
        assertEquals("Livro já existe no catálogo", exception.errors["titulo"])
    }

    @Test
    fun `buscarLivroPorId should return livro when livro exists`() {
        // Given
        val livro = Livro(id = 1, titulo = "Test Livro", autor = "Test Autor", anoPublicacao = 2023, sinopse = "Test Sinopse", generos = listOf("Test Genero"))
        every { livroRepository.getLivroById(1) } returns livro

        // When
        val result = livroService.buscarLivroPorId(1)

        // Then
        assertEquals(livro, result)
    }

    @Test
    fun `buscarLivroPorId should throw NotFoundException when livro does not exist`() {
        // Given
        every { livroRepository.getLivroById(1) } returns null

        // When & Then
        assertThrows<NotFoundException> {
            livroService.buscarLivroPorId(1)
        }
    }

    @Test
    fun `listarLivros should return a list of livros`() {
        // Given
        val livros = listOf(Livro(id = 1, titulo = "Test Livro", autor = "Test Autor", anoPublicacao = 2023, sinopse = "Test Sinopse", generos = listOf("Test Genero")))
        every { livroRepository.getAllLivros() } returns livros

        // When
        val result = livroService.listarLivros()

        // Then
        assertEquals(livros, result)
    }

    @Test
    fun `atualizarLivro should update livro`() {
        // Given
        val livro = Livro(id = 1, titulo = "Test Livro", autor = "Test Autor", anoPublicacao = 2023, sinopse = "Test Sinopse", generos = listOf("Test Genero"))
        every { livroRepository.updateLivro(1, livro) } returns livro

        // When
        val result = livroService.atualizarLivro(1, livro)

        // Then
        assertEquals(livro, result)
    }

    @Test
    fun `deletarLivro should delete livro`() {
        // Given
        every { livroRepository.deleteLivro(1) } returns true

        // When
        val result = livroService.deletarLivro(1)

        // Then
        assertTrue(result)
    }
}
