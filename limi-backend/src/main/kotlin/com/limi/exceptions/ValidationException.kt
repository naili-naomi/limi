package com.limi.exceptions

class ValidationException(
    val errors: Map<String, String>
) : RuntimeException(errors.values.firstOrNull() ?: "Erro de validação") {

    constructor(field: String, message: String) : this(mapOf(field to message))
}