package com.luizmatias.workout_tracker.features.temporary_token.repository

import com.luizmatias.workout_tracker.features.temporary_token.model.TemporaryToken
import jakarta.transaction.Transactional
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository

@Repository
interface TemporaryTokenRepository : JpaRepository<TemporaryToken, Long> {
    @Query("SELECT t FROM TemporaryToken t WHERE t.token = :token")
    fun findByToken(token: String): TemporaryToken?

    @Modifying
    @Transactional
    @Query("DELETE FROM TemporaryToken")
    fun deleteAllTemporaryTokens()
}
