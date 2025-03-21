package com.luizmatias.workout_tracker.features.auth.dto

import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotBlank

data class AuthCredentialsDTO(
    @field:NotBlank
    @field:Email
    val email: String,
    @field:NotBlank
    val password: String,
)
