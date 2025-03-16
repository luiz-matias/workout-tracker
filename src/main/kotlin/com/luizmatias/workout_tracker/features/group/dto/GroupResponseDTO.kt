package com.luizmatias.workout_tracker.features.group.dto

import com.luizmatias.workout_tracker.features.user.dto.UserResponseDTO
import com.luizmatias.workout_tracker.features.group.model.GroupMeasurementStrategy

data class GroupResponseDTO(
    val id: Long,
    val name: String,
    val description: String?,
    val bannerUrl: String?,
    val measurementStrategy: GroupMeasurementStrategy,
    val members: List<UserResponseDTO>?,
)
