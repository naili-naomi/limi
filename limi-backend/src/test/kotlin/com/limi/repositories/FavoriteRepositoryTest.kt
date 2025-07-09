
package com.limi.repositories

import com.limi.DatabaseConfig
import com.limi.models.Livro
import com.limi.models.Livros
import com.limi.models.User
import com.limi.models.UserFavorites
import com.limi.models.Users
import org.junit.jupiter.api.AfterAll
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertTrue

class FavoriteRepositoryTest {

    private lateinit var favoriteRepository: FavoriteRepository
    private lateinit var userRepository: UserRepository
    private lateinit var livroRepository: LivroRepository

    companion object {
        @BeforeAll
        @JvmStatic
        fun setupAll() {
            DatabaseConfig.init()
        }

        @AfterAll
        @JvmStatic
        fun teardownAll() {
            DatabaseConfig.close()
        }
    }

    @BeforeEach
    fun setup() {
        DatabaseConfig.clearTables()
        favoriteRepository = FavoriteRepository()
        userRepository = UserRepository()
        livroRepository = LivroRepository()
    }

    @Test
    fun `addFavorite should add a new favorite`() {
        val user = userRepository.addUser(User(nome = "Test User", username = "testuser", email = "test@example.com", senha = "password"))
        val livro = livroRepository.addLivro(Livro(titulo = "Test Livro", autor = "Test Autor", anoPublicacao = 2023, sinopse = "Test Sinopse", generos = listOf("Test Genero")))
        val result = favoriteRepository.addFavorite(user.id!!, livro.id!!)
        assertTrue(result)
        assertTrue(favoriteRepository.isFavorite(user.id!!, livro.id!!))
    }

    @Test
    fun `removeFavorite should remove a favorite`() {
        val user = userRepository.addUser(User(nome = "Test User", username = "testuser", email = "test@example.com", senha = "password"))
        val livro = livroRepository.addLivro(Livro(titulo = "Test Livro", autor = "Test Autor", anoPublicacao = 2023, sinopse = "Test Sinopse", generos = listOf("Test Genero")))
        favoriteRepository.addFavorite(user.id!!, livro.id!!)
        val result = favoriteRepository.removeFavorite(user.id!!, livro.id!!)
        assertTrue(result)
        assertFalse(favoriteRepository.isFavorite(user.id!!, livro.id!!))
    }

    @Test
    fun `getFavorites should return a list of favorite books`() {
        val user = userRepository.addUser(User(nome = "Test User", username = "testuser", email = "test@example.com", senha = "password"))
        val livro1 = livroRepository.addLivro(Livro(titulo = "Test Livro 1", autor = "Test Autor", anoPublicacao = 2023, sinopse = "Test Sinopse", generos = listOf("Test Genero")))
        val livro2 = livroRepository.addLivro(Livro(titulo = "Test Livro 2", autor = "Test Autor", anoPublicacao = 2023, sinopse = "Test Sinopse", generos = listOf("Test Genero")))
        favoriteRepository.addFavorite(user.id!!, livro1.id!!)
        favoriteRepository.addFavorite(user.id!!, livro2.id!!)
        val favorites = favoriteRepository.getFavorites(user.id!!)
        assertEquals(2, favorites.size)
        assertTrue(favorites.any { it.id == livro1.id })
        assertTrue(favorites.any { it.id == livro2.id })
    }

    @Test
    fun `isFavorite should return true if book is favorite`() {
        val user = userRepository.addUser(User(nome = "Test User", username = "testuser", email = "test@example.com", senha = "password"))
        val livro = livroRepository.addLivro(Livro(titulo = "Test Livro", autor = "Test Autor", anoPublicacao = 2023, sinopse = "Test Sinopse", generos = listOf("Test Genero")))
        favoriteRepository.addFavorite(user.id!!, livro.id!!)
        val result = favoriteRepository.isFavorite(user.id!!, livro.id!!)
        assertTrue(result)
    }

    @Test
    fun `isFavorite should return false if book is not favorite`() {
        val user = userRepository.addUser(User(nome = "Test User", username = "testuser", email = "test@example.com", senha = "password"))
        val livro = livroRepository.addLivro(Livro(titulo = "Test Livro", autor = "Test Autor", anoPublicacao = 2023, sinopse = "Test Sinopse", generos = listOf("Test Genero")))
        val result = favoriteRepository.isFavorite(user.id!!, livro.id!!)
        assertFalse(result)
    }
}
