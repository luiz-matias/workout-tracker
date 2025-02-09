package com.luizmatias.workout_tracker.api.dto.user

import com.luizmatias.workout_tracker.api.dto.token.TokenDTO

data class AuthResponseDTO(
    val user: UserResponseDTO,
    val token: TokenDTO
)