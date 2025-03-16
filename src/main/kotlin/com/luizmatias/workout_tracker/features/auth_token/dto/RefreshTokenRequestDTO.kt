package com.luizmatias.workout_tracker.features.auth_token.dto

import jakarta.validation.constraints.NotBlank

data class RefreshTokenRequestDTO(
    @field:NotBlank
    val refreshToken: String,
)
