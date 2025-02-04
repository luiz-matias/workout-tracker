package com.luizmatias.workout_tracker.service.auth.jwt

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import java.util.*

@Service
class JWTServiceImpl : JWTService {

    @Value("\${jwt.secret}")
    private lateinit var secretKey: String

    @Value("\${jwt.expiry-time-in-seconds}")
    private lateinit var expiryTime: String

    @Value("\${jwt.issuer}")
    private lateinit var issuer: String

    override fun generateToken(subject: String): String {
        val algorithm: Algorithm = Algorithm.HMAC256(secretKey)
        return JWT.create()
            .withIssuer(issuer)
            .withSubject(subject)
            .withExpiresAt(Date(System.currentTimeMillis() + expiryTime.toLong() * 1000))
            .sign(algorithm)
    }

    override fun validateAndGetSubjectFromToken(token: String): String? {
        val algorithm: Algorithm = Algorithm.HMAC256(secretKey)
        return try {
            JWT.require(algorithm)
                .withIssuer(issuer)
                .build()
                .verify(token)
                .subject
        } catch (e: Exception) {
            return null
        }
    }

}