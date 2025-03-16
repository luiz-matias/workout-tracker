package com.luizmatias.workout_tracker.features.workout_log_group_post.service

import com.luizmatias.workout_tracker.features.group_member.model.GroupMember
import com.luizmatias.workout_tracker.features.user.model.User
import com.luizmatias.workout_tracker.features.workout_log_group_post.model.WorkoutLogGroupPost
import com.luizmatias.workout_tracker.features.workout_log_post.model.WorkoutLogPost
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable

/**
 * Service for managing workout log group posts. This service is accountable for managing user workout log posts linked
 * to its registration (group member relationship).
 *
 * e.g. A user can create their workout log post and then use this service to link this post to the groups they are
 * part of.
 *
 * Note that this service is not responsible for managing the workout log post itself, but only the relationship
 * between a workout log and a group registration. In other words, it will share the workout log post into other groups.
 */
interface WorkoutLogGroupPostService {
    /**
     * Get all workout log group posts based on a group member registration.
     */
    fun getAllWorkoutLogGroupPostsByGroupMember(
        groupMember: GroupMember,
        pageable: Pageable,
    ): Page<WorkoutLogGroupPost>

    /**
     * Get all workout log group posts based on a workout log post.
     */
    fun getAllWorkoutLogGroupPostsByWorkoutLogPost(
        workoutLogPost: WorkoutLogPost,
        pageable: Pageable,
    ): Page<WorkoutLogGroupPost>

    /**
     * Get a workout log group post by its ID.
     */
    fun getWorkoutGroupLogPostById(id: Long): WorkoutLogGroupPost

    /**
     * Create multiple group log posts for a given range of groups
     */
    fun createWorkoutGroupLogPosts(
        workoutLogPostId: Long,
        groupIds: List<Long>,
        user: User,
    ): List<WorkoutLogGroupPost>

    /**
     * Delete a workout log group post.
     */
    fun deleteWorkoutGroupLogPost(id: Long, user: User)
}
