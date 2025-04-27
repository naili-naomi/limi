package com.limi.models

import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IntIdTable
import kotlinx.serialization.Serializable

// Data Class (DTO)
@Serializable
data class Autor(
    val id: Int,
    val nome: String
)

// Tabela
object Autores : IntIdTable("autores") {
    val nome = varchar("nome", 255)
}

// Entidade
class AutorEntity(id: EntityID<Int>) : IntEntity(id) {
    companion object : IntEntityClass<AutorEntity>(Autores)

    var nome by Autores.nome
    val livros by LivroEntity referrersOn Livros.autor

}