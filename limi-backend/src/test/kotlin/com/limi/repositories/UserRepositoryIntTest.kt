package com.limi.repositories

import com.limi.config.DatabaseFactory
import com.limi.models.User
import io.ktor.client.* 
import io.ktor.client.engine.cio.* 
import io.ktor.client.plugins.contentnegotiation.* 
import io.ktor.serialization.kotlinx.json.json
import org.jetbrains.exposed.sql.transactions.transaction
import org.junit.jupiter.api.*
import kotlin.test.assertEquals
import kotlin.test.assertNotNull
import kotlin.test.assertNull
import kotlin.test.assertTrue

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class UserRepositoryIntTest {

    private lateinit var repo: UserRepository

    @BeforeAll
    fun setup() {
        val client = HttpClient(CIO) {
            install(ContentNegotiation) {
                json()
            }
        }
        // Conecta ao banco H2 em memória
        DatabaseFactory.init(
            client = client,
            url = "jdbc:h2:mem:test-users;DB_CLOSE_DELAY=-1", 
            driver = "org.h2.Driver"
        )
        repo = UserRepository()
    }

    @Test
    fun `deve adicionar e recuperar usuário por email`() {
        val user = User(
            id = 5,
            nome = "Maria",
            username = "mariazinha",
            email = "maria@teste.com",
            senha = "123456"
        )

        val criado = repo.addUser(user)
        assertNotNull(criado.id)

        val buscado = repo.buscarPorEmail("maria@teste.com")
        assertNotNull(buscado)
        assertEquals("Maria", buscado.nome)
        assertEquals("mariazinha", buscado.username)
    }

    @Test
    fun `buscar usuario inexistente deve retornar null`() {
        val resultado = repo.buscarPorEmail("naoexiste@exemplo.com")
        assertNull(resultado)
    }

    @Test
    fun `updateUser persiste alterações corretamente`() {
        // cria usuário inicial
        val original = repo.addUser(User(0, "Pedro", "pedro01", "pedro@ex.com", "senha123"))
        val updatedData = User(original.id, "Pedro Silva", "pedrosilva", "pedro@ex.com", "novaSenha")

        val updated = repo.updateUser(original.id, updatedData)
        assertNotNull(updated)
        assertEquals("Pedro Silva", updated!!.nome)
        assertEquals("pedrosilva", updated.username)
    }

    @Test
    fun `getUserById retorna null após deleteUser`() {
        // cria e deleta
        val user = repo.addUser(User(0, "Marta", "marta01", "marta@ex.com", "senha321"))
        val deleted = repo.deleteUser(user.id)
        assertTrue(deleted)

        val fetched = repo.getUserById(user.id)
        assertNull(fetched)
    }
}