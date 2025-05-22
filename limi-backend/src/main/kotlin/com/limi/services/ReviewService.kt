package com.limi.services

import com.limi.models.Review
import com.limi.repositories.ReviewRepository
import com.limi.validation.validateForCreation


class ReviewService(private val reviewRepository: ReviewRepository) {
    fun adicionarReview(review: Review): Review {

        review.validateForCreation()
        return reviewRepository.addReview(review)
    }

    fun buscarReviewsPorLivro(livroId: Int): List<Review> {
        return reviewRepository.getReviewsByLivroId(livroId)
    }

    fun listarReviews(livroId: Int): List<Review> {
        return reviewRepository.getReviewsByLivroId(livroId) // Ajuste conforme necessidade
    }

    fun atualizarReview(id: Int, novoComentario: String, novaNota: Int): Review? {
        return reviewRepository.updateReview(id, novoComentario, novaNota)
    }

    fun deletarReview(id: Int): Boolean {
        return reviewRepository.deleteReview(id)
    }

}