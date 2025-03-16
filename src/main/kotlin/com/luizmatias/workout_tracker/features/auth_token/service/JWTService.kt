package com.luizmatias.workout_tracker.features.auth_token.service

import com.auth0.jwt.exceptions.JWTCreationException

interface JWTService {
    @Throws(JWTCreationException::class)
    fun generateAccessToken(subject: String): String

    fun validateAndGetSubjectFromToken(token: String): String?
}
