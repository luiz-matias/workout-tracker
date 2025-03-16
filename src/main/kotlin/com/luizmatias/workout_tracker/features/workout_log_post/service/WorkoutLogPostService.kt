package com.luizmatias.workout_tracker.features.workout_log_post.service

import com.luizmatias.workout_tracker.features.user.model.User
import com.luizmatias.workout_tracker.features.workout_log_post.model.WorkoutLogPost
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import com.luizmatias.workout_tracker.features.workout_log_group_post.service.WorkoutLogGroupPostService

/**
 * Service for managing workout log posts. This service is accountable for managing CRUD operations related to workout
 * log posts.
 *
 * e.g. A user can create a workout log post, update it, delete it, and list all posts they have created by using this
 * service.
 *
 * Note that this service only creates workout log posts linked to the user account. In order to share the workout into
 * groups the user is part of, the user must use the [WorkoutLogGroupPostService].
 */
interface WorkoutLogPostService {
    /**
     * Get all workout log posts based on a user.
     */
    fun getAllWorkoutLogPostsByUser(
        user: User,
        pageable: Pageable,
    ): Page<WorkoutLogPost>

    /**
     * Get a workout log post by its ID.
     */
    fun getWorkoutLogPostById(
        id: Long,
        user: User,
    ): WorkoutLogPost

    /**
     * Create a new workout log post.
     */
    fun createWorkoutLogPost(workoutLogPost: WorkoutLogPost): WorkoutLogPost

    /**
     * Update a workout log post.
     */
    fun updateWorkoutLogPost(
        id: Long,
        workoutLogPost: WorkoutLogPost,
        user: User,
    ): WorkoutLogPost

    /**
     * Delete a workout log post.
     */
    fun deleteWorkoutLogPost(
        id: Long,
        user: User,
    )
}
