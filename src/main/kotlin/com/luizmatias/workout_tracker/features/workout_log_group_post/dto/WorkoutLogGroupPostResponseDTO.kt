package com.luizmatias.workout_tracker.features.workout_log_group_post.dto

import java.time.Instant

data class WorkoutLogGroupPostResponseDTO(
    val id: Long,
    val groupId: Long,
    val createdAt: Instant,
    val deletedAt: Instant?,
)
