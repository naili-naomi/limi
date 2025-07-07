package com.limi.DTO

import kotlinx.serialization.Serializable

@Serializable
data class ReviewUpdateRequest(
    val comentario: String,
    val nota: Int
)