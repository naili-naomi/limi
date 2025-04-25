package com.limi.models

import kotlinx.serialization.Serializable

@Serializable
data class Review(
    val id: Int,
    val livroId: String,  // ou Int se preferir o ID
    val userId: String,
    var comentario: String,
    val nota: Int
)

{
    fun alterarReview(novaReview: String): String {
        comentario = novaReview
        return comentario
    }
}
