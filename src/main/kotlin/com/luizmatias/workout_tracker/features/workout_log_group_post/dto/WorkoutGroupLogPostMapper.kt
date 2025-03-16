package com.luizmatias.workout_tracker.features.workout_log_group_post.dto

import com.luizmatias.workout_tracker.features.workout_log_group_post.model.WorkoutLogGroupPost

fun WorkoutLogGroupPost.toWorkoutLogGroupPostDTO(): WorkoutLogGroupPostResponseDTO =
    WorkoutLogGroupPostResponseDTO(
        id = id ?: -1,
        groupId = groupMember.group.id ?: -1,
        createdAt = createdAt,
        deletedAt = deletedAt,
    )
