package com.limi.validation

import com.limi.exceptions.ValidationException
import com.limi.models.User
import org.junit.jupiter.api.Test
import kotlin.test.assertFailsWith
import kotlin.test.assertTrue

class UserValidationTest {

    @Test
    fun `validateForCreation deve falhar quando o nome e branco`() {
        val user = User(id = 0, nome = "", username = "user01", email = "user@example.com", senha = "password")
        val exception = assertFailsWith<ValidationException> {
            user.validateForCreation()
        }
        assertTrue(exception.errors.containsKey("nome"))
    }

    @Test
    fun `validateForCreation deve falhar quando o username e branco`() {
        val user = User(id = 0, nome = "User", username = "", email = "user@example.com", senha = "password")
        val exception = assertFailsWith<ValidationException> {
            user.validateForCreation()
        }
        assertTrue(exception.errors.containsKey("username"))
    }

    @Test
    fun `validateForCreation deve falhar quando o email e branco`() {
        val user = User(id = 0, nome = "User", username = "user01", email = "", senha = "password")
        val exception = assertFailsWith<ValidationException> {
            user.validateForCreation()
        }
        assertTrue(exception.errors.containsKey("email"))
    }

    @Test
    fun `validateForCreation deve falhar quando o email e invalido`() {
        val user = User(id = 0, nome = "User", username = "user01", email = "invalid-email", senha = "password")
        val exception = assertFailsWith<ValidationException> {
            user.validateForCreation()
        }
        assertTrue(exception.errors.containsKey("email"))
    }

    @Test
    fun `validateForCreation deve falhar quando a senha e curta`() {
        val user = User(id = 0, nome = "User", username = "user01", email = "user@example.com", senha = "123")
        val exception = assertFailsWith<ValidationException> {
            user.validateForCreation()
        }
        assertTrue(exception.errors.containsKey("senha"))
    }

    @Test
    fun `validateForCreation deve ter sucesso com dados validos`() {
        val user = User(id = 0, nome = "User", username = "user01", email = "user@example.com", senha = "strongPassword")
        user.validateForCreation() // no exception expected
    }


}
