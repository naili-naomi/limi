package com.limi.services

import com.limi.models.User
import com.limi.repositories.UserRepository
import com.limi.exceptions.ValidationException
import com.limi.exceptions.NotFoundException

class UserService(private val userRepository: UserRepository) {
    fun adicionarUser(usuario: User): User {

        userRepository.buscarPorEmail(usuario.email)?.let {
            throw ValidationException("email", "Já existe um usuário com este email")
        }
        return userRepository.addUser(usuario)
    }

    fun buscarPorEmail(email: String): User? = userRepository.buscarPorEmail(email)

    fun listarUsers(): List<User> = userRepository.getAllUsers()

    fun atualizarUser(id: Int, user: User): User {
        val existente = userRepository.buscarPorEmail(user.email)
        if (existente != null && existente.id != id) {
            throw ValidationException("email", "Já existe um usuário com este email")
        }

        return userRepository.updateUser(id, user)
            ?: throw NotFoundException("Usuário com ID $id não encontrado")
    }
    fun deletarUser(id: Int): Boolean {
        return userRepository.deleteUser(id)
    }

}