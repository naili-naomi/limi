package com.limi.services

import com.limi.models.User
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import com.limi.services.UserService
import com.limi.repositories.UserRepository

class UserServiceTest {

    private lateinit var service: UserService

    @BeforeEach
    fun setup() {
        service = UserService(UserRepository())
    }

    @Test
    fun adicionaEBuscaPorEmail() {
        val user = User(1,"Ana", "aninha01","ana@exemplo.com", "Ana")
        service.adicionarUser(user)

        val buscado = service.buscarPorEmail("ana@exemplo.com")
        assertNotNull(buscado)
        assertEquals("Ana", buscado?.nome)
    }

    @Test
    fun buscaUserInexistente() {
        assertNull(service.buscarPorEmail("naoexiste@teste.com"))
    }
}
