package com.luizmatias.workout_tracker.dto.mapper

import com.luizmatias.workout_tracker.dto.user.AuthCredentialsDTO
import com.luizmatias.workout_tracker.dto.user.UserDTO
import com.luizmatias.workout_tracker.model.AccountRole
import com.luizmatias.workout_tracker.model.User
import com.luizmatias.workout_tracker.model.UserPrincipal

fun User.toPrincipal(): UserPrincipal = UserPrincipal(this)

fun User.toUserDTO(): UserDTO = UserDTO(
    id = this.id,
    email = this.email,
)

fun AuthCredentialsDTO.toUserRegistration(): User = User(
    id = null,
    email = this.email,
    password = this.password,
    role = AccountRole.USER,
    isEmailVerified = false,
    isCredentialsExpired = false,
    isAccountExpired = false,
    isLocked = false,
    isEnabled = true,
)