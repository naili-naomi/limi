package com.limi.services

import com.limi.repositories.LikeRepository

class LikeService(private val likeRepository: LikeRepository) {
    fun addLike(userId: Int, reviewId: Int): Boolean {
        return likeRepository.addLike(userId, reviewId)
    }

    fun removeLike(userId: Int, reviewId: Int): Boolean {
        return likeRepository.removeLike(userId, reviewId)
    }

    fun isLiked(userId: Int, reviewId: Int): Boolean {
        return likeRepository.isLiked(userId, reviewId)
    }
}
