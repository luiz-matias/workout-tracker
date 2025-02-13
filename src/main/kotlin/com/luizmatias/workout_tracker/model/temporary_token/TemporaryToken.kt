package com.luizmatias.workout_tracker.model.temporary_token

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.luizmatias.workout_tracker.model.user.User
import jakarta.persistence.*
import java.time.Instant

@Entity
@Table(name = "temporary_tokens")
data class TemporaryToken(
    @Id @GeneratedValue val id: Long?,
    @field:JsonIgnoreProperties("groups", "createdGroups", "workoutLogPosts", "temporaryTokens")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    val createdBy: User,
    val token: String,
    @Enumerated(EnumType.STRING) val type: TokenType,
    val extraData: String? = null,
    val expiresAt: Instant,
) {

    companion object {
        fun TemporaryToken.isValidType(
            desiredTokenType: TokenType
        ): Boolean {
            return desiredTokenType == this.type
        }

        fun TemporaryToken.isExpired(
            currentDatetime: Instant = Instant.now()
        ): Boolean {
            return this.expiresAt.isBefore(currentDatetime)
        }
    }

}