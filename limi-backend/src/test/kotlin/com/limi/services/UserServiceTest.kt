package com.limi.services

import com.limi.models.User
import com.limi.validation.validateForCreation
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
    private val userRepository = mockk<UserRepository>(relaxed = true)
    // Mock
    private val service = UserService(userRepository)

    @Test
    fun `buscarPorEmail deve retornar usuario existente`() {
        val usuario = User(1, "Ana", "ana01", "ana@ex.com", "senha123")
        every { userRepository.buscarPorEmail("ana@ex.com") } returns usuario

        val resultado = service.buscarPorEmail("ana@ex.com")
        assertEquals(1, resultado?.id)
        assertEquals("Ana", resultado?.nome)
    }

    @Test
    fun `buscarPorEmail deve retornar nulo quando usuario nao existe`() {
        every { userRepository.buscarPorEmail(any()) } returns null

        val resultado = service.buscarPorEmail("inexistente@ex.com")
        assertEquals(null, resultado)
    }

    @Test
    fun `adicionarUser deve salvar usuario com senha hashada`() {
        val novo = User(
            id = 0,
            nome = "Teste",
            username = "teste01",
            email = "teste@ex.com",
            senha = "senha123"
        )
        // não existe usuário com esse email
        every { userRepository.buscarPorEmail(novo.email) } returns null
        // mock adiciona e retorna com id = 1
        every { userRepository.addUser(match { it.senha.startsWith("$2a$") }) } returns novo.copy(id = 1)

        val resultado = service.adicionarUser(novo)

        verify(exactly = 1) { userRepository.addUser(any()) }
        assertEquals(1, resultado.id)
        assertEquals(novo.email, resultado.email)
    }

    @Test
    fun `adicionarUser deve falhar se email ja existir`() {
        val existente = User(1, "Outro", "outro01", "teste@ex.com", "senha123")
        every { userRepository.buscarPorEmail(existente.email) } returns existente

        assertFailsWith<ValidationException> {
            service.adicionarUser(existente)
        }
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