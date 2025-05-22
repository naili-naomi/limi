package com.limi.services

import com.limi.models.User
import com.limi.repositories.UserRepository
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotNull
import kotlin.test.assertNull
import kotlin.test.*
import com.limi.exceptions.*


class UserServiceTest {
    private val userRepository = mockk<UserRepository>() // Mock
    private val service = UserService(userRepository)

    @Test
    fun adicionaEBuscaPorEmail() {
        // Configura mock
        val user = User(1, "Ana", "aninha01", "ana@exemplo.com", "Ana")
        every { userRepository.buscarPorEmail("ana@exemplo.com") } returns user

        val buscado = service.buscarPorEmail("ana@exemplo.com")

        assertNotNull(buscado)
        assertEquals("Ana", buscado.nome)
    }

    @Test
    fun buscaUserInexistente() {
        every { userRepository.buscarPorEmail(any()) } returns null

        val buscado = service.buscarPorEmail("naoexiste@teste.com")
        assertNull(buscado)
    }
    @Test
    fun `atualizarUser deve atualizar quando usuário existir e email único`() {
        val existing = User(1, "Ana", "ana01", "ana@ex.com", "senha123")
        val updated = User(1, "Ana Maria", "anamaria", "ana@ex.com", "novaSenha")

        // buscarPorEmail retorna null (pois mesmo email pertence ao próprio id)
        every { userRepository.buscarPorEmail(updated.email) } returns existing
        every { userRepository.updateUser(1, updated) } returns updated

        val result = service.atualizarUser(1, updated)
        assertEquals("Ana Maria", result.nome)
        verify(exactly = 1) { userRepository.updateUser(1, updated) }
    }

    @Test
    fun `atualizarUser deve falhar se email já usado por outro`() {
        val existing = User(2, "Carlos", "carlos01", "carlos@ex.com", "senha")
        val toUpdate = User(1, "João", "joao01", "carlos@ex.com", "senha123")

        every { userRepository.buscarPorEmail(toUpdate.email) } returns existing

        assertFailsWith<ValidationException> {
            service.atualizarUser(1, toUpdate)
        }
    }

    @Test
    fun `atualizarUser deve falhar com NotFoundException quando id não existir`() {
        val updated = User(99, "Fulano", "fulano", "fulano@ex.com", "pwd123")

        every { userRepository.buscarPorEmail(any()) } returns null
        every { userRepository.updateUser(99, updated) } returns null

        assertFailsWith<NotFoundException> {
            service.atualizarUser(99, updated)
        }
    }

    @Test
    fun `deletarUser deve retornar true quando usuário removido`() {
        every { userRepository.deleteUser(5) } returns true

        val result = service.deletarUser(5)
        assertTrue(result)
        verify(exactly = 1) { userRepository.deleteUser(5) }
    }

    @Test
    fun `deletarUser deve retornar false quando usuário não existir`() {
        every { userRepository.deleteUser(99) } returns false

        val result = service.deletarUser(99)
        assertTrue(result == false)
        verify(exactly = 1) { userRepository.deleteUser(99) }
    }


}