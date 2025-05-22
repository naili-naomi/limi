package com.limi.services

import com.limi.models.Review
import com.limi.repositories.ReviewRepository
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class ReviewServiceTest {
    private val reviewRepository = mockk<ReviewRepository>()
    private val reviewService = ReviewService(reviewRepository)

    @Test
    fun `adicionarReview deve retornar review com mesmo id`() {
        val input = Review(id = 0, livroId = 1, userId = 1, comentario = "OK", nota = 4)
        val saved = input.copy(id = 42)
        every { reviewRepository.addReview(input) } returns saved

        val resultado = reviewService.adicionarReview(input)

        assertEquals(42, resultado.id)
        assertEquals(input.comentario, resultado.comentario)
        verify(exactly = 1) { reviewRepository.addReview(input) }
    }

    @Test
    fun `buscarReviewsPorLivro deve delegar ao repository`() {
        val lista = listOf(
            Review(id = 1, livroId = 5, userId = 2, comentario = "A", nota = 3),
            Review(id = 2, livroId = 5, userId = 3, comentario = "B", nota = 5)
        )
        every { reviewRepository.getReviewsByLivroId(5) } returns lista

        val result = reviewService.buscarReviewsPorLivro(5)

        assertEquals(2, result.size)
        assertTrue(result.all { it.livroId == 5 })
        verify { reviewRepository.getReviewsByLivroId(5) }
    }

    @Test
    fun `deve retornar true ao deletar review existente`() {
        // dado
        every { reviewRepository.deleteReview(42) } returns true

        // quando
        val resultado = reviewService.deletarReview(42)

        // então
        assertTrue(resultado)
        verify(exactly = 1) { reviewRepository.deleteReview(42) }
    }

    @Test
    fun `deve retornar false ao tentar deletar review inexistente`() {

        every { reviewRepository.deleteReview(99) } returns false


        val resultado = reviewService.deletarReview(99)


        assertFalse(resultado)
        verify(exactly = 1) { reviewRepository.deleteReview(99) }
    }
}
