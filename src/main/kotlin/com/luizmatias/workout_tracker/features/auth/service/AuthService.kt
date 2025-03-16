package com.luizmatias.workout_tracker.features.auth.service

import com.luizmatias.workout_tracker.features.auth.dto.AuthCredentialsDTO
import com.luizmatias.workout_tracker.features.auth.dto.AuthRegisterDTO
import com.luizmatias.workout_tracker.features.auth.dto.AuthResponseDTO

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
