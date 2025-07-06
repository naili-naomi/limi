package com.limi.repositories

import com.limi.models.Review
import com.limi.models.ReviewEntity
import com.limi.models.Reviews
import com.limi.models.Users
import org.jetbrains.exposed.sql.transactions.transaction
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.and
import org.jetbrains.exposed.sql.select

class ReviewRepository {
    fun addReview(review: Review) = transaction {
        ReviewEntity.new {
            this.livroId = review.livroId
            this.userId = review.userId
            this.comentario = review.comentario
            this.nota = review.nota
        }.toReview()
    }

    fun getReviewsByBookId(bookId: Int): List<Review> = transaction {
        (Reviews innerJoin Users) 
            .slice(Reviews.columns + Users.username)
            .select { Reviews.livroId eq bookId }
            .map { row ->
                Review(
                    id = row[Reviews.id].value,
                    livroId = row[Reviews.livroId],
                    userId = row[Reviews.userId],
                    comentario = row[Reviews.comentario],
                    nota = row[Reviews.nota],
                    username = row[Users.username]
                )
            }
    }

    fun deleteReview(reviewId: Int): Boolean = transaction {
        val review = ReviewEntity.findById(reviewId)
        if (review != null) {
            review.delete()
            true
        } else {
            false
        }
    }

    fun updateReview(reviewId: Int, review: Review): Review? = transaction {
        val reviewEntity = ReviewEntity.findById(reviewId)
        if (reviewEntity != null) {
            reviewEntity.comentario = review.comentario
            reviewEntity.nota = review.nota
            reviewEntity.toReview()
        } else {
            null
        }
    }
}
