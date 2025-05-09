package com.limi.validation

import com.limi.exceptions.ValidationException
import com.limi.models.Livro
import java.time.Year

fun Livro.validateForCreation() {
    if (titulo.isBlank()) {
        throw ValidationException("titulo", "Título não pode ser vazio")
    }
    if (autor.isBlank()) {
        throw ValidationException("autor", "Autor não pode ser vazio")
    }
    val currentYear = Year.now().value
    if (anoPublicacao > currentYear) {
        throw ValidationException("anoPublicacao", "O ano deve ser no máximo $currentYear")
    }
    if (sinopse.isBlank()) {
        throw ValidationException("sinopse", "Sinopse não pode ser vazia")
    }
    if (generos.isEmpty()) {
        throw ValidationException("generos", "Deve haver ao menos um gênero")
    }
}
