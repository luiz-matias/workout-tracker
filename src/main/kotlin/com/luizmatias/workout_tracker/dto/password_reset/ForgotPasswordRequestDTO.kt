package com.luizmatias.workout_tracker.dto.password_reset

import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotBlank

data class ForgotPasswordRequestDTO(
    @field:NotBlank
    @field:Email
    val email: String,
)
