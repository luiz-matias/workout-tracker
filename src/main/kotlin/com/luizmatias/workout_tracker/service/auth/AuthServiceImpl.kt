package com.luizmatias.workout_tracker.service.auth

import com.luizmatias.workout_tracker.dto.mapper.toUserDTO
import com.luizmatias.workout_tracker.dto.mapper.toUserRegistration
import com.luizmatias.workout_tracker.dto.user.AuthCredentialsDTO
import com.luizmatias.workout_tracker.dto.user.AuthResponseDTO
import com.luizmatias.workout_tracker.service.auth.jwt.JWTService
import com.luizmatias.workout_tracker.service.user.UserService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.stereotype.Service

@Service
class AuthServiceImpl @Autowired constructor(
    private val authenticationManager: AuthenticationManager,
    private val userService: UserService,
    private val jwtService: JWTService
) : AuthService {

    override fun register(credentials: AuthCredentialsDTO): AuthResponseDTO {
        val addedUser = userService.registerUser(credentials.toUserRegistration())
        return AuthResponseDTO(addedUser.toUserDTO(), jwtService.generateToken(addedUser.email))
    }

    override fun login(credentials: AuthCredentialsDTO): AuthResponseDTO? {
        val authResponse = authenticationManager.authenticate(
            UsernamePasswordAuthenticationToken(
                credentials.email,
                credentials.password
            )
        )

        if (authResponse.isAuthenticated) {
            val user = userService.getUserByEmail(credentials.email)
            return user?.let {
                AuthResponseDTO(it.toUserDTO(), jwtService.generateToken(it.email))
            }
        }
        return null
    }

}