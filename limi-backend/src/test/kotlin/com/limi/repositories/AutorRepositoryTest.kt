
package com.limi.repositories

import com.limi.DatabaseConfig
import com.limi.models.Autor
import com.limi.models.Autores
import org.junit.jupiter.api.AfterAll
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Assertions.assertNull

class AutorRepositoryTest {

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
        autorRepository = AutorRepository()
    }

    @Test
    fun `addAutor should add a new autor`() {
        val autor = Autor(nome = "Test Autor")
        val addedAutor = autorRepository.addAutor(autor)
        assertNotNull(addedAutor.id)
        assertEquals(autor.nome, addedAutor.nome)
    }

    @Test
    fun `getAllAutores should return all autors`() {
        autorRepository.addAutor(Autor(nome = "Autor 1"))
        autorRepository.addAutor(Autor(nome = "Autor 2"))
        val autors = autorRepository.getAllAutores()
        assertEquals(2, autors.size)
    }

    @Test
    fun `getAutorById should return autor by id`() {
        val autor = Autor(nome = "Test Autor")
        val addedAutor = autorRepository.addAutor(autor)
        val foundAutor = autorRepository.getAutorById(addedAutor.id!!)
        assertNotNull(foundAutor)
        assertEquals(addedAutor.id, foundAutor?.id)
    }

    @Test
    fun `buscarPorNome should return autor by name`() {
        val autor = Autor(nome = "Test Autor")
        autorRepository.addAutor(autor)
        val foundAutor = autorRepository.buscarPorNome("Test Autor")
        assertNotNull(foundAutor)
        assertEquals(autor.nome, foundAutor?.nome)
    }

    @Test
    fun `buscarPorNome should return null if autor not found`() {
        val foundAutor = autorRepository.buscarPorNome("Non Existent Autor")
        assertNull(foundAutor)
    }
}
