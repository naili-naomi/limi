package com.limi.models

import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.sql.ReferenceOption
import kotlinx.serialization.Serializable

object Reviews : IntIdTable("reviews") {
    val livroId = integer("livro_id").references(Livros.id, onDelete = ReferenceOption.CASCADE)
    val userId = integer("user_id").references(Users.id, onDelete = ReferenceOption.CASCADE)
    val comentario = text("comentario")
    val nota = integer("nota").check { it.between(1, 5) }
}

class ReviewEntity(id: EntityID<Int>) : IntEntity(id) {
    companion object : IntEntityClass<ReviewEntity>(Reviews)

    var livroId by Reviews.livroId
    var userId by Reviews.userId
    var comentario by Reviews.comentario
    var nota by Reviews.nota

    fun toReview() = Review(
        id.value,
        livroId,
        userId,
        comentario,
        nota
    )
}

@Serializable
data class Review(
    val id: Int? = null,
    val livroId: Int,
    val userId: Int,
    val comentario: String,
    val nota: Int
)