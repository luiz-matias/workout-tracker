package com.luizmatias.workout_tracker.features.auth_token.dto

data class TokenDTO(
    val accessToken: String,
    val refreshToken: String,
)
