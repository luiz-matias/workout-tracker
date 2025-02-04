package com.luizmatias.workout_tracker.service.auth

import com.luizmatias.workout_tracker.dto.user.AuthCredentialsDTO
import com.luizmatias.workout_tracker.dto.user.AuthResponseDTO

interface AuthService {
     fun register(credentials: AuthCredentialsDTO): AuthResponseDTO
     fun login(credentials: AuthCredentialsDTO): AuthResponseDTO?
}