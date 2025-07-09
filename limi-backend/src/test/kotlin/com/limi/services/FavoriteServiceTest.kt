
package com.limi.services

import com.limi.models.Livro
import com.limi.repositories.FavoriteRepository
import io.mockk.every
import io.mockk.mockk
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test

class FavoriteServiceTest {

    private val favoriteRepository: FavoriteRepository = mockk()
    private val favoriteService = FavoriteService(favoriteRepository)

    @Test
    fun `addFavorite should add favorite`() {
        // Given
        every { favoriteRepository.addFavorite(1, 1) } returns true

        // When
        val result = favoriteService.addFavorite(1, 1)

        // Then
        assertTrue(result)
    }

    @Test
    fun `removeFavorite should remove favorite`() {
        // Given
        every { favoriteRepository.removeFavorite(1, 1) } returns true

        // When
        val result = favoriteService.removeFavorite(1, 1)

        // Then
        assertTrue(result)
    }

    @Test
    fun `getFavorites should return a list of livros`() {
        // Given
        val livros = listOf(Livro(id = 1, titulo = "Test Livro", autor = "Test Autor", anoPublicacao = 2023, sinopse = "Test Sinopse", generos = listOf("Test Genero")))
        every { favoriteRepository.getFavorites(1) } returns livros

        // When
        val result = favoriteService.getFavorites(1)

        // Then
        assertEquals(livros, result)
    }

    @Test
    fun `isFavorite should return true when favorite exists`() {
        // Given
        every { favoriteRepository.isFavorite(1, 1) } returns true

        // When
        val result = favoriteService.isFavorite(1, 1)

        // Then
        assertTrue(result)
    }
}
