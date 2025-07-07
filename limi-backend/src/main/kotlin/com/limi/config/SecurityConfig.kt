package com.limi.config

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import io.ktor.server.auth.jwt.*

object JwtConfig {
    private const val secret = "super-secret" // use variável de ambiente no deploy
    private const val issuer = "limi"
    private const val audience = "limi-users"
    private const val validityInMs = 36_000_00 * 24 // 24h

    private val algorithm = Algorithm.HMAC256(secret)

    fun generateToken(userId: Int, email: String): String {
        println("Generating token for userId: $userId, email: $email")
        val token = JWT.create()
            .withIssuer(issuer)
            .withAudience(audience)
            .withClaim("userId", userId)
            .withClaim("email", email) // Adiciona o email como claim
            .sign(algorithm)
        println("Generated token: $token")
        return token
    }

    fun configureJwt(jwt: JWTAuthenticationProvider.Config) {
        println("Configuring JWT: issuer=$issuer, audience=$audience")
        jwt.verifier(
            JWT.require(algorithm)
                .withIssuer(issuer)
                .withAudience(audience)
                .build()
        )
        jwt.validate { credential ->
            val userId = credential.payload.getClaim("userId").asInt()
            println("Validating token for userId: $userId")
            if (userId != null) JWTPrincipal(credential.payload) else null
        }
    }
}
