package com.luizmatias.workout_tracker.dto.user

import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotBlank

data class AuthCredentialsDTO(
    @field:Email
    val email: String,
    @field:NotBlank
    val password: String,
)
