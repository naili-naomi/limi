package com.limi.models

data class Review(
    val id: Int,
    val livroId: Int,
    val usuarioId: Int,
    val nota: Int,
    val comentario: String
)
