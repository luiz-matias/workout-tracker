package com.luizmatias.workout_tracker.service.auth

import com.luizmatias.workout_tracker.dto.user.AuthCredentialsDTO
import com.luizmatias.workout_tracker.dto.user.AuthRegisterDTO
import com.luizmatias.workout_tracker.dto.user.AuthResponseDTO

/**
 * Service for managing user authentication. This service is accountable for managing user registration, login, and
 * password recovery.
 */
interface AuthService {
    /**
     * Registers a new user into the system. This user will be granted with a common user role.
     */
    fun register(registration: AuthRegisterDTO): AuthResponseDTO

    /**
     * Verify user credentials and provide a user and an auth token if the credentials are valid.
     */
    fun login(credentials: AuthCredentialsDTO): AuthResponseDTO

    /**
     * Sends an email to the user with a token to reset the password.
     */
    fun forgotPassword(email: String)
}
