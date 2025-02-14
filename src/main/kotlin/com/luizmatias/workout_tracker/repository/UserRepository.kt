package com.luizmatias.workout_tracker.repository

import com.luizmatias.workout_tracker.model.user.User
import jakarta.transaction.Transactional
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository

@Repository
interface UserRepository : JpaRepository<User, Long> {
    @Query("SELECT u FROM User u WHERE u.email = :email")
    fun findByEmail(email: String): User?

    @Modifying
    @Transactional
    @Query("DELETE FROM User")
    fun deleteAllUsers()
}
