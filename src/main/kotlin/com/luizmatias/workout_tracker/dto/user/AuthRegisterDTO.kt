package com.luizmatias.workout_tracker.dto.user

import com.luizmatias.workout_tracker.validators.StrongPassword
import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Size

data class AuthRegisterDTO(
    @field:NotBlank
    val name: String,
    @field:Email
    val email: String,
    @field:Size(min = 3, max = 100)
    val profilePictureUrl: String?,
    @field:StrongPassword
    val password: String,
    @field:Size(min = 3, max = 50)
    val instagramUsername: String?,
    @field:Size(min = 3, max = 50)
    val twitterUsername: String?,
)