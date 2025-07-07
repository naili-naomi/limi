package com.limi.models

import org.jetbrains.exposed.sql.Table

object ReviewLikes : Table("review_likes") {
    val review = reference("review_id", Reviews)
    val user = reference("user_id", Users)
    override val primaryKey = PrimaryKey(review, user, name = "PK_Review_Likes")
}
