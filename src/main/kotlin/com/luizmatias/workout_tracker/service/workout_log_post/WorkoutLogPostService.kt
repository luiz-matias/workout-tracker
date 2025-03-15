package com.luizmatias.workout_tracker.service.workout_log_post

import com.luizmatias.workout_tracker.model.user.User
import com.luizmatias.workout_tracker.model.workout_log_post.WorkoutLogPost
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import com.luizmatias.workout_tracker.service.workout_log_group_post.WorkoutLogGroupPostService

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
    fun getWorkoutLogPostById(id: Long): WorkoutLogPost

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
    ): WorkoutLogPost

    /**
     * Delete a workout log post.
     */
    fun deleteWorkoutLogPost(id: Long)
}
