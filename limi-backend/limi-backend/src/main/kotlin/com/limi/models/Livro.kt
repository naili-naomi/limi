package com.limi.models

import kotlinx.serialization.Serializable

@Serializable
data class Livro(
    val id: Int,
    val titulo: String,
    val autor: String,
    val anoPublicacao: Int,
    val sinopse: String
)
