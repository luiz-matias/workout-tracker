package com.luizmatias.workout_tracker.service.user

import com.luizmatias.workout_tracker.model.user.User

/**
 * Service for managing users. This service is accountable for managing CRUD operations related to users.
 */
interface UserService {
    /**
     * Get a user by its email.
     */
    fun getUserByEmail(email: String): User

    /**
     * Register a new user.
     */
    fun registerUser(user: User): User

    /**
     * Updates a user.
     */
    fun updateUser(
        id: Long,
        user: User,
    ): User

    /**
     * Change the password of a user.
     */
    fun changePassword(
        id: Long,
        existingPassword: String,
        newPassword: String,
    ): User

    /**
     * Reset the password of a user.
     */
    fun resetPassword(
        token: String,
        newPassword: String,
    ): User

    /**
     * Verify the email of a user.
     */
    fun verifyEmail(token: String): User

    /**
     * Delete a user.
     */
    fun deleteUser(id: Long)
}
