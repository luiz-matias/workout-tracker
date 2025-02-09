package com.luizmatias.workout_tracker.service.auth.jwt

import com.luizmatias.workout_tracker.api.dto.token.TokenDTO
import com.luizmatias.workout_tracker.api.exception.common_exceptions.UnauthorizedException
import com.luizmatias.workout_tracker.model.token.RefreshToken
import com.luizmatias.workout_tracker.model.user.User
import com.luizmatias.workout_tracker.repository.RefreshTokenRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import java.time.Instant
import java.util.*

@Service
class RefreshTokenServiceImpl @Autowired constructor(
    private val jwtService: JWTService,
    private val refreshTokenRepository: RefreshTokenRepository
) : RefreshTokenService {

    @Value("\${jwt.refresh-token-expiry-time-in-days}")
    private lateinit var refreshTokenExpiryTime: String

    override fun generateTokensFromUserAuth(user: User): TokenDTO {
        var refreshToken = refreshTokenRepository.findByUser(user) ?: RefreshToken(
            id = null,
            user = user,
            token = getRandomUUID(),
            expiry = getExpiryTime()
        )
        refreshToken = refreshTokenRepository.save(
            refreshToken.copy(
                token = getRandomUUID(),
                expiry = getExpiryTime()
            )
        )

        val accessToken = jwtService.generateAccessToken(user.email)
        return TokenDTO(accessToken, refreshToken.token)
    }

    override fun regenerateTokens(refreshToken: String): TokenDTO {
        var refreshTokenEntity =
            refreshTokenRepository.findByToken(refreshToken) ?: throw UnauthorizedException("Refresh token not found.")
        if (refreshTokenEntity.expiry.isBefore(Instant.now())) {
            refreshTokenRepository.delete(refreshTokenEntity)
            throw UnauthorizedException("Refresh token expired.")
        }
        refreshTokenEntity = refreshTokenEntity.copy(
            token = getRandomUUID(),
            expiry = getExpiryTime()
        )
        val accessToken = jwtService.generateAccessToken(refreshTokenEntity.user.email)
        refreshTokenEntity = refreshTokenRepository.save(refreshTokenEntity)

        return TokenDTO(accessToken, refreshTokenEntity.token)
    }

    private fun getRandomUUID() = UUID.randomUUID().toString()

    private fun getExpiryTime() = Instant.now().plusSeconds(refreshTokenExpiryTime.toLong() * DAY_IN_SECONDS)

    companion object {
        private const val DAY_IN_SECONDS = 86400
    }

}