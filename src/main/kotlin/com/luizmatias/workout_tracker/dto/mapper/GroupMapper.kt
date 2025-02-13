package com.luizmatias.workout_tracker.dto.mapper

import com.luizmatias.workout_tracker.dto.group.GroupCreationDTO
import com.luizmatias.workout_tracker.dto.group.GroupResponseDTO
import com.luizmatias.workout_tracker.model.group.Group
import com.luizmatias.workout_tracker.model.group.GroupMeasurementStrategy
import com.luizmatias.workout_tracker.model.user.User
import java.time.Instant

fun GroupCreationDTO.toGroup(user: User): Group = Group(
    id = null,
    name = name,
    description = description,
    bannerUrl = bannerUrl,
    measurementStrategy = GroupMeasurementStrategy.valueOf(measurementStrategy),
    createdAt = Instant.now(),
    createdBy = user,
    members = emptyList()
)

fun Group.toGroupResponseDTO(): GroupResponseDTO = GroupResponseDTO(
    id = id ?: -1,
    name = name,
    description = description,
    bannerUrl = bannerUrl,
    measurementStrategy = measurementStrategy,
    members = members.map { it.user.toUserDTO() }.distinctBy { it.id }
)