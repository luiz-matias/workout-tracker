package com.luizmatias.workout_tracker.dto.mapper

import com.luizmatias.workout_tracker.dto.user.AuthRegisterDTO
import com.luizmatias.workout_tracker.dto.user.UserResponseDTO
import com.luizmatias.workout_tracker.model.user.AccountRole
import com.luizmatias.workout_tracker.model.user.User
import com.luizmatias.workout_tracker.model.user.UserPrincipal
import java.time.Instant

fun User.toPrincipal(): UserPrincipal = UserPrincipal(this)

fun User.toUserDTO(): UserResponseDTO =
    UserResponseDTO(
        id = this.id ?: -1,
        firstName = this.firstName,
        lastName = this.lastName,
        email = this.email,
        profilePictureUrl = this.profilePictureUrl,
        instagramUsername = this.instagramUsername,
        twitterUsername = this.twitterUsername
    )

fun AuthRegisterDTO.toUserRegistration(): User = User(
    id = null,
    firstName = this.firstName,
    lastName = this.lastName,
    email = this.email,
    isEmailVerified = false,
    profilePictureUrl = this.profilePictureUrl,
    password = this.password,
    instagramUsername = this.instagramUsername,
    twitterUsername = this.twitterUsername,
    role = AccountRole.USER,
    groups = emptyList(),
    createdGroups = emptyList(),
    workoutLogPosts = emptyList(),
    temporaryTokens = emptyList(),
    isEnabled = true,
    createdAt = Instant.now()
)