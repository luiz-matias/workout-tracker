package com.luizmatias.workout_tracker.service.auth.jwt

import com.auth0.jwt.exceptions.JWTCreationException

interface JWTService {

    @Throws(JWTCreationException::class)
    fun generateToken(subject: String): String

    fun validateAndGetSubjectFromToken(token: String): String?

}