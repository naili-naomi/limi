package com.limi.services

import com.limi.models.User
import com.limi.repositories.UserRepository
import io.mockk.every
import io.mockk.mockk
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotNull
import kotlin.test.assertNull

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
}