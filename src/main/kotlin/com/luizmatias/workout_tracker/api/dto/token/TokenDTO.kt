package com.luizmatias.workout_tracker.api.dto.token

data class TokenDTO(
    val accessToken: String,
    val refreshToken: String
)