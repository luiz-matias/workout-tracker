package com.luizmatias.workout_tracker.dto.password_reset

import com.luizmatias.workout_tracker.config.api.validators.StrongPassword

data class PasswordResetRequestDTO(
    @field:StrongPassword
    val password: String,
)