package com.limi.services

import com.limi.repositories.UserRepository
import java.util.*
import javax.mail.*
import javax.mail.internet.InternetAddress
import javax.mail.internet.MimeMessage

class AuthService(private val userRepository: UserRepository) {

    fun forgotPassword(email: String): String {
        val user = userRepository.findByEmail(email) ?: throw Exception("User not found")

        val token = generateResetToken()
        val expiryDate = System.currentTimeMillis() + 3600000 // 1 hour

        userRepository.updateResetToken(user.id, token, expiryDate)

        sendPasswordResetEmail(user.email, token)

        return "Password reset email sent"
    }

    fun resetPassword(token: String, newPassword: String):String {
        val user = userRepository.findByResetToken(token) ?: throw Exception("Invalid token")

        if (user.resetTokenExpiry != null) {
            if (System.currentTimeMillis() > user.resetTokenExpiry) {
                throw Exception("Token expired")
            }
        }

        userRepository.updatePassword(user.id, newPassword)
        userRepository.updateResetToken(user.id, null, null)

        return "Password reset successfully"
    }

    private fun generateResetToken(): String {
        return UUID.randomUUID().toString()
    }

    private fun sendPasswordResetEmail(email: String, token: String) {
        val props = Properties().apply {
            put("mail.smtp.auth", "true")
            put("mail.smtp.starttls.enable", "true") // STARTTLS HABILITADO
            put("mail.smtp.starttls.required", "true") // Obrigatório
            put("mail.smtp.host", "smtp.gmail.com")
            put("mail.smtp.port", "587") // Porta para STARTTLS
            put("mail.smtp.ssl.protocols", "TLSv1.2") // Especifica protocolo moderno
        }


        val session = Session.getDefaultInstance(props, object : Authenticator() {
            override fun getPasswordAuthentication(): PasswordAuthentication {
                val username = System.getenv("GMAIL_USERNAME")
                val password = System.getenv("GMAIL_PASSWORD")
                return PasswordAuthentication(username, password)
            }
        })

        try {
            val message = MimeMessage(session)
            message.addRecipient(Message.RecipientType.TO, InternetAddress(email))
            message.subject = "Password Reset"
            message.setText("To reset your password, click the following link: http://localhost:5173/reset-password?token=$token")
            Transport.send(message)
        } catch (e: MessagingException) {
            throw RuntimeException(e)
        }
    }
}
