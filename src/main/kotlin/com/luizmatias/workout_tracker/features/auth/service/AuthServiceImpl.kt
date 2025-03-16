package com.luizmatias.workout_tracker.features.auth.service

import com.luizmatias.workout_tracker.config.exception.common_exceptions.UnauthorizedException
import com.luizmatias.workout_tracker.features.user.dto.toUserDTO
import com.luizmatias.workout_tracker.features.user.dto.toUserRegistration
import com.luizmatias.workout_tracker.features.auth.dto.AuthCredentialsDTO
import com.luizmatias.workout_tracker.features.auth.dto.AuthRegisterDTO
import com.luizmatias.workout_tracker.features.auth.dto.AuthResponseDTO
import com.luizmatias.workout_tracker.features.temporary_token.model.TemporaryToken
import com.luizmatias.workout_tracker.features.temporary_token.model.TokenType
import com.luizmatias.workout_tracker.features.auth_token.service.RefreshTokenService
import com.luizmatias.workout_tracker.features.notification.service.NotificationSenderService
import com.luizmatias.workout_tracker.features.temporary_token.service.TemporaryTokenService
import com.luizmatias.workout_tracker.features.user.service.UserService
import java.time.Instant
import java.time.temporal.ChronoUnit
import java.util.UUID
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.AuthenticationException
import org.springframework.stereotype.Service

@Service
class AuthServiceImpl @Autowired constructor(
    private val authenticationManager: AuthenticationManager,
    private val userService: UserService,
    private val refreshTokenService: RefreshTokenService,
    private val temporaryTokenService: TemporaryTokenService,
    private val notificationSenderService: NotificationSenderService,
) : AuthService {
    private val logger = LoggerFactory.getLogger(AuthServiceImpl::class.java)

    override fun register(registration: AuthRegisterDTO): AuthResponseDTO {
        val addedUser = userService.registerUser(registration.toUserRegistration())
        return AuthResponseDTO(
            addedUser.toUserDTO(),
            refreshTokenService.generateTokensFromUserAuth(addedUser),
        )
    }

    override fun login(credentials: AuthCredentialsDTO): AuthResponseDTO {
        try {
            val authResponse =
                authenticationManager.authenticate(
                    UsernamePasswordAuthenticationToken(
                        credentials.email,
                        credentials.password,
                    ),
                )

            if (authResponse.isAuthenticated) {
                val user = userService.getUserByEmail(credentials.email)
                return AuthResponseDTO(
                    user.toUserDTO(),
                    refreshTokenService.generateTokensFromUserAuth(user),
                )
            } else {
                throw UnauthorizedException("Invalid credentials")
            }
        } catch (e: AuthenticationException) {
            logger.info("Failed to log user: $e")
            throw UnauthorizedException("Invalid credentials")
        }
    }

    override fun forgotPassword(email: String) {
        val user = userService.getUserByEmail(email)
        val token =
            temporaryTokenService.createTemporaryToken(
                TemporaryToken(
                    id = null,
                    createdBy = user,
                    token = UUID.randomUUID().toString(),
                    type = TokenType.RESET_PASSWORD,
                    extraData = null,
                    expiresAt = Instant.now().plus(RESET_PASSWORD_EXPIRATION_MINUTES, ChronoUnit.MINUTES),
                ),
            )

        notificationSenderService.send(
            user.email,
            "Password Reset",
            "Use the following token to reset your password: ${token.token}",
        )
    }

    companion object {
        private const val RESET_PASSWORD_EXPIRATION_MINUTES = 15L
    }
}
