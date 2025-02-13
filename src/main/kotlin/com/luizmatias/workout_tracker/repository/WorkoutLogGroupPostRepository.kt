package com.luizmatias.workout_tracker.repository

import com.luizmatias.workout_tracker.model.group_members.GroupMember
import com.luizmatias.workout_tracker.model.workout_log_group_post.WorkoutLogGroupPost
import com.luizmatias.workout_tracker.model.workout_log_post.WorkoutLogPost
import jakarta.transaction.Transactional
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
    fun findAllByGroupMember(groupMember: GroupMember): List<WorkoutLogGroupPost>

    @Query("SELECT wlgp FROM WorkoutLogGroupPost wlgp WHERE wlgp.workoutLogPost = :workoutLogPost")
    fun findAllByWorkoutLogPost(workoutLogPost: WorkoutLogPost): List<WorkoutLogGroupPost>

}