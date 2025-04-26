package com.limi.models

import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IntIdTable
import kotlinx.serialization.Serializable

object Livros : IntIdTable("livros") {
    val titulo = varchar("titulo", 255)
    val autor = varchar("autor", 255)
    val anoPublicacao = integer("ano_publicacao")
    val sinopse = text("sinopse")
    val genero = varchar("genero", 100).nullable()
}

class LivroEntity(id: EntityID<Int>) : IntEntity(id) {
    companion object : IntEntityClass<LivroEntity>(Livros)

    var titulo by Livros.titulo
    var autor by Livros.autor
    var anoPublicacao by Livros.anoPublicacao
    var sinopse by Livros.sinopse
    var genero by Livros.genero

    fun toLivro() = Livro(
        id.value,
        titulo,
        autor,
        anoPublicacao,
        sinopse,
        genero
    )
}

@Serializable
data class Livro(
    val id: Int,
    val titulo: String,
    val autor: String,
    val anoPublicacao: Int,
    val sinopse: String,
    val genero: String? = null
)