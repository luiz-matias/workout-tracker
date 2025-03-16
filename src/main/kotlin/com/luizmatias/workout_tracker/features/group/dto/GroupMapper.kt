package com.luizmatias.workout_tracker.features.group.dto

import com.luizmatias.workout_tracker.features.group.model.Group
import com.luizmatias.workout_tracker.features.group.model.GroupMeasurementStrategy
import com.luizmatias.workout_tracker.features.user.dto.toUserDTO
import com.luizmatias.workout_tracker.features.user.model.User
import java.time.Instant

fun GroupCreationDTO.toGroupCreation(user: User): Group =
    Group(
        id = null,
        name = name,
        description = description,
        bannerUrl = bannerUrl,
        measurementStrategy = GroupMeasurementStrategy.valueOf(measurementStrategy),
        createdAt = Instant.now(),
        createdBy = user,
        members = emptyList(),
    )

fun GroupCreationDTO.toGroupEdit(): Group =
    Group(
        id = null,
        name = name,
        description = description,
        bannerUrl = bannerUrl,
        measurementStrategy = GroupMeasurementStrategy.valueOf(measurementStrategy),
        createdAt = Instant.now(),
        createdBy = null,
        members = emptyList(),
    )

fun Group.toGroupResponseDTO(): GroupResponseDTO =
    GroupResponseDTO(
        id = id ?: -1,
        name = name,
        description = description,
        bannerUrl = bannerUrl,
        measurementStrategy = measurementStrategy,
        members = members.map { it.user.toUserDTO() }.distinctBy { it.id },
    )
