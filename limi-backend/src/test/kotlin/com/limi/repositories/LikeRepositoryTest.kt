
package com.limi.repositories

import com.limi.DatabaseConfig
import com.limi.models.Review
import com.limi.models.ReviewLikes
import com.limi.models.Reviews
import com.limi.models.User
import com.limi.models.Users
import com.limi.models.Livro
import org.junit.jupiter.api.AfterAll
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertTrue

class LikeRepositoryTest {

    private lateinit var likeRepository: LikeRepository
    private lateinit var userRepository: UserRepository
    private lateinit var reviewRepository: ReviewRepository
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
        likeRepository = LikeRepository()
        userRepository = UserRepository()
        reviewRepository = ReviewRepository()
        livroRepository = LivroRepository()
    }

    @Test
    fun `addLike should add a new like`() {
        val user = userRepository.addUser(User(nome = "Test User", username = "testuser", email = "test@example.com", senha = "password"))
        val livro = Livro(titulo = "Test Livro", autor = "Test Autor", anoPublicacao = 2023, sinopse = "Test Sinopse", generos = listOf("Test Genero"))
        val addedLivro = livroRepository.addLivro(livro)
        val review = reviewRepository.addReview(Review(livroId = addedLivro.id!!, userId = user.id!!, comentario = "Great book!", nota = 5))
        val result = likeRepository.addLike(user.id!!, review.id!!)
        assertTrue(result)
        assertTrue(likeRepository.isLiked(user.id!!, review.id!!))
    }

    @Test
    fun `removeLike should remove a like`() {
        val user = userRepository.addUser(User(nome = "Test User", username = "testuser", email = "test@example.com", senha = "password"))
        val livro = Livro(titulo = "Test Livro", autor = "Test Autor", anoPublicacao = 2023, sinopse = "Test Sinopse", generos = listOf("Test Genero"))
        val addedLivro = livroRepository.addLivro(livro)
        val review = reviewRepository.addReview(Review(livroId = addedLivro.id!!, userId = user.id!!, comentario = "Great book!", nota = 5))
        likeRepository.addLike(user.id!!, review.id!!)
        val result = likeRepository.removeLike(user.id!!, review.id!!)
        assertTrue(result)
        assertFalse(likeRepository.isLiked(user.id!!, review.id!!))
    }

    @Test
    fun `isLiked should return true if review is liked`() {
        val user = userRepository.addUser(User(nome = "Test User", username = "testuser", email = "test@example.com", senha = "password"))
        val livro = Livro(titulo = "Test Livro", autor = "Test Autor", anoPublicacao = 2023, sinopse = "Test Sinopse", generos = listOf("Test Genero"))
        val addedLivro = livroRepository.addLivro(livro)
        val review = reviewRepository.addReview(Review(livroId = addedLivro.id!!, userId = user.id!!, comentario = "Great book!", nota = 5))
        likeRepository.addLike(user.id!!, review.id!!)
        val result = likeRepository.isLiked(user.id!!, review.id!!)
        assertTrue(result)
    }

    @Test
    fun `isLiked should return false if review is not liked`() {
        val user = userRepository.addUser(User(nome = "Test User", username = "testuser", email = "test@example.com", senha = "password"))
        val livro = Livro(titulo = "Test Livro", autor = "Test Autor", anoPublicacao = 2023, sinopse = "Test Sinopse", generos = listOf("Test Genero"))
        val addedLivro = livroRepository.addLivro(livro)
        val review = reviewRepository.addReview(Review(livroId = addedLivro.id!!, userId = user.id!!, comentario = "Great book!", nota = 5))
        val result = likeRepository.isLiked(user.id!!, review.id!!)
        assertFalse(result)
    }
}
