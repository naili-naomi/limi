package com.limi.services

import com.limi.models.Autor
import com.limi.repositories.AutorRepository
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class AutorServiceTest {
    private val autorRepository = mockk<AutorRepository>()
    private val autorService = AutorService(autorRepository)

    @Test
    fun `adicionarAutor deve chamar repository`() {
        val novo = Autor(id = 0, nome = "Test")
        every { autorRepository.addAutor(novo) } returns novo

        autorService.adicionarAutor(novo)

        verify(exactly = 1) { autorRepository.addAutor(novo) }
    }

    @Test
    fun `listarAutores deve retornar lista do repository`() {
        val lista = listOf(Autor(1, "A"), Autor(2, "B"))
        every { autorRepository.getAllAutores() } returns lista

        val result = autorService.listarAutores()

        assertEquals(2, result.size)
        assertEquals("A", result[0].nome)
        verify { autorRepository.getAllAutores() }
    }
}
