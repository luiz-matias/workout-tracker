package com.luizmatias.workout_tracker.features.user.dto

import com.luizmatias.workout_tracker.features.auth.dto.AuthRegisterDTO
import com.luizmatias.workout_tracker.features.user.model.AccountRole
import com.luizmatias.workout_tracker.features.user.model.User
import com.luizmatias.workout_tracker.features.user.model.UserPrincipal
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
        twitterUsername = this.twitterUsername,
    )

fun AuthRegisterDTO.toUserRegistration(): User =
    User(
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
        createdAt = Instant.now(),
    )
