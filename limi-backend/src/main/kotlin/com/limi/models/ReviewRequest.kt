package com.limi.models

import kotlinx.serialization.Serializable

@Serializable
data class ReviewRequest(
    val livroId: Int,
    val comentario: String,
    val nota: Int
)
