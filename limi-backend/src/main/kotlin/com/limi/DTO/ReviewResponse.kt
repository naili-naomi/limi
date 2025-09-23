package com.limi.DTO

import kotlinx.serialization.Serializable

@Serializable
data class ReviewResponse(
    val id: Int,
    val livroId: Int,
    val userId: Int,
    val username: String,
    val review: String,
    val nota: Int,
    val likesCount: Long,
    val isLiked: Boolean
)
