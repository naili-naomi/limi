package com.limi.repositories

import com.limi.config.DatabaseFactory
import com.limi.config.DatabaseSeed
import com.limi.models.*
import io.ktor.client.* 
import io.ktor.client.engine.cio.* 
import io.ktor.client.plugins.contentnegotiation.* 
import io.ktor.serialization.kotlinx.json.json
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.transaction
import org.junit.jupiter.api.*
import org.junit.jupiter.api.Assertions.assertTrue
import java.util.*
import kotlin.test.assertEquals
import kotlin.test.assertNotNull

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class LivroRepositoryIntTest {
    private lateinit var repo: LivroRepository
    private lateinit var testDb: Database
    private lateinit var client: HttpClient

    @BeforeAll
    fun setupDb() {
        client = HttpClient(CIO) {
            install(ContentNegotiation) {
                json()
            }
        }
        testDb = Database.connect(
            "jdbc:h2:mem:test_${UUID.randomUUID()};DB_CLOSE_DELAY=-1",
            driver = "org.h2.Driver"
        )
    }

    @BeforeEach
    fun setup() {
        repo = LivroRepository()
        transaction(testDb) {
            SchemaUtils.create(Autores, Users, Generos, Livros, LivroGenero, Reviews)
            DatabaseSeed.seedLivrosIniciais(client)
        }
    }

    @AfterEach
    fun teardown() {
        transaction(testDb) {
            SchemaUtils.drop(LivroGenero, Generos, Reviews, Livros, Autores, Users)
        }
    }

    @Test
    fun `addLivro persiste e getLivroById retorna o mesmo`() {
        val input = Livro(
            id = 0,
            titulo = "Test Title",
            autor = "Test Author",
            anoPublicacao = 2025,
            sinopse = "Sinopse",
            generos = listOf("G1", "G2")
        )
        val criado = repo.addLivro(input)
        assertNotNull(criado.id)
        val buscado = repo.getLivroById(criado.id)
        assertNotNull(buscado)
        assertEquals("Test Title", buscado.titulo)
        assertEquals(listOf("G1", "G2"), buscado.generos)
    }

    @Test
    fun `listarTodos retorna ao menos os seedados`() {
        val todos = repo.getAllLivros()
        assertTrue(todos.size >= 1)
    }
}