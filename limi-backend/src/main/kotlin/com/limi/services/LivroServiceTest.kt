package com.limi.services

import com.limi.models.Livro
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import services.LivroService

class LivroServiceTest {

    private lateinit var livroService: LivroService

    @BeforeEach
    fun setup() {
        livroService = LivroService()
    }

    @Test
    fun `deve adicionar um livro corretamente`() {
        val livro = Livro(id = 1, titulo = "1984", autor = "George Orwell", 1984, "O COMUNISMO!")
        livroService.adicionarLivro(livro)

        val resultado = livroService.listarLivros()
        assertEquals(1, resultado.size)
        assertEquals("1984", resultado[0].titulo)
    }

    @Test
    fun `deve retornar null se livro nao for encontrado`() {
        val resultado = livroService.buscarLivroPorId(999)
        assertNull(resultado)
    }
}
