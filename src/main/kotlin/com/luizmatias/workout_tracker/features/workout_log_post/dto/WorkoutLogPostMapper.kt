package com.luizmatias.workout_tracker.features.workout_log_post.dto

import com.luizmatias.workout_tracker.features.user.dto.toUserDTO
import com.luizmatias.workout_tracker.features.user.model.User
import com.luizmatias.workout_tracker.features.workout_log_post.model.WorkoutLogPost
import java.time.Instant

fun WorkoutLogPostCreationDTO.toWorkoutLogPostCreation(user: User): WorkoutLogPost =
    WorkoutLogPost(
        id = null,
        user = user,
        title = title,
        description = description,
        photoUrl = photoUrl,
        workoutLogGroupPosts = emptyList(),
        createdAt = Instant.now(),
        deletedAt = null,
    )

fun WorkoutLogPostCreationDTO.toWorkoutLogPostEdit(user: User): WorkoutLogPost =
    WorkoutLogPost(
        id = null,
        user = user,
        title = title,
        description = description,
        photoUrl = photoUrl,
        workoutLogGroupPosts = emptyList(),
        createdAt = Instant.now(),
        deletedAt = null,
    )

fun WorkoutLogPost.toWorkoutLogPostDTO(): WorkoutLogPostResponseDTO =
    WorkoutLogPostResponseDTO(
        id = id ?: -1,
        user = user.toUserDTO(),
        title = title,
        description = description,
        photoUrl = photoUrl,
        createdAt = createdAt,
        deletedAt = deletedAt,
    )
