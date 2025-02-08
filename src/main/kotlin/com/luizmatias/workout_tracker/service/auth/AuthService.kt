package com.luizmatias.workout_tracker.service.auth

import com.luizmatias.workout_tracker.dto.user.AuthCredentialsDTO
import com.luizmatias.workout_tracker.dto.user.AuthRegisterDTO
import com.luizmatias.workout_tracker.dto.user.AuthResponseDTO

interface AuthService {
     fun register(registration: AuthRegisterDTO): AuthResponseDTO
     fun login(credentials: AuthCredentialsDTO): AuthResponseDTO?
}