package com.limi.DTO

import kotlinx.serialization.Serializable

@Serializable
data class UpdatePasswordRequest(
    val currentPassword: String,
    val newPassword: String
)
