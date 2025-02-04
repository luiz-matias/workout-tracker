package com.luizmatias.workout_tracker.model

import jakarta.persistence.*

@Entity
@Table(name = "users")
data class User(
    @Id @GeneratedValue val id: Long? = null,
    @Column(nullable = false, unique = true) val email: String,
    val password: String,
    @Enumerated(EnumType.STRING) val role: AccountRole,
    val isEmailVerified: Boolean,
    val isCredentialsExpired: Boolean,
    val isAccountExpired: Boolean,
    val isLocked: Boolean,
    val isEnabled: Boolean
)