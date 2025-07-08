package com.limi.models

import kotlinx.serialization.Serializable

@Serializable
data class NewLivro(
    val titulo: String,
    val autor: String,
    val anoPublicacao: Int,
    val sinopse: String,
    val urlImagem: String? = null,
    val generos: List<String>
)