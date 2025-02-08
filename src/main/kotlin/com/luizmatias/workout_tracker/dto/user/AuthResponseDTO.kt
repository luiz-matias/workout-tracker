package com.luizmatias.workout_tracker.dto.user

data class AuthResponseDTO(
    val user: UserResponseDTO,
    val token: String
)