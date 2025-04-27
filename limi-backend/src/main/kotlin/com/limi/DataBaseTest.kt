package com.limi

import com.limi.config.DatabaseFactory
import com.limi.config.DatabaseSeed
import com.limi.models.*
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.transactions.transaction
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.api.Assertions.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.and
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.deleteWhere

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class DatabaseTest {

    @BeforeAll
    fun setup() {
        // Configura banco de teste em memória
        Database.connect("jdbc:h2:mem:test;DB_CLOSE_DELAY=-1", driver = "org.h2.Driver")
        DatabaseFactory.init()
        DatabaseSeed.seedLivrosIniciais()
    }

    @Test
    fun `deve criar autores corretamente`() = transaction {
        // Verifica se autores foram criados
        val autores = AutorEntity.all().toList()
        assertTrue(autores.size >= 5) // Ajuste conforme seus dados de seed

        // Verifica se "J.K. Rowling" existe
        val rowling = AutorEntity.find { Autores.nome eq "J.K. Rowling" }.first()
        assertEquals("J.K. Rowling", rowling.nome)
    }

    @Test
    fun `deve vincular livro ao autor corretamente`() = transaction {
        val livro = LivroEntity.find { Livros.titulo eq "Harry Potter e a Pedra Filosofal" }.first()
        val autor = livro.autor

        assertEquals("J.K. Rowling", autor.nome)
        assertTrue(autor.livros.toList().any { it.titulo == "Harry Potter e a Pedra Filosofal" })
    }

    @Test
    fun `deve ter generos multiplos`() = transaction {
        val livro = LivroEntity.find { Livros.titulo eq "Capitães de Areia" }.first()
        val generos = livro.generos.toList()

        assertEquals(2, generos.size) // Romance + Drama
        assertTrue(generos.any { it.nome == "Romance" })
    }

    @Test
    fun `deve filtrar livros por genero`() = transaction {
        val genero = GeneroEntity.find { Generos.nome eq "Fantasia" }.first()
        val livrosFantasia = LivroGenero
            .slice(LivroGenero.livro)
            .select { LivroGenero.genero eq genero.id }
            .map { row ->
                LivroEntity[row[LivroGenero.livro]].titulo
            }

        assertTrue(livrosFantasia.contains("Harry Potter e a Pedra Filosofal"))
    }

    @Test
    fun `deve filtrar livros por autor`() = transaction {
        val autor = AutorEntity.find { Autores.nome eq "Machado de Assis" }.first()
        val livrosMachado = LivroEntity.find { Livros.autor eq autor.id }.toList()

        assertEquals(1, livrosMachado.size)
        assertEquals("Dom Casmurro", livrosMachado[0].titulo)
    }
}