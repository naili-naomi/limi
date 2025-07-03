package com.limi.models

import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IntIdTable
import kotlinx.serialization.Serializable
import com.limi.clients.gerarImagemMockada

object Livros : IntIdTable("livros") {
    val titulo = varchar("titulo", 255)
    val autor = reference("autor_id", Autores)
    val anoPublicacao = integer("ano_publicacao")
    val sinopse = text("sinopse")
    val urlImagem = varchar("url_imagem", 512).nullable()

    //  val genero = varchar("genero", 100).nullable()
}

class LivroEntity(id: EntityID<Int>) : IntEntity(id) {
    companion object : IntEntityClass<LivroEntity>(Livros)

    var titulo by Livros.titulo
    var autor by AutorEntity referencedOn Livros.autor
    var anoPublicacao by Livros.anoPublicacao
    var sinopse by Livros.sinopse
    var urlImagem by Livros.urlImagem
    val generos by GeneroEntity via LivroGenero


    fun toLivro() = Livro(
        id = id.value,
        titulo = titulo,
        autor = autor.nome,
        anoPublicacao = anoPublicacao,
        sinopse = sinopse,
        urlImagem = urlImagem,
        generos = generos.map { it.nome }
    )
}

@Serializable
data class Livro(
    val id: Int,
    val titulo: String,
    val autor: String,
    val anoPublicacao: Int,
    val sinopse: String,
    val urlImagem: String? = null,
    val generos: List<String>
)