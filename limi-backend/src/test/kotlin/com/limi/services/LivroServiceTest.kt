package com.limi.services

import com.limi.models.Livro
import com.limi.repositories.LivroRepository
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class LivroServiceTest {
    private lateinit var livroService: LivroService
    private val livroRepository = LivroRepository()

    @BeforeEach
    fun setup() {
        livroService = LivroService(livroRepository)
    }

    @Test
    fun `deve adicionar um livro corretamente`() {
        val livro = Livro(
            id = 1,
            titulo = "1984",
            autor = "George Orwell",
            anoPublicacao = 1949,
            sinopse = "Distopia",
            generos = listOf("Ficção") // Corrigido para generos
        )
        livroService.adicionarLivro(livro)

        val resultado = livroService.listarLivros()
        assertEquals(1, resultado.size)
    }
}