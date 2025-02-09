package com.luizmatias.workout_tracker.api.dto.user

data class UserResponseDTO(
    val id: Long,
    val firstName: String,
    val lastName: String,
    val email: String,
    val profilePictureUrl: String?,
    val instagramUsername: String?,
    val twitterUsername: String?,
)