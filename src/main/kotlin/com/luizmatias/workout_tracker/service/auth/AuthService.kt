package com.luizmatias.workout_tracker.service.auth

import com.luizmatias.workout_tracker.api.dto.user.AuthCredentialsDTO
import com.luizmatias.workout_tracker.api.dto.user.AuthRegisterDTO
import com.luizmatias.workout_tracker.api.dto.user.AuthResponseDTO

interface AuthService {
     fun register(registration: AuthRegisterDTO): AuthResponseDTO
     fun login(credentials: AuthCredentialsDTO): AuthResponseDTO?
}