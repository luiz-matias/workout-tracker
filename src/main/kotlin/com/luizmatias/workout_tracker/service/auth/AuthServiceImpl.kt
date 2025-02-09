package com.luizmatias.workout_tracker.service.auth

import com.luizmatias.workout_tracker.api.dto.mapper.toUserDTO
import com.luizmatias.workout_tracker.api.dto.mapper.toUserRegistration
import com.luizmatias.workout_tracker.api.dto.user.AuthCredentialsDTO
import com.luizmatias.workout_tracker.api.dto.user.AuthRegisterDTO
import com.luizmatias.workout_tracker.api.dto.user.AuthResponseDTO
import com.luizmatias.workout_tracker.service.auth.token.RefreshTokenService
import com.luizmatias.workout_tracker.service.user.UserService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.stereotype.Service

@Service
class AuthServiceImpl @Autowired constructor(
    private val authenticationManager: AuthenticationManager,
    private val userService: UserService,
    private val refreshTokenService: RefreshTokenService
) : AuthService {

    override fun register(registration: AuthRegisterDTO): AuthResponseDTO {
        val addedUser = userService.registerUser(registration.toUserRegistration())
        return AuthResponseDTO(addedUser.toUserDTO(), refreshTokenService.generateTokensFromUserAuth(addedUser))
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
                AuthResponseDTO(it.toUserDTO(), refreshTokenService.generateTokensFromUserAuth(user))
            }
        }
        return null
    }

}