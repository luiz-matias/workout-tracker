package com.luizmatias.workout_tracker.features.workout_log_post.dto

import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Size

data class WorkoutLogPostCreationDTO(
    @field:NotBlank
    @field:Size(min = 1, max = 255)
    val title: String,
    @field:Size(min = 1, max = 500)
    val description: String?,
    val photoUrl: String?,
)
