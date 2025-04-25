package com.limi.models

data class Book(
    val id: Int,
    val titulo: String,
    val autor: String,
    val anoPublicacao: Int,
    val sinopse: String
)