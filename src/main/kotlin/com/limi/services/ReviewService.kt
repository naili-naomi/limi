package com.limi.services

import com.limi.models.Review
import com.limi.repositories.ReviewRepository

class ReviewService(private val reviewRepository: ReviewRepository) {
    fun adicionarReview(review: Review): Review {
        return reviewRepository.addReview(review)
    }

    fun buscarReviewsPorLivro(livroId: Int): List<Review> {
        return reviewRepository.getReviewsByLivroId(livroId)
    }

    fun listarReviews(): List<Review> {
        return reviewRepository.getReviewsByLivroId(1) // Ajuste conforme necessidade
    }

    fun atualizarReview(id: Int, novoComentario: String, novaNota: Int): Review? {
        return reviewRepository.updateReview(id, novoComentario, novaNota)
    }
}