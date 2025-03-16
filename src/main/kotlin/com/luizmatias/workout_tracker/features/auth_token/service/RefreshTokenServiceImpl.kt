package com.luizmatias.workout_tracker.features.auth_token.service

import com.luizmatias.workout_tracker.config.exception.common_exceptions.UnauthorizedException
import com.luizmatias.workout_tracker.features.auth_token.dto.TokenDTO
import com.luizmatias.workout_tracker.features.auth_token.model.RefreshToken
import com.luizmatias.workout_tracker.features.user.model.User
import com.luizmatias.workout_tracker.features.auth_token.repository.RefreshTokenRepository
import java.time.Instant
import java.util.UUID
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service

@Service
class RefreshTokenServiceImpl @Autowired constructor(
    private val jwtService: JWTService,
    private val refreshTokenRepository: RefreshTokenRepository,
) : RefreshTokenService {
    @Value("\${jwt.refresh-token-expiry-time-in-days}")
    private val refreshTokenExpiryTime: String? = null

    override fun generateTokensFromUserAuth(user: User): TokenDTO {
        var refreshToken =
            refreshTokenRepository.findByUser(user) ?: RefreshToken(
                id = null,
                user = user,
                token = getRandomUUID(),
                expiresAt = getExpiryTime(),
            )
        refreshToken =
            refreshTokenRepository.save(
                refreshToken.copy(
                    token = getRandomUUID(),
                    expiresAt = getExpiryTime(),
                ),
            )

        val accessToken = jwtService.generateAccessToken(user.email)
        return TokenDTO(accessToken, refreshToken.token)
    }

    override fun regenerateTokens(refreshToken: String): TokenDTO {
        var refreshTokenEntity =
            refreshTokenRepository.findByToken(refreshToken) ?: throw UnauthorizedException("Refresh token not found.")
        if (refreshTokenEntity.expiresAt.isBefore(Instant.now())) {
            refreshTokenRepository.delete(refreshTokenEntity)
            throw UnauthorizedException("Refresh token expired.")
        }
        refreshTokenEntity =
            refreshTokenEntity.copy(
                token = getRandomUUID(),
                expiresAt = getExpiryTime(),
            )
        val accessToken = jwtService.generateAccessToken(refreshTokenEntity.user.email)
        refreshTokenEntity = refreshTokenRepository.save(refreshTokenEntity)

        return TokenDTO(accessToken, refreshTokenEntity.token)
    }

    private fun getRandomUUID() = UUID.randomUUID().toString()

    private fun getExpiryTime() =
        Instant.now().plusSeconds((refreshTokenExpiryTime?.toLongOrNull() ?: 1) * DAY_IN_SECONDS)

    companion object {
        private const val DAY_IN_SECONDS = 86_400
    }
}
