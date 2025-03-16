package com.luizmatias.workout_tracker.features.temporary_token.dto

import com.luizmatias.workout_tracker.config.validators.StrongPassword

data class PasswordResetRequestDTO(
    @field:StrongPassword
    val password: String,
)
