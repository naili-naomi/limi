package com.limi.services

import com.limi.models.Review
import com.limi.repositories.ReviewRepository
import com.limi.repositories.UserRepository
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class ReviewServiceTest {
    private val reviewRepository = mockk<ReviewRepository>()
    private val userRepository = mockk<UserRepository>()
    private val reviewService = ReviewService(reviewRepository, userRepository)

    @Test
    fun `addReview should return review with same id`() {
        val input = Review(id = 0, livroId = 1, userId = 1, comentario = "OK", nota = 4)
        val saved = input.copy(id = 42)
        every { reviewRepository.addReview(input) } returns saved

        val resultado = reviewService.addReview(input)

        assertEquals(42, resultado.id)
        assertEquals(input.comentario, resultado.comentario)
        verify(exactly = 1) { reviewRepository.addReview(input) }
    }

    @Test
    fun `getReviewsByBookId should delegate to repository`() {
        val lista = listOf(
            Review(id = 1, livroId = 5, userId = 2, comentario = "A", nota = 3),
            Review(id = 2, livroId = 5, userId = 3, comentario = "B", nota = 5)
        )
        every { reviewRepository.getReviewsByBookId(5) } returns lista

        val result = reviewService.getReviewsByBookId(5)

        assertEquals(2, result.size)
        assertTrue(result.all { it.livroId == 5 })
        verify { reviewRepository.getReviewsByBookId(5) }
    }

    @Test
    fun `should return true when deleting an existing review`() {
        // given
        every { reviewRepository.deleteReview(42) } returns true

        // when
        val resultado = reviewService.deleteReview(42)

        // then
        assertTrue(resultado)
        verify(exactly = 1) { reviewRepository.deleteReview(42) }
    }

    @Test
    fun `should return false when trying to delete a non-existing review`() {

        every { reviewRepository.deleteReview(99) } returns false


        val resultado = reviewService.deleteReview(99)


        assertFalse(resultado)
        verify(exactly = 1) { reviewRepository.deleteReview(99) }
    }

    @Test
    fun `updateReview should return updated review`() {
        val review = Review(id = 1, livroId = 1, userId = 1, comentario = "Updated", nota = 5)
        every { reviewRepository.updateReview(1, review) } returns review

        val result = reviewService.updateReview(1, review)

        assertEquals("Updated", result?.comentario)
        assertEquals(5, result?.nota)
        verify(exactly = 1) { reviewRepository.updateReview(1, review) }
    }
}
