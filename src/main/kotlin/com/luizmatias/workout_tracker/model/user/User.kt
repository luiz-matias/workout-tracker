package com.luizmatias.workout_tracker.model.user

import jakarta.persistence.*
import org.springframework.data.annotation.CreatedDate
import java.time.Instant
import java.util.*

@Entity
@Table(name = "users")
data class User(
    @Id @GeneratedValue
    val id: Long? = null,
    @Column(nullable = false)
    val firstName: String,
    @Column(nullable = false)
    val lastName: String,
    @Column(nullable = false, unique = true)
    val email: String,
    @Column(nullable = false)
    val isEmailVerified: Boolean,
    val profilePictureUrl: String?,
    @Column(nullable = false)
    val password: String,
    val instagramUsername: String?,
    val twitterUsername: String?,
    @Column(nullable = false)
    @Enumerated(EnumType.STRING) val role: AccountRole,
    @Column(nullable = false)
    val isEnabled: Boolean,
    @CreatedDate
    val createdAt: Instant,
)