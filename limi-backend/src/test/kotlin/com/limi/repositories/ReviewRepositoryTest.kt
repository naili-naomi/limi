
package com.limi.repositories

import com.limi.DatabaseConfig
import com.limi.models.Livro
import com.limi.models.Review
import com.limi.models.Reviews
import com.limi.models.User
import com.limi.models.Users
import org.junit.jupiter.api.AfterAll
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.Assertions.assertTrue

class ReviewRepositoryTest {

    private lateinit var reviewRepository: ReviewRepository
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
        reviewRepository = ReviewRepository()
        userRepository = UserRepository()
        livroRepository = LivroRepository()
    }

    @Test
    fun `addReview should add a new review`() {
        val user = userRepository.addUser(User(nome = "Test User", username = "testuser", email = "test@example.com", senha = "password"))
        val livro = livroRepository.addLivro(Livro(titulo = "Test Livro", autor = "Test Autor", anoPublicacao = 2023, sinopse = "Test Sinopse", generos = listOf("Test Genero")))
        val review = Review(livroId = livro.id!!, userId = user.id!!, comentario = "Great book!", nota = 5)
        val addedReview = reviewRepository.addReview(review)
        assertNotNull(addedReview.id)
        assertEquals(review.comentario, addedReview.comentario)
    }

    @Test
    fun `getReviewsByBookId should return reviews for a given book`() {
        val user = userRepository.addUser(User(nome = "Test User", username = "testuser", email = "test@example.com", senha = "password"))
        val livro = livroRepository.addLivro(Livro(titulo = "Test Livro", autor = "Test Autor", anoPublicacao = 2023, sinopse = "Test Sinopse", generos = listOf("Test Genero")))
        reviewRepository.addReview(Review(livroId = livro.id!!, userId = user.id!!, comentario = "Great book!", nota = 5))
        val reviews = reviewRepository.getReviewsByBookId(livro.id!!)
        assertEquals(1, reviews.size)
        assertEquals("Great book!", reviews[0].comentario)
    }

    @Test
    fun `deleteReview should delete a review`() {
        val user = userRepository.addUser(User(nome = "Test User", username = "testuser", email = "test@example.com", senha = "password"))
        val livro = livroRepository.addLivro(Livro(titulo = "Test Livro", autor = "Test Autor", anoPublicacao = 2023, sinopse = "Test Sinopse", generos = listOf("Test Genero")))
        val review = reviewRepository.addReview(Review(livroId = livro.id!!, userId = user.id!!, comentario = "Great book!", nota = 5))
        val result = reviewRepository.deleteReview(review.id!!)
        assertTrue(result)
        assertNull(reviewRepository.getReviewById(review.id!!))
    }

    @Test
    fun `updateReview should update a review`() {
        val user = userRepository.addUser(User(nome = "Test User", username = "testuser", email = "test@example.com", senha = "password"))
        val livro = livroRepository.addLivro(Livro(titulo = "Test Livro", autor = "Test Autor", anoPublicacao = 2023, sinopse = "Test Sinopse", generos = listOf("Test Genero")))
        val review = reviewRepository.addReview(Review(livroId = livro.id!!, userId = user.id!!, comentario = "Great book!", nota = 5))
        val updatedReview = review.copy(comentario = "Updated comment", nota = 4)
        val result = reviewRepository.updateReview(review.id!!, updatedReview)
        assertNotNull(result)
        assertEquals("Updated comment", result?.comentario)
        assertEquals(4, result?.nota)
    }

    @Test
    fun `getReviewById should return a review by id`() {
        val user = userRepository.addUser(User(nome = "Test User", username = "testuser", email = "test@example.com", senha = "password"))
        val livro = livroRepository.addLivro(Livro(titulo = "Test Livro", autor = "Test Autor", anoPublicacao = 2023, sinopse = "Test Sinopse", generos = listOf("Test Genero")))
        val review = reviewRepository.addReview(Review(livroId = livro.id!!, userId = user.id!!, comentario = "Great book!", nota = 5))
        val foundReview = reviewRepository.getReviewById(review.id!!)
        assertNotNull(foundReview)
        assertEquals(review.id, foundReview?.id)
    }
}
