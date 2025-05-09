package com.limi.services

import com.limi.models.User
import com.limi.repositories.UserRepository
import com.limi.exceptions.ValidationException

class UserService(private val userRepository: UserRepository) {
    fun adicionarUser(usuario: User): User {

        userRepository.buscarPorEmail(usuario.email)?.let {
            throw ValidationException("email", "Já existe um usuário com este email")
        }
        return userRepository.addUser(usuario)
    }

    fun buscarPorEmail(email: String): User? = userRepository.buscarPorEmail(email)

    fun listarUsers(): List<User> = userRepository.getAllUsers()
}