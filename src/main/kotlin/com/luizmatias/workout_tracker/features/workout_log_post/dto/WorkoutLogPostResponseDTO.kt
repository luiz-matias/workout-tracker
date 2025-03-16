package com.luizmatias.workout_tracker.features.workout_log_post.dto

import com.luizmatias.workout_tracker.features.user.dto.UserResponseDTO
import java.time.Instant

data class WorkoutLogPostResponseDTO(
    val id: Long,
    val user: UserResponseDTO,
    val title: String,
    val description: String?,
    val photoUrl: String?,
    val createdAt: Instant,
    val deletedAt: Instant?,
)
