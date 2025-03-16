package com.luizmatias.workout_tracker.features.workout_log_post.repository

import com.luizmatias.workout_tracker.features.user.model.User
import com.luizmatias.workout_tracker.features.workout_log_post.model.WorkoutLogPost
import jakarta.transaction.Transactional
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository

@Repository
interface WorkoutLogPostRepository : JpaRepository<WorkoutLogPost, Long> {
    @Modifying
    @Transactional
    @Query("DELETE FROM WorkoutLogPost")
    fun deleteAllWorkoutLogPosts()

    @Query("SELECT wlp FROM WorkoutLogPost wlp WHERE wlp.user = :user")
    fun findAllByUser(
        user: User,
        pageable: Pageable,
    ): Page<WorkoutLogPost>
}
