package com.limi.services

import com.limi.models.Autor

class AutorService {
    private val autores = mutableListOf<Autor>()

    fun adicionarAutor(autor: Autor) {
        autores.add(autor)
    }

    fun listarAutores(): List<Autor> = autores
}
