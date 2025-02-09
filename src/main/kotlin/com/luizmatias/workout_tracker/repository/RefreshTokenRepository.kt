package com.luizmatias.workout_tracker.repository

import com.luizmatias.workout_tracker.model.token.RefreshToken
import com.luizmatias.workout_tracker.model.user.User
import jakarta.transaction.Transactional
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository

@Repository
interface RefreshTokenRepository : JpaRepository<RefreshToken, Long> {

    @Query("SELECT r FROM RefreshToken r WHERE r.token = :token")
    fun findByToken(token: String): RefreshToken?

    @Query("SELECT r FROM RefreshToken r WHERE r.user = :user")
    fun findByUser(user: User): RefreshToken?

    @Modifying
    @Transactional
    @Query("DELETE FROM RefreshToken")
    fun deleteAllRefreshTokens()

}