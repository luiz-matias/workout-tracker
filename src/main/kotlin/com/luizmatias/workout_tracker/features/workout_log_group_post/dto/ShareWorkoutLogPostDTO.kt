package com.luizmatias.workout_tracker.features.workout_log_group_post.dto

import jakarta.validation.constraints.NotBlank

data class ShareWorkoutLogPostDTO(
    @field:NotBlank
    val workoutLogPostId: Long,
    @field:NotBlank
    val groupIds: List<Long>,
)
