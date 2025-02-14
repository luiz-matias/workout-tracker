package com.luizmatias.workout_tracker.dto.token

import jakarta.validation.constraints.NotBlank

data class RefreshTokenRequestDTO(
    @field:NotBlank
    val refreshToken: String,
)
