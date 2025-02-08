package com.luizmatias.workout_tracker.service.user

import com.luizmatias.workout_tracker.model.user.User

interface UserService {

    fun getUserByEmail(email: String): User?

    fun registerUser(user: User): User

    fun updateUser(id: Long, user: User): User?

    fun changePassword(id: Long, existingPassword: String, newPassword: String): User

    fun deleteUser(id: Long): Boolean

}