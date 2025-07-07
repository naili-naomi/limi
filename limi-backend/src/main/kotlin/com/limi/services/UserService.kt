package com.limi.services

import com.limi.models.User
import com.limi.repositories.UserRepository
import com.limi.exceptions.ValidationException
import com.limi.exceptions.AuthenticationException
import com.limi.exceptions.NotFoundException
import com.limi.config.JwtConfig
import org.mindrot.jbcrypt.BCrypt
import com.limi.DTO.UserLoginRequest
import org.jetbrains.exposed.exceptions.ExposedSQLException

class UserService(private val userRepository: UserRepository) {
    fun adicionarUser(usuario: User): User {
        if (usuario.senha.length < 6) {
            throw ValidationException("senha", "A senha deve ter no mínimo 6 caracteres.")
        }

        val senhaHash = BCrypt.hashpw(usuario.senha, BCrypt.gensalt())
        val usuarioComHash = usuario.copy(senha = senhaHash)

        return try {
            userRepository.addUser(usuarioComHash)
        } catch (e: ExposedSQLException) {
            if (e.message?.contains("UNIQUE constraint failed", ignoreCase = true) == true) {
                if (e.message?.contains("email", ignoreCase = true) == true) {
                    throw ValidationException("email", "Já existe um usuário com este e-mail.")
                } else if (e.message?.contains("username", ignoreCase = true) == true) {
                    throw ValidationException("username", "Já existe um usuário com este nome.")
                } else {
                    throw e
                }
            } else {
                throw e
            }
        } catch (e: Exception) {
            throw e
        }
    }
    fun login(loginRequest: UserLoginRequest): String {
        println("DEBUG: Tentativa de login para o email: ${loginRequest.email}")
        val usuario = userRepository.buscarPorEmail(loginRequest.email)
            ?: run {
                println("DEBUG: Usuário não encontrado para o email: ${loginRequest.email}")
                throw AuthenticationException("Email inválido.")
            }

        println("DEBUG: Usuário encontrado: ${usuario.email}")
        if (!BCrypt.checkpw(loginRequest.senha, usuario.senha)) {
            println("DEBUG: Senha inválida para o email: ${loginRequest.email}")
            throw AuthenticationException("Senha inválida.")
        }

        println("DEBUG: Senha verificada com sucesso para o email: ${loginRequest.email}")
        val token = JwtConfig.generateToken(usuario.id, usuario.email)
        println("DEBUG: Token JWT gerado para o email: ${loginRequest.email}")
        return token
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