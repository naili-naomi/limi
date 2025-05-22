package com.limi.repositories

import com.limi.models.Review
import com.limi.models.ReviewEntity
import com.limi.models.Reviews
import org.jetbrains.exposed.sql.and
import org.jetbrains.exposed.sql.transactions.transaction

class ReviewRepository {
    fun getReviewsByLivroId(livroId: Int): List<Review> = transaction {
        ReviewEntity.find { Reviews.livroId eq livroId }.map { it.toReview() }
    }

    fun addReview(review: Review): Review = transaction {
        ReviewEntity.new {
            this.livroId = review.livroId
            userId = review.userId
            comentario = review.comentario
            nota = review.nota
        }.toReview()
    }

    fun updateReview(id: Int, novoComentario: String, novaNota: Int): Review? = transaction {
        ReviewEntity.findById(id)?.apply {
            comentario = novoComentario
            nota = novaNota
        }?.toReview()
    }

    fun deleteReview(id: Int): Boolean = transaction {
        val review = ReviewEntity.findById(id)
        if (review != null) {
            review.delete()
            true
        } else {
            false
        }
    }

}