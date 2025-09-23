package com.limi.services

import com.limi.DTO.ReviewResponse
import com.limi.models.Review
import com.limi.repositories.LikeRepository
import com.limi.repositories.ReviewRepository
import com.limi.repositories.UserRepository

class ReviewService(
    private val reviewRepository: ReviewRepository,
    private val userRepository: UserRepository,
    private val likeRepository: LikeRepository
) {
    fun addReview(review: Review): Review {
        val user = userRepository.getUserById(review.userId)
        val addedReview = reviewRepository.addReview(review)
        return addedReview.copy(username = user?.username)
    }

    fun getReviewsByBookId(bookId: Int, userId: Int?): List<ReviewResponse> {
        val reviews = reviewRepository.getReviewsByBookId(bookId)
        return reviews.map { review ->
            val likesCount = likeRepository.getLikesCount(review.id)
            val isLiked = userId?.let { likeRepository.isLiked(it, review.id) } ?: false
            ReviewResponse(
                id = review.id,
                livroId = review.livroId,
                userId = review.userId,
                username = review.username ?: "",
                review = review.review,
                nota = review.nota,
                likesCount = likesCount,
                isLiked = isLiked
            )
        }
    }

    fun deleteReview(reviewId: Int): Boolean {
        return reviewRepository.deleteReview(reviewId)
    }

    fun updateReview(reviewId: Int, review: Review): Review? {
        return reviewRepository.updateReview(reviewId, review)
    }

    fun getReviewById(reviewId: Int): Review? {
        return reviewRepository.getReviewById(reviewId)
    }
}