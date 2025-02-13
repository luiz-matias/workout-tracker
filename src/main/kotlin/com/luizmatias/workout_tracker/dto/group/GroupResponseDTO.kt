package com.luizmatias.workout_tracker.dto.group

import com.luizmatias.workout_tracker.dto.user.UserResponseDTO
import com.luizmatias.workout_tracker.model.group.GroupMeasurementStrategy

data class GroupResponseDTO(
    val id: Long,
    val name: String,
    val description: String?,
    val bannerUrl: String?,
    val measurementStrategy: GroupMeasurementStrategy,
    val members: List<UserResponseDTO>?
)