package com.limi.models

import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.sql.Table

object Generos : IntIdTable("generos") {
    val nome = varchar("nome", 100).uniqueIndex()
}

class GeneroEntity(id: EntityID<Int>) : IntEntity(id) {
    companion object : IntEntityClass<GeneroEntity>(Generos)
    var nome by Generos.nome
}

// Tabela de junção para relacionamentos entre livro e genêro
object LivroGenero : Table("livro_genero") {
    val livro = reference("livro_id", Livros)
    val genero = reference("genero_id", Generos)
    override val primaryKey = PrimaryKey(livro, genero, name = "PK_Livro_Genero")
}