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

    fun generateToken(userId: Int): String = JWT.create()
        .withIssuer(issuer)
        .withAudience(audience)
        .withClaim("userId", userId)
        .sign(algorithm)

    fun configureJwt(jwt: JWTAuthenticationProvider.Config) {
        jwt.verifier(
            JWT.require(algorithm)
                .withIssuer(issuer)
                .withAudience(audience)
                .build()
        )
        jwt.validate { credential ->
            if (credential.payload.getClaim("userId").asInt() != null) JWTPrincipal(credential.payload) else null
        }
    }
}
