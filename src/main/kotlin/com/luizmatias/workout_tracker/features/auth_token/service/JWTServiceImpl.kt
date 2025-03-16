package com.luizmatias.workout_tracker.features.auth_token.service

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import com.auth0.jwt.exceptions.JWTCreationException
import com.auth0.jwt.exceptions.JWTVerificationException
import com.luizmatias.workout_tracker.config.exception.common_exceptions.InternalServerErrorException
import java.time.Instant
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service

@Service
class JWTServiceImpl : JWTService {
    @Value("\${jwt.secret}")
    private val secretKey: String? = null

    @Value("\${jwt.access-token-expiry-time-in-seconds}")
    private val expiryTime: String? = null

    @Value("\${jwt.issuer}")
    private val issuer: String? = null

    private val logger = LoggerFactory.getLogger(JWTServiceImpl::class.java)

    override fun generateAccessToken(subject: String): String {
        try {
            val algorithm: Algorithm = Algorithm.HMAC256(secretKey)
            return JWT
                .create()
                .withIssuer(issuer)
                .withSubject(subject)
                .withExpiresAt(Instant.now().plusSeconds(expiryTime?.toLongOrNull() ?: 0))
                .sign(algorithm)
        } catch (e: JWTCreationException) {
            logger.error("Failed to create JWT token: $e")
            throw InternalServerErrorException("Failed to create JWT token.")
        }
    }

    override fun validateAndGetSubjectFromToken(token: String): String? {
        val algorithm: Algorithm = Algorithm.HMAC256(secretKey)
        return try {
            JWT
                .require(algorithm)
                .withIssuer(issuer)
                .build()
                .verify(token)
                .subject
        } catch (e: JWTVerificationException) {
            logger.warn("Failed to validate JWT token: $e")
            return null
        }
    }
}
