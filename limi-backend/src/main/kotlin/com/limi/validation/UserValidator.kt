package com.limi.validation

import com.limi.exceptions.ValidationException
import com.limi.models.User
import org.valiktor.ConstraintViolationException
import org.valiktor.functions.*
import org.valiktor.validate

fun User.validateForCreation() {
    try {
        validate(this) {
            validate(User::nome).isNotBlank()
            validate(User::username).isNotBlank()
            validate(User::email).isNotBlank().isEmail()
        }
    } catch (ex: ConstraintViolationException) {
        val erros = ex.constraintViolations.associate {
            it.property to it.constraint.name
        }
        throw ValidationException(erros)
    }
}
