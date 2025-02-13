package com.luizmatias.workout_tracker.service.workout_log_post

import com.luizmatias.workout_tracker.model.user.User
import com.luizmatias.workout_tracker.model.workout_log_post.WorkoutLogPost

interface WorkoutLogPostService {

    fun getAllWorkoutLogPostsByUser(user: User): List<WorkoutLogPost>

    fun getWorkoutLogPostById(id: Long): WorkoutLogPost?

    fun createWorkoutLogPost(workoutLogPost: WorkoutLogPost): WorkoutLogPost

    fun updateWorkoutLogPost(id: Long, workoutLogPost: WorkoutLogPost): WorkoutLogPost?

    fun deleteWorkoutLogPost(id: Long): Boolean

}