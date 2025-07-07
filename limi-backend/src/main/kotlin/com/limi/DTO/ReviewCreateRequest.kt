package com.limi.models

import kotlinx.serialization.Serializable

@Serializable
data class ReviewCreateRequest(
    val comentario: String,
    val nota: Int
)
