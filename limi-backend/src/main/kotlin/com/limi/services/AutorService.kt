package com.limi.services

import com.limi.models.Autor
import com.limi.repositories.AutorRepository

class AutorService(private val autorRepository: AutorRepository) {
    fun adicionarAutor(autor: Autor) = autorRepository.addAutor(autor)

    fun listarAutores(): List<Autor> = autorRepository.getAllAutores()

    fun buscarPorNome(nome: String): Autor? {
        return autorRepository.buscarPorNome(nome)
    }
}
