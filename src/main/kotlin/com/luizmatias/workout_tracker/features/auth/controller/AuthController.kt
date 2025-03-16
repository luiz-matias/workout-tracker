package com.luizmatias.workout_tracker.features.auth.controller

import com.luizmatias.workout_tracker.features.common.dto.MessageResponseDTO
import com.luizmatias.workout_tracker.features.auth.dto.ForgotPasswordRequestDTO
import com.luizmatias.workout_tracker.features.auth_token.dto.RefreshTokenRequestDTO
import com.luizmatias.workout_tracker.features.auth_token.dto.TokenDTO
import com.luizmatias.workout_tracker.features.auth.dto.AuthCredentialsDTO
import com.luizmatias.workout_tracker.features.auth.dto.AuthRegisterDTO
import com.luizmatias.workout_tracker.features.auth.dto.AuthResponseDTO
import com.luizmatias.workout_tracker.features.auth.service.AuthService
import com.luizmatias.workout_tracker.features.auth_token.service.RefreshTokenService
import jakarta.validation.Valid
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/auth")
class AuthController @Autowired constructor(
    private val authService: AuthService,
    private val refreshTokenService: RefreshTokenService,
) {
    @PostMapping("/register")
    fun registerUser(
        @RequestBody @Valid authRegisterDTO: AuthRegisterDTO,
    ): ResponseEntity<AuthResponseDTO> {
        val response = authService.register(authRegisterDTO)
        return ResponseEntity.ok(response)
    }

    @PostMapping("/login")
    fun login(
        @RequestBody @Valid authCredentialsDTO: AuthCredentialsDTO,
    ): ResponseEntity<AuthResponseDTO> = ResponseEntity.ok(authService.login(authCredentialsDTO))

    @PostMapping("/refresh-token")
    fun refreshToken(
        @RequestBody @Valid refreshTokenDTO: RefreshTokenRequestDTO,
    ): ResponseEntity<TokenDTO> {
        val response = refreshTokenService.regenerateTokens(refreshTokenDTO.refreshToken)
        return ResponseEntity.ok(response)
    }

    @PostMapping("/forgot-password")
    fun forgotPassword(
        @RequestBody @Valid forgotPasswordRequestDTO: ForgotPasswordRequestDTO,
    ): ResponseEntity<MessageResponseDTO> {
        authService.forgotPassword(forgotPasswordRequestDTO.email)
        return ResponseEntity.ok(
            MessageResponseDTO("A password reset was sent to the provided email (in case of existing email)."),
        )
    }
}
