package com.luizmatias.workout_tracker.service.auth.token

import com.luizmatias.workout_tracker.api.dto.token.TokenDTO
import com.luizmatias.workout_tracker.model.user.User

interface RefreshTokenService {
    fun generateTokensFromUserAuth(user: User): TokenDTO
    fun regenerateTokens(refreshToken: String): TokenDTO
}