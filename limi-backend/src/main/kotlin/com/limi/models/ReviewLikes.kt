package com.limi.models

import org.jetbrains.exposed.dao.id.IntIdTable

object ReviewLikes : IntIdTable() {
    val user = reference("user_id", Users)
    val review = reference("review_id", Reviews)
}