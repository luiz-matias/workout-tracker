package com.luizmatias.workout_tracker.features.auth.dto

import com.luizmatias.workout_tracker.features.auth_token.dto.TokenDTO
import com.luizmatias.workout_tracker.features.user.dto.UserResponseDTO

data class AuthResponseDTO(
    val user: UserResponseDTO,
    val token: TokenDTO,
)
