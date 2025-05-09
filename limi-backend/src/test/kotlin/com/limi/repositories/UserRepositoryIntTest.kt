package com.limi.repositories

import com.limi.config.DatabaseFactory
import com.limi.models.User
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.transactions.transaction
import org.junit.jupiter.api.*
import kotlin.test.assertEquals
import kotlin.test.assertNotNull
import kotlin.test.assertNull

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class UserRepositoryIntTest {

    private lateinit var repo: UserRepository

    @BeforeAll
    fun setup() {
        // Conecta ao banco H2 em memória
        Database.connect("jdbc:h2:mem:test-users;DB_CLOSE_DELAY=-1", driver = "org.h2.Driver")
        transaction { // ✅ Garante que tudo aconteça em uma transação única
            DatabaseFactory.init()
        }
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
}
