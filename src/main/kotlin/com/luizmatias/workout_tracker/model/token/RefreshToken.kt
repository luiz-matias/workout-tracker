package com.luizmatias.workout_tracker.model.token

import com.luizmatias.workout_tracker.model.user.User
import jakarta.persistence.*
import java.time.Instant

@Entity
@Table(name = "refresh_tokens")
data class RefreshToken(
    @Id @GeneratedValue val id: Long?,
    @OneToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    val user: User,
    val token: String,
    val expiresAt: Instant,
)