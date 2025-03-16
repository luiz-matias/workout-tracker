package com.luizmatias.workout_tracker.features.auth_token.service

import com.luizmatias.workout_tracker.features.auth_token.dto.TokenDTO
import com.luizmatias.workout_tracker.features.user.model.User

interface RefreshTokenService {
    fun generateTokensFromUserAuth(user: User): TokenDTO

    fun regenerateTokens(refreshToken: String): TokenDTO
}
