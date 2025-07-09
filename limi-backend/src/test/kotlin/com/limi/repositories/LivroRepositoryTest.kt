package com.limi.repositories

import com.limi.DatabaseConfig
import com.limi.models.Autor
import com.limi.models.Autores
import com.limi.models.Generos
import com.limi.models.Livro
import com.limi.models.LivroGenero
import com.limi.models.Livros
import org.junit.jupiter.api.AfterAll
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.Assertions.assertTrue

class LivroRepositoryTest {

    private lateinit var livroRepository: LivroRepository
    private lateinit var autorRepository: AutorRepository

    companion object {
        @BeforeAll
        @JvmStatic
        fun setupAll() {
            DatabaseConfig.init()
        }

        @AfterAll
        @JvmStatic
        fun teardownAll() {
            DatabaseConfig.close()
        }
    }

    @BeforeEach
    fun setup() {
        DatabaseConfig.clearTables()
        livroRepository = LivroRepository()
        autorRepository = AutorRepository()
    }

    @Test
    fun `addLivro should add a new livro`() {
        val autor = autorRepository.addAutor(Autor(nome = "Test Autor"))
        val livro = Livro(titulo = "Test Livro", autor = autor.nome, anoPublicacao = 2023, sinopse = "Test Sinopse", generos = listOf("Fiction"))
        val addedLivro = livroRepository.addLivro(livro)
        assertNotNull(addedLivro.id)
        assertEquals(livro.titulo, addedLivro.titulo)
        assertEquals(livro.autor, addedLivro.autor)
        assertTrue(addedLivro.generos.contains("Fiction"))
    }

    @Test
    fun `getAllLivros should return all livros`() {
        val autor = autorRepository.addAutor(Autor(nome = "Autor 1"))
        livroRepository.addLivro(Livro(titulo = "Livro 1", autor = autor.nome, anoPublicacao = 2020, sinopse = "Sinopse 1", generos = listOf("Fiction")))
        livroRepository.addLivro(Livro(titulo = "Livro 2", autor = autor.nome, anoPublicacao = 2021, sinopse = "Sinopse 2", generos = listOf("Science")))
        val livros = livroRepository.getAllLivros()
        assertEquals(2, livros.size)
    }

    @Test
    fun `getLivroById should return livro by id`() {
        val autor = autorRepository.addAutor(Autor(nome = "Test Autor"))
        val livro = Livro(titulo = "Test Livro", autor = autor.nome, anoPublicacao = 2023, sinopse = "Test Sinopse", generos = listOf("Fiction"))
        val addedLivro = livroRepository.addLivro(livro)
        val foundLivro = livroRepository.getLivroById(addedLivro.id!!)
        assertNotNull(foundLivro)
        assertEquals(addedLivro.id, foundLivro?.id)
    }

    @Test
    fun `findByTitle should return livro by title`() {
        val autor = autorRepository.addAutor(Autor(nome = "Test Autor"))
        val livro = Livro(titulo = "Test Livro", autor = autor.nome, anoPublicacao = 2023, sinopse = "Test Sinopse", generos = listOf("Fiction"))
        livroRepository.addLivro(livro)
        val foundLivro = livroRepository.findByTitle("Test Livro")
        assertNotNull(foundLivro)
        assertEquals(livro.titulo, foundLivro?.titulo)
    }

    @Test
    fun `getLivrosByGenero should return livros by genre`() {
        val autor = autorRepository.addAutor(Autor(nome = "Test Autor"))
        livroRepository.addLivro(Livro(titulo = "Livro Genero 1", autor = autor.nome, anoPublicacao = 2020, sinopse = "Sinopse 1", generos = listOf("Fiction", "Adventure")))
        livroRepository.addLivro(Livro(titulo = "Livro Genero 2", autor = autor.nome, anoPublicacao = 2021, sinopse = "Sinopse 2", generos = listOf("Science")))
        val livros = livroRepository.getLivrosByGenero("Fiction")
        assertEquals(1, livros.size)
        assertEquals("Livro Genero 1", livros[0].titulo)
    }

    @Test
    fun `getAllGeneros should return all genres`() {
        val autor = autorRepository.addAutor(Autor(nome = "Test Autor"))
        livroRepository.addLivro(Livro(titulo = "Livro Genero 1", autor = autor.nome, anoPublicacao = 2020, sinopse = "Sinopse 1", generos = listOf("Fiction", "Adventure")))
        livroRepository.addLivro(Livro(titulo = "Livro Genero 2", autor = autor.nome, anoPublicacao = 2021, sinopse = "Sinopse 2", generos = listOf("Science")))
        val generos = livroRepository.getAllGeneros()
        assertEquals(3, generos.size)
        assertTrue(generos.containsAll(listOf("Fiction", "Adventure", "Science")))
    }

    @Test
    fun `updateLivro should update livro details`() {
        val autor = autorRepository.addAutor(Autor(nome = "Test Autor"))
        val livro = Livro(titulo = "Test Livro", autor = autor.nome, anoPublicacao = 2023, sinopse = "Test Sinopse", generos = listOf("Fiction"))
        val addedLivro = livroRepository.addLivro(livro)

        val updatedLivro = addedLivro.copy(titulo = "Updated Livro", anoPublicacao = 2024, generos = listOf("Science"))
        val result = livroRepository.updateLivro(addedLivro.id!!, updatedLivro)
        assertNotNull(result)
        assertEquals("Updated Livro", result?.titulo)
        assertEquals(2024, result?.anoPublicacao)
        assertTrue(result?.generos?.contains("Science") == true)
    }

    @Test
    fun `deleteLivro should delete livro`() {
        val autor = autorRepository.addAutor(Autor(nome = "Test Autor"))
        val livro = Livro(titulo = "Test Livro", autor = autor.nome, anoPublicacao = 2023, sinopse = "Test Sinopse", generos = listOf("Fiction"))
        val addedLivro = livroRepository.addLivro(livro)
        val result = livroRepository.deleteLivro(addedLivro.id!!)
        assertTrue(result)
        assertNull(livroRepository.getLivroById(addedLivro.id!!))
    }
}