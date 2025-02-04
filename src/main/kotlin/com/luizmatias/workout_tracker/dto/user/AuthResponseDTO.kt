package com.luizmatias.workout_tracker.dto.user

data class AuthResponseDTO(
    val user: UserDTO,
    val token: String
)