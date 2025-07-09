
package com.limi.services

import com.limi.models.Autor
import com.limi.repositories.AutorRepository
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class AutorServiceTest {

    private val autorRepository: AutorRepository = mockk()
    private val autorService = AutorService(autorRepository)

    @Test
    fun `adicionarAutor should add autor`() {
        // Given
        val autor = Autor(id = 1, nome = "Test Autor")
        every { autorRepository.addAutor(autor) } returns autor

        // When
        val result = autorService.adicionarAutor(autor)

        // Then
        assertEquals(autor, result)
        verify(exactly = 1) { autorRepository.addAutor(autor) }
    }

    @Test
    fun `listarAutores should return a list of autors`() {
        // Given
        val autors = listOf(Autor(id = 1, nome = "Test Autor"))
        every { autorRepository.getAllAutores() } returns autors

        // When
        val result = autorService.listarAutores()

        // Then
        assertEquals(autors, result)
    }

    @Test
    fun `buscarPorNome should return autor when autor exists`() {
        // Given
        val autor = Autor(id = 1, nome = "Test Autor")
        every { autorRepository.buscarPorNome("Test Autor") } returns autor

        // When
        val result = autorService.buscarPorNome("Test Autor")

        // Then
        assertEquals(autor, result)
    }
}
