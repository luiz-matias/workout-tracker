package com.luizmatias.workout_tracker.dto.user

import com.luizmatias.workout_tracker.dto.token.TokenDTO

data class AuthResponseDTO(
    val user: UserResponseDTO,
    val token: TokenDTO,
)
