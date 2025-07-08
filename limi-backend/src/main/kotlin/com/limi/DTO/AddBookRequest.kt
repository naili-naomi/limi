package com.limi.DTO

import kotlinx.serialization.Serializable

@Serializable
data class AddBookRequest(
    val titulo: String,
    val generos: List<String>
)