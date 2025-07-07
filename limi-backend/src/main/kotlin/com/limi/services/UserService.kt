package com.limi.services

import com.limi.models.User
import com.limi.repositories.UserRepository
import com.limi.exceptions.ValidationException
import com.limi.exceptions.AuthenticationException
import com.limi.exceptions.NotFoundException
import com.limi.config.JwtConfig
import org.mindrot.jbcrypt.BCrypt
import com.limi.models.UserLoginRequest

class UserService(private val userRepository: UserRepository) {
    fun adicionarUser(usuario: User): User {

        userRepository.buscarPorEmail(usuario.email)?.let {
            throw ValidationException("email", "Já existe um usuário com este email")
        }
        val senhaHash = BCrypt.hashpw(usuario.senha, BCrypt.gensalt())
        val usuarioComHash = usuario.copy(senha = senhaHash)

        return userRepository.addUser(usuarioComHash)
    }
    fun login(loginRequest: UserLoginRequest): String {
        val usuario = userRepository.buscarPorEmail(loginRequest.email)
            ?: throw AuthenticationException("Email ou senha inválidos")

        if (!BCrypt.checkpw(loginRequest.senha, usuario.senha)) {
            throw AuthenticationException("Email ou senha inválidos")
        }

        return JwtConfig.generateToken(usuario.id, usuario.email)
    }

    fun buscarPorEmail(email: String): User? = userRepository.buscarPorEmail(email)

    fun listarUsers(): List<User> = userRepository.getAllUsers()

    fun atualizarUser(id: Int, user: User): User {
        val existente = userRepository.buscarPorEmail(user.email)
        if (existente != null && existente.id != id) {
            throw ValidationException("email", "Já existe um usuário com este email")
        }

        // Gera o hash da senha antes de atualizar
        val hashedUser = user.copy(senha = BCrypt.hashpw(user.senha, BCrypt.gensalt()))

        return userRepository.updateUser(id, hashedUser)
            ?: throw NotFoundException("Usuário com ID $id não encontrado")
    }

    fun deletarUser(id: Int): Boolean {
        return userRepository.deleteUser(id)
    }

    fun findByUsername(username: String): User? {
        return userRepository.findByUsername(username)
    }

    fun getUserById(id: Int): User? {
        return userRepository.getUserById(id)
    }
}