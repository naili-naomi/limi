
package com.limi.services

import com.limi.DTO.UserLoginRequest
import com.limi.config.JwtConfig
import com.limi.exceptions.AuthenticationException
import com.limi.exceptions.NotFoundException
import com.limi.models.User
import com.limi.repositories.UserRepository
import com.limi.exceptions.ValidationException
import io.mockk.every
import io.mockk.mockk
import io.mockk.mockkObject
import io.mockk.verify
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.mindrot.jbcrypt.BCrypt

class UserServiceTest {

    private val userRepository: UserRepository = mockk(relaxed = true)
    private val userService = UserService(userRepository)

    @BeforeEach
    fun setUp() {
        mockkObject(JwtConfig)
    }

    @Test
    fun `adicionarUser should add user when password is valid`() {
        // Given
        val user = User(id = 1, nome = "Test", username = "testuser", email = "test@test.com", senha = "password")
        val hashedPassword = BCrypt.hashpw("password", BCrypt.gensalt())
        val userWithHashedPassword = user.copy(senha = hashedPassword)

        every { userRepository.addUser(any()) } returns userWithHashedPassword

        // When
        val result = userService.adicionarUser(user)

        // Then
        assertEquals(userWithHashedPassword.id, result.id)
        verify(exactly = 1) { userRepository.addUser(any()) }
    }

    @Test
    fun `adicionarUser should throw ValidationException when password is too short`() {
        // Given
        val user = User(id = 1, nome = "Test", username = "testuser", email = "test@test.com", senha = "123")

        // When & Then
        val exception = assertThrows<ValidationException> {
            userService.adicionarUser(user)
        }
        assertEquals("A senha deve ter no mínimo 6 caracteres.", exception.errors["senha"])
    }

    @Test
    fun `login should return token when credentials are valid`() {
        // Given
        val loginRequest = UserLoginRequest("test@test.com", "password")
        val hashedPassword = BCrypt.hashpw("password", BCrypt.gensalt())
        val user = User(id = 1, nome = "Test", username = "testuser", email = "test@test.com", senha = hashedPassword)
        val token = "test_token"

        every { userRepository.buscarPorEmail(loginRequest.email) } returns user
        every { JwtConfig.generateToken(user.id, user.email) } returns token

        // When
        val result = userService.login(loginRequest)

        // Then
        assertEquals(token, result)
    }

    @Test
    fun `login should throw AuthenticationException when email is invalid`() {
        // Given
        val loginRequest = UserLoginRequest("invalid@test.com", "password")

        every { userRepository.buscarPorEmail(loginRequest.email) } returns null

        // When & Then
        val exception = assertThrows<AuthenticationException> {
            userService.login(loginRequest)
        }
        assertEquals("Email inválido.", exception.message)
    }

    @Test
    fun `login should throw AuthenticationException when password is invalid`() {
        // Given
        val loginRequest = UserLoginRequest("test@test.com", "wrong_password")
        val hashedPassword = BCrypt.hashpw("password", BCrypt.gensalt())
        val user = User(id = 1, nome = "Test", username = "testuser", email = "test@test.com", senha = hashedPassword)

        every { userRepository.buscarPorEmail(loginRequest.email) } returns user

        // When & Then
        val exception = assertThrows<AuthenticationException> {
            userService.login(loginRequest)
        }
        assertEquals("Senha inválida.", exception.message)
    }

    @Test
    fun `buscarPorEmail should return user when user exists`() {
        // Given
        val user = User(id = 1, nome = "Test", username = "testuser", email = "test@test.com", senha = "password")
        every { userRepository.buscarPorEmail("test@test.com") } returns user

        // When
        val result = userService.buscarPorEmail("test@test.com")

        // Then
        assertEquals(user, result)
    }

    @Test
    fun `listarUsers should return a list of users`() {
        // Given
        val users = listOf(User(id = 1, nome = "Test", username = "testuser", email = "test@test.com", senha = "password"))
        every { userRepository.getAllUsers() } returns users

        // When
        val result = userService.listarUsers()

        // Then
        assertEquals(users, result)
    }

    @Test
    fun `atualizarUser should update user`() {
        // Given
        val user = User(id = 1, nome = "Test", username = "testuser", email = "test@test.com", senha = "password")
        every { userRepository.updateUser(1, any()) } returns user
        every { userRepository.buscarPorEmail(any()) } returns null

        // When
        val result = userService.atualizarUser(1, user)

        // Then
        assertEquals(user, result)
    }

    @Test
    fun `deletarUser should delete user`() {
        // Given
        every { userRepository.deleteUser(1) } returns true

        // When
        val result = userService.deletarUser(1)

        // Then
        assertTrue(result)
    }

    @Test
    fun `findByUsername should return user when user exists`() {
        // Given
        val user = User(id = 1, nome = "Test", username = "testuser", email = "test@test.com", senha = "password")
        every { userRepository.findByUsername("testuser") } returns user

        // When
        val result = userService.findByUsername("testuser")

        // Then
        assertEquals(user, result)
    }

    @Test
    fun `getUserById should return user when user exists`() {
        // Given
        val user = User(id = 1, nome = "Test", username = "testuser", email = "test@test.com", senha = "password")
        every { userRepository.getUserById(1) } returns user

        // When
        val result = userService.getUserById(1)

        // Then
        assertEquals(user, result)
    }

    @Test
    fun `changePassword should change password when current password is correct`() {
        // Given
        val hashedPassword = BCrypt.hashpw("currentPassword", BCrypt.gensalt())
        val user = User(id = 1, nome = "Test", username = "testuser", email = "test@test.com", senha = hashedPassword)
        every { userRepository.buscarPorEmail("test@test.com") } returns user

        // When
        userService.changePassword("test@test.com", "currentPassword", "newPassword")

        // Then
        verify { userRepository.updateUser(1, any()) }
    }

    @Test
    fun `changePassword should throw AuthenticationException when current password is incorrect`() {
        // Given
        val hashedPassword = BCrypt.hashpw("currentPassword", BCrypt.gensalt())
        val user = User(id = 1, nome = "Test", username = "testuser", email = "test@test.com", senha = hashedPassword)
        every { userRepository.buscarPorEmail("test@test.com") } returns user

        // When & Then
        assertThrows<AuthenticationException> {
            userService.changePassword("test@test.com", "wrongPassword", "newPassword")
        }
    }
}
