package com.luizmatias.workout_tracker.service.auth.token

import com.auth0.jwt.exceptions.JWTCreationException

interface JWTService {

    @Throws(JWTCreationException::class)
    fun generateAccessToken(subject: String): String

    fun validateAndGetSubjectFromToken(token: String): String?

}