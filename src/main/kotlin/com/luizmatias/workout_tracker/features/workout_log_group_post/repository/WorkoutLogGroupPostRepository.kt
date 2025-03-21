package com.luizmatias.workout_tracker.features.workout_log_group_post.repository

import com.luizmatias.workout_tracker.features.group_member.model.GroupMember
import com.luizmatias.workout_tracker.features.workout_log_group_post.model.WorkoutLogGroupPost
import com.luizmatias.workout_tracker.features.workout_log_post.model.WorkoutLogPost
import jakarta.transaction.Transactional
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository

@Repository
interface WorkoutLogGroupPostRepository : JpaRepository<WorkoutLogGroupPost, Long> {
    @Modifying
    @Transactional
    @Query("DELETE FROM WorkoutLogGroupPost")
    fun deleteAllWorkoutLogGroupPosts()

    @Query("SELECT wlgp FROM WorkoutLogGroupPost wlgp WHERE wlgp.groupMember = :groupMember")
    fun findAllByGroupMember(
        groupMember: GroupMember,
        pageable: Pageable,
    ): Page<WorkoutLogGroupPost>

    @Query("SELECT wlgp FROM WorkoutLogGroupPost wlgp WHERE wlgp.workoutLogPost = :workoutLogPost")
    fun findAllByWorkoutLogPost(
        workoutLogPost: WorkoutLogPost,
        pageable: Pageable,
    ): Page<WorkoutLogGroupPost>
}
