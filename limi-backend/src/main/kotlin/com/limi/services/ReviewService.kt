package com.limi.services

import com.limi.models.Review
import com.limi.repositories.ReviewRepository
import com.limi.repositories.UserRepository

class ReviewService(private val reviewRepository: ReviewRepository, private val userRepository: UserRepository) {
    fun addReview(review: Review): Review {
        val user = userRepository.getUserById(review.userId)
        val addedReview = reviewRepository.addReview(review)
        return addedReview.copy(username = user?.username)
    }

    fun getReviewsByBookId(bookId: Int): List<Review> {
        return reviewRepository.getReviewsByBookId(bookId)
    }

    fun deleteReview(reviewId: Int): Boolean {
        return reviewRepository.deleteReview(reviewId)
    }

    fun updateReview(reviewId: Int, review: Review): Review? {
        return reviewRepository.updateReview(reviewId, review)
    }
}