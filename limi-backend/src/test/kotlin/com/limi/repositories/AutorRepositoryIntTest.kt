package com.limi.repositories

import com.limi.config.DatabaseFactory
import com.limi.models.Autor
import io.ktor.client.* 
import io.ktor.client.engine.cio.* 
import io.ktor.client.plugins.contentnegotiation.* 
import io.ktor.serialization.kotlinx.json.json
import org.junit.jupiter.api.*
import kotlin.test.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import kotlin.test.assertNotNull

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class AutorRepositoryIntTest {
    private lateinit var repo: AutorRepository

    @BeforeAll
    fun setup() {
        val client = HttpClient(CIO) {
            install(ContentNegotiation) {
                json()
            }
        }
        DatabaseFactory.init(
            client = client,
            url = "jdbc:h2:mem:test_autor;DB_CLOSE_DELAY=-1", 
            driver = "org.h2.Driver"
        )
    }

    @BeforeEach
    fun initRepo() {
        repo = AutorRepository()
    }

    @Test
    fun `adiciona autor e recupera por id`() {
        val autor = Autor(
            id = 20,
            nome = "Isaac Asimov",
        )

        val criado = repo.addAutor(autor)
        assertNotNull(criado.id)

        val buscado = repo.getAutorById(criado.id)
        assertNotNull(buscado)
        assertEquals("Isaac Asimov", buscado.nome)
    }

    @Test
    fun `listar todos retorna lista não vazia após adição`() {
        val autor = Autor(21, "Arthur C. Clarke")
        repo.addAutor(autor)

        val lista = repo.getAllAutores()
        assertTrue(lista.isNotEmpty())
    }
}