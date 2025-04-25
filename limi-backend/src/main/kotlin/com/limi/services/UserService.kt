package services

import com.limi.models.User

class UserService {
    private val usuarios = mutableListOf<User>()

    fun adicionarUser(usuario: User) {
        usuarios.add(usuario)
    }

    fun buscarPorEmail(email: String): User? {
        return usuarios.find { it.email.equals(email, ignoreCase = true) }
    }

    fun listarUsers(): List<User> = usuarios
}
