package com.limi

import com.limi.config.DatabaseFactory
import com.limi.models.*
import io.ktor.client.*
import io.ktor.client.engine.cio.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.serialization.kotlinx.json.json
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.transactions.transaction
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class DatabaseTest {

    private lateinit var client: HttpClient

    @BeforeAll
    fun setup() {
        // Configura o cliente HTTP
        client = HttpClient(CIO) {
            install(ContentNegotiation) {
                json()
            }
        }

        // Configura banco de teste em memória e popula com dados
        DatabaseFactory.init(
            client = client,
            url = "jdbc:h2:mem:test;DB_CLOSE_DELAY=-1",
            driver = "org.h2.Driver"
        )
    }

    @Test
    fun `deve criar autores corretamente`() = transaction {
        // Verifica se autores foram criados
        val autores = AutorEntity.all().toList()
        assertTrue(autores.isNotEmpty()) // Garante que a lista não está vazia

        // Verifica se "J.K. Rowling" existe
        val rowling = AutorEntity.find { Autores.nome eq "J.K. Rowling" }.firstOrNull()
        assertNotNull(rowling)
        assertEquals("J.K. Rowling", rowling?.nome)
    }

    @Test
    fun `deve vincular livro ao autor corretamente`() = transaction {
        val livro = LivroEntity.find { Livros.titulo eq "Harry Potter e a Pedra Filosofal" }.firstOrNull()
        assertNotNull(livro, "Livro 'Harry Potter' não encontrado.")

        val autor = livro!!.autor
        assertEquals("J.K. Rowling", autor.nome)
        assertTrue(autor.livros.any { it.titulo == "Harry Potter e a Pedra Filosofal" })
    }

    @Test
    fun `deve ter generos multiplos`() = transaction {
        val livro = LivroEntity.find { Livros.titulo eq "Capitães de Areia" }.firstOrNull()
        assertNotNull(livro, "Livro 'Capitães de Areia' não encontrado.")

        val generos = livro!!.generos.toList()
        assertTrue(generos.size >= 1, "O livro deveria ter pelo menos um gênero.")
        assertTrue(generos.any { it.nome == "Ficção" })
    }

    @Test
    fun `deve filtrar livros por genero`() = transaction {
        val genero = GeneroEntity.find { Generos.nome eq "Ficção" }.firstOrNull()
        assertNotNull(genero, "Gênero 'Ficção' não encontrado.")

        val livrosFiccao = LivroGenero
            .slice(LivroGenero.livro)
            .select { LivroGenero.genero eq genero!!.id }
            .map { row -> LivroEntity[row[LivroGenero.livro]].titulo }

        assertTrue(livrosFiccao.isNotEmpty(), "Deveria haver livros de ficção.")
    }

    @Test
    fun `deve filtrar livros por autor`() = transaction {
        val autor = AutorEntity.find { Autores.nome eq "Machado de Assis" }.firstOrNull()
        assertNotNull(autor, "Autor 'Machado de Assis' não encontrado.")

        val livrosMachado = LivroEntity.find { Livros.autor eq autor!!.id }.toList()

        assertEquals(1, livrosMachado.size)
        assertEquals("Dom Casmurro", livrosMachado[0].titulo)
    }
}