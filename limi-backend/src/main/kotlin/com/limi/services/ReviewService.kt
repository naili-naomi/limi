package services

import com.limi.models.Review

class ReviewService {
    private val reviews = mutableListOf<Review>()

    fun adicionarReview(review: Review) {
        reviews.add(review)
    }

    fun buscarReviewsPorLivro(livro: String): List<Review> {
        return reviews.filter { it.livroId.equals(livro, ignoreCase = true) }
    }

    fun listarReviews(): List<Review> = reviews
}
