package com.limi.repositories

import com.limi.models.ReviewLikes
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.and
import org.jetbrains.exposed.sql.deleteWhere
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.transactions.transaction
import org.jetbrains.exposed.sql.select

class LikeRepository {

    fun addLike(userId: Int, reviewId: Int): Boolean = transaction {
        val existing = ReviewLikes.select(ReviewLikes.user eq userId and (ReviewLikes.review eq reviewId)).count()

        if (existing == 0L) {
            ReviewLikes.insert { it[user] = userId; it[review] = reviewId }
            true
        } else {
            false // Already liked
        }
    }

    fun removeLike(userId: Int, reviewId: Int): Boolean = transaction {
        ReviewLikes.deleteWhere { user eq userId and (review eq reviewId) } > 0
    }

    fun isLiked(userId: Int, reviewId: Int): Boolean = transaction {
        ReviewLikes.select(ReviewLikes.user eq userId and (ReviewLikes.review eq reviewId)).count() > 0
    }
}
