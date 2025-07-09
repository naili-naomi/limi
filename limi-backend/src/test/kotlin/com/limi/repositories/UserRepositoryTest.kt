
package com.limi.repositories

import com.limi.DatabaseConfig
import com.limi.models.User
import com.limi.models.Users
import org.junit.jupiter.api.AfterAll
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.Assertions.assertTrue
import org.mindrot.jbcrypt.BCrypt

class UserRepositoryTest {

    private lateinit var userRepository: UserRepository

    companion object {
        @BeforeAll
        @JvmStatic
        fun setupAll() {
            DatabaseConfig.init()
        }

        @AfterAll
        @JvmStatic
        fun teardownAll() {
            DatabaseConfig.close()
        }
    }

    @BeforeEach
    fun setup() {
        DatabaseConfig.clearTables()
        userRepository = UserRepository()
    }

    @Test
    fun `addUser should add a new user`() {
        val user = User(nome = "Test User", username = "testuser", email = "test@example.com", senha = "password")
        val addedUser = userRepository.addUser(user)
        assertNotNull(addedUser.id)
        assertEquals(user.email, addedUser.email)
    }

    @Test
    fun `buscarPorEmail should return user by email`() {
        val user = User(nome = "Test User", username = "testuser", email = "test@example.com", senha = "password")
        userRepository.addUser(user)
        val foundUser = userRepository.buscarPorEmail("test@example.com")
        assertNotNull(foundUser)
        assertEquals(user.email, foundUser?.email)
    }

    @Test
    fun `findByUsername should return user by username`() {
        val user = User(nome = "Test User", username = "testuser", email = "test@example.com", senha = "password")
        userRepository.addUser(user)
        val foundUser = userRepository.findByUsername("testuser")
        assertNotNull(foundUser)
        assertEquals(user.username, foundUser?.username)
    }

    @Test
    fun `getAllUsers should return all users`() {
        userRepository.addUser(User(nome = "User1", username = "user1", email = "user1@example.com", senha = "pass1"))
        userRepository.addUser(User(nome = "User2", username = "user2", email = "user2@example.com", senha = "pass2"))
        val users = userRepository.getAllUsers()
        assertEquals(2, users.size)
    }

    @Test
    fun `getUserById should return user by id`() {
        val user = User(nome = "Test User", username = "testuser", email = "test@example.com", senha = "password")
        val addedUser = userRepository.addUser(user)
        val foundUser = userRepository.getUserById(addedUser.id!!)
        assertNotNull(foundUser)
        assertEquals(addedUser.id, foundUser?.id)
    }

    @Test
    fun `updateUser should update user details`() {
        val user = User(nome = "Test User", username = "testuser", email = "test@example.com", senha = "password")
        val addedUser = userRepository.addUser(user)
        val updatedUser = addedUser.copy(nome = "Updated User", email = "updated@example.com")
        val result = userRepository.updateUser(addedUser.id!!, updatedUser)
        assertNotNull(result)
        assertEquals("Updated User", result?.nome)
        assertEquals("updated@example.com", result?.email)
    }

    @Test
    fun `deleteUser should delete user`() {
        val user = User(nome = "Test User", username = "testuser", email = "test@example.com", senha = "password")
        val addedUser = userRepository.addUser(user)
        val result = userRepository.deleteUser(addedUser.id!!)
        assertTrue(result)
        assertNull(userRepository.getUserById(addedUser.id!!))
    }

    @Test
    fun `findByEmail should return user by email for auth service`() {
        val user = User(nome = "Test User", username = "testuser", email = "auth@example.com", senha = "password")
        userRepository.addUser(user)
        val foundUser = userRepository.findByEmail("auth@example.com")
        assertNotNull(foundUser)
        assertEquals(user.email, foundUser?.email)
    }

    @Test
    fun `findByResetToken should return user by reset token`() {
        val user = User(nome = "Test User", username = "testuser", email = "reset@example.com", senha = "password", resetToken = "token123", resetTokenExpiry = System.currentTimeMillis() + 100000)
        val addedUser = userRepository.addUser(user)
        val foundUser = userRepository.findByResetToken("token123")
        assertNotNull(foundUser)
        assertEquals(addedUser.id, foundUser?.id)
    }

    @Test
    fun `updateResetToken should update reset token and expiry`() {
        val user = User(nome = "Test User", username = "testuser", email = "update_token@example.com", senha = "password")
        val addedUser = userRepository.addUser(user)
        val newToken = "newToken456"
        val newExpiry = System.currentTimeMillis() + 200000
        userRepository.updateResetToken(addedUser.id!!, newToken, newExpiry)
        val updatedUser = userRepository.getUserById(addedUser.id!!)
        assertEquals(newToken, updatedUser?.resetToken)
        assertEquals(newExpiry, updatedUser?.resetTokenExpiry)
    }

    @Test
    fun `updatePassword should update user password`() {
        val user = User(nome = "Test User", username = "testuser", email = "update_pass@example.com", senha = "old_password")
        val addedUser = userRepository.addUser(user)
        val newPassword = "new_password"
        val result = userRepository.updatePassword(addedUser.id!!, newPassword)
        assertTrue(result)
        val updatedUser = userRepository.getUserById(addedUser.id!!)
        assertTrue(BCrypt.checkpw(newPassword, updatedUser?.senha))
    }
}
