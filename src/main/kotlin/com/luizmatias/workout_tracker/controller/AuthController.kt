package com.luizmatias.workout_tracker.controller

import com.luizmatias.workout_tracker.config.api.exception.common_exceptions.UnauthorizedException
import com.luizmatias.workout_tracker.dto.common.MessageResponseDTO
import com.luizmatias.workout_tracker.dto.password_reset.ForgotPasswordRequestDTO
import com.luizmatias.workout_tracker.dto.token.RefreshTokenRequestDTO
import com.luizmatias.workout_tracker.dto.token.TokenDTO
import com.luizmatias.workout_tracker.dto.user.AuthCredentialsDTO
import com.luizmatias.workout_tracker.dto.user.AuthRegisterDTO
import com.luizmatias.workout_tracker.dto.user.AuthResponseDTO
import com.luizmatias.workout_tracker.service.auth.AuthService
import com.luizmatias.workout_tracker.service.auth.token.RefreshTokenService
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
    private val refreshTokenService: RefreshTokenService
) {

    @PostMapping("/register")
    fun registerUser(@RequestBody @Valid authRegisterDTO: AuthRegisterDTO): ResponseEntity<AuthResponseDTO> {
        val response = authService.register(authRegisterDTO)
        return ResponseEntity.ok(response)
    }

    @PostMapping("/login")
    fun login(@RequestBody @Valid authCredentialsDTO: AuthCredentialsDTO): ResponseEntity<AuthResponseDTO> {
        val response = authService.login(authCredentialsDTO)
        return if (response != null) {
            ResponseEntity.ok(response)
        } else {
            throw UnauthorizedException("Invalid credentials")
        }
    }

    @PostMapping("/refresh-token")
    fun refreshToken(@RequestBody @Valid refreshTokenDTO: RefreshTokenRequestDTO): ResponseEntity<TokenDTO> {
        val response = refreshTokenService.regenerateTokens(refreshTokenDTO.refreshToken)
        return ResponseEntity.ok(response)
    }

    @PostMapping("/forgot-password")
    fun forgotPassword(@RequestBody @Valid forgotPasswordRequestDTO: ForgotPasswordRequestDTO): ResponseEntity<MessageResponseDTO> {
        authService.forgotPassword(forgotPasswordRequestDTO.email)
        return ResponseEntity.ok(MessageResponseDTO("A password reset was sent to the provided email (in case of existing email)."))
    }

}