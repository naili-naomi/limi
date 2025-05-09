package com.limi.validation

import com.limi.exceptions.ValidationException
import com.limi.models.Review

fun Review.validateForCreation() {
    if (livroId <= 0) {
        throw ValidationException("livroId", "ID de livro inválido")
    }
    if (userId <= 0) {
        throw ValidationException("userId", "ID de usuário inválido")
    }
    if (comentario.isBlank()) {
        throw ValidationException("comentario", "Comentário não pode ser vazio")
    }
    if (nota !in 1..5) {
        throw ValidationException("nota", "A nota deve estar entre 1 e 5")
    }
}
