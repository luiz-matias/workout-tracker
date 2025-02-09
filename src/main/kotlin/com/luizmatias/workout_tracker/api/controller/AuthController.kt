package com.luizmatias.workout_tracker.api.controller

import com.luizmatias.workout_tracker.api.dto.token.RefreshTokenRequestDTO
import com.luizmatias.workout_tracker.api.dto.token.TokenDTO
import com.luizmatias.workout_tracker.api.dto.user.AuthCredentialsDTO
import com.luizmatias.workout_tracker.api.dto.user.AuthRegisterDTO
import com.luizmatias.workout_tracker.api.dto.user.AuthResponseDTO
import com.luizmatias.workout_tracker.api.exception.common_exceptions.UnauthorizedException
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
    fun login(@RequestBody @Valid refreshTokenDTO: RefreshTokenRequestDTO): ResponseEntity<TokenDTO> {
        val response = refreshTokenService.regenerateTokens(refreshTokenDTO.refreshToken)
        return ResponseEntity.ok(response)
    }

}