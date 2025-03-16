package com.luizmatias.workout_tracker.features.user.dto

data class UserResponseDTO(
    val id: Long,
    val firstName: String,
    val lastName: String,
    val email: String,
    val profilePictureUrl: String?,
    val instagramUsername: String?,
    val twitterUsername: String?,
)
