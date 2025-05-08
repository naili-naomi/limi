package com.limi.services

import com.limi.models.User
import com.limi.repositories.UserRepository

class UserService(private val userRepository: UserRepository) {
    fun adicionarUser(usuario: User) = userRepository.addUser(usuario)

    fun buscarPorEmail(email: String): User? = userRepository.buscarPorEmail(email)

    fun listarUsers(): List<User> = userRepository.getAllUsers()
}