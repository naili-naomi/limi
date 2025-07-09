
package com.limi.services

import com.limi.models.Livro
import com.limi.repositories.LivroRepository
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class CatalogoServiceTest {

    private val livroRepository: LivroRepository = mockk(relaxed = true)
    private val catalogoService = CatalogoService(livroRepository)

    @Test
    fun `pesquisarPorTitulo should return a list of livros`() {
        // Given
        val livros = listOf(Livro(id = 1, titulo = "Test Livro", autor = "Test Autor", anoPublicacao = 2023, sinopse = "Test Sinopse", generos = listOf("Test Genero")))
        every { livroRepository.getAllLivros() } returns livros

        // When
        val result = catalogoService.pesquisarPorTitulo("Test")

        // Then
        assertEquals(livros, result)
    }

    @Test
    fun `pesquisarPorAutor should return a list of livros`() {
        // Given
        val livros = listOf(Livro(id = 1, titulo = "Test Livro", autor = "Test Autor", anoPublicacao = 2023, sinopse = "Test Sinopse", generos = listOf("Test Genero")))
        every { livroRepository.getAllLivros() } returns livros

        // When
        val result = catalogoService.pesquisarPorAutor("Test")

        // Then
        assertEquals(livros, result)
    }

    @Test
    fun `pesquisarPorGenero should return a list of livros`() {
        // Given
        val livros = listOf(Livro(id = 1, titulo = "Test Livro", autor = "Test Autor", anoPublicacao = 2023, sinopse = "Test Sinopse", generos = listOf("Test Genero")))
        every { livroRepository.getLivrosByGenero("Test Genero") } returns livros

        // When
        val result = catalogoService.pesquisarPorGenero("Test Genero")

        // Then
        assertEquals(livros, result)
    }

    @Test
    fun `listarTodosGeneros should return a list of generos`() {
        // Given
        val generos = listOf("Test Genero")
        every { livroRepository.getAllGeneros() } returns generos

        // When
        val result = catalogoService.listarTodosGeneros()

        // Then
        assertEquals(generos, result)
    }

    @Test
    fun `pesquisarLivros should return a list of livros`() {
        // Given
        val livros = listOf(Livro(id = 1, titulo = "Test Livro", autor = "Test Autor", anoPublicacao = 2023, sinopse = "Test Sinopse", generos = listOf("Test Genero")))
        every { livroRepository.getAllLivros() } returns livros

        // When
        val result = catalogoService.pesquisarLivros("Test")

        // Then
        assertEquals(livros, result)
    }

    @Test
    fun `listarTodosLivros should return a list of livros`() {
        // Given
        val livros = listOf(Livro(id = 1, titulo = "Test Livro", autor = "Test Autor", anoPublicacao = 2023, sinopse = "Test Sinopse", generos = listOf("Test Genero")))
        every { livroRepository.getAllLivros() } returns livros

        // When
        val result = catalogoService.listarTodosLivros()

        // Then
        assertEquals(livros, result)
    }

    @Test
    fun `getLivroById should return a livro`() {
        // Given
        val livro = Livro(id = 1, titulo = "Test Livro", autor = "Test Autor", anoPublicacao = 2023, sinopse = "Test Sinopse", generos = listOf("Test Genero"))
        every { livroRepository.getLivroById(1) } returns livro

        // When
        val result = catalogoService.getLivroById(1)

        // Then
        assertEquals(livro, result)
    }

    @Test
    fun `adicionarLivro should add a livro`() {
        // Given
        val livro = Livro(id = 1, titulo = "Test Livro", autor = "Test Autor", anoPublicacao = 2023, sinopse = "Test Sinopse", generos = listOf("Test Genero"))

        // When
        catalogoService.adicionarLivro(livro)

        // Then
        verify(exactly = 1) { livroRepository.addLivro(any()) }
    }
}
