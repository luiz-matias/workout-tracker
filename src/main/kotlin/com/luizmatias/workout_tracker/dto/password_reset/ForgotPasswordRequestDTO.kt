package com.luizmatias.workout_tracker.dto.password_reset

import jakarta.validation.constraints.Email

data class ForgotPasswordRequestDTO(
    @field:Email
    val email: String,
)