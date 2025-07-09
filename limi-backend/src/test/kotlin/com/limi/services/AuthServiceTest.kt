
package com.limi.services

import com.limi.models.User
import com.limi.repositories.UserRepository
import io.mockk.every
import io.mockk.mockk
import io.mockk.spyk
import io.mockk.verify
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class AuthServiceTest {

    private val userRepository: UserRepository = mockk(relaxed = true)
    private val authService = spyk(AuthService(userRepository))

    @Test
    fun `forgotPassword should send a password reset email`() {
        // Given
        val user = User(id = 1, nome = "Test", username = "testuser", email = "test@test.com", senha = "password", resetToken = null, resetTokenExpiry = null)
        every { userRepository.findByEmail("test@test.com") } returns user
        every { authService["sendPasswordResetEmail"](any<String>(), any<String>()) } returns Unit

        // When
        val result = authService.forgotPassword("test@test.com")

        // Then
        assertEquals("E-mail de redefinição de senha enviado", result)
        verify(exactly = 1) { userRepository.updateResetToken(any(), any(), any()) }
    }

    @Test
    fun `forgotPassword should throw an exception when user is not found`() {
        // Given
        every { userRepository.findByEmail("test@test.com") } returns null

        // When & Then
        assertThrows<Exception> {
            authService.forgotPassword("test@test.com")
        }
    }

    @Test
    fun `resetPassword should reset the password`() {
        // Given
        val user = User(id = 1, nome = "Test", username = "testuser", email = "test@test.com", senha = "password", resetToken = "test_token", resetTokenExpiry = System.currentTimeMillis() + 3600000)
        every { userRepository.findByResetToken("test_token") } returns user

        // When
        val result = authService.resetPassword("test_token", "newPassword")

        // Then
        assertEquals("Password reset successfully", result)
        verify(exactly = 1) { userRepository.updatePassword(1, "newPassword") }
        verify(exactly = 1) { userRepository.updateResetToken(1, null, null) }
    }

    @Test
    fun `resetPassword should throw an exception when token is invalid`() {
        // Given
        every { userRepository.findByResetToken("invalid_token") } returns null

        // When & Then
        assertThrows<Exception> {
            authService.resetPassword("invalid_token", "newPassword")
        }
    }

    @Test
    fun `resetPassword should throw an exception when token is expired`() {
        // Given
        val user = User(id = 1, nome = "Test", username = "testuser", email = "test@test.com", senha = "password", resetToken = "test_token", resetTokenExpiry = System.currentTimeMillis() - 1000)
        every { userRepository.findByResetToken("test_token") } returns user

        // When & Then
        assertThrows<Exception> {
            authService.resetPassword("test_token", "newPassword")
        }
    }
}
