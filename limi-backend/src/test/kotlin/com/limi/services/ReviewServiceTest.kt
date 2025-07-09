
package com.limi.services

import com.limi.models.Review
import com.limi.models.User
import com.limi.repositories.ReviewRepository
import com.limi.repositories.UserRepository
import io.mockk.every
import io.mockk.mockk
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test

class ReviewServiceTest {

    private val reviewRepository: ReviewRepository = mockk()
    private val userRepository: UserRepository = mockk()
    private val reviewService = ReviewService(reviewRepository, userRepository)

    @Test
    fun `addReview should add review`() {
        // Given
        val review = Review(id = 1, livroId = 1, userId = 1, nota = 5, comentario = "Test Comment")
        val user = User(id = 1, nome = "Test", username = "testuser", email = "test@test.com", senha = "password")
        every { userRepository.getUserById(1) } returns user
        every { reviewRepository.addReview(review) } returns review

        // When
        val result = reviewService.addReview(review)

        // Then
        assertEquals(review.copy(username = user.username), result)
    }

    @Test
    fun `getReviewsByBookId should return a list of reviews`() {
        // Given
        val reviews = listOf(Review(id = 1, livroId = 1, userId = 1, nota = 5, comentario = "Test Comment"))
        every { reviewRepository.getReviewsByBookId(1) } returns reviews

        // When
        val result = reviewService.getReviewsByBookId(1)

        // Then
        assertEquals(reviews, result)
    }

    @Test
    fun `deleteReview should delete review`() {
        // Given
        every { reviewRepository.deleteReview(1) } returns true

        // When
        val result = reviewService.deleteReview(1)

        // Then
        assertTrue(result)
    }

    @Test
    fun `updateReview should update review`() {
        // Given
        val review = Review(id = 1, livroId = 1, userId = 1, nota = 5, comentario = "Test Comment")
        every { reviewRepository.updateReview(1, review) } returns review

        // When
        val result = reviewService.updateReview(1, review)

        // Then
        assertEquals(review, result)
    }

    @Test
    fun `getReviewById should return review when review exists`() {
        // Given
        val review = Review(id = 1, livroId = 1, userId = 1, nota = 5, comentario = "Test Comment")
        every { reviewRepository.getReviewById(1) } returns review

        // When
        val result = reviewService.getReviewById(1)

        // Then
        assertEquals(review, result)
    }
}
