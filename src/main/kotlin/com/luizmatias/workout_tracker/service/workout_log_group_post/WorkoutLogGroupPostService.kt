package com.luizmatias.workout_tracker.service.workout_log_group_post

import com.luizmatias.workout_tracker.model.group_members.GroupMember
import com.luizmatias.workout_tracker.model.workout_log_group_post.WorkoutLogGroupPost
import com.luizmatias.workout_tracker.model.workout_log_post.WorkoutLogPost

interface WorkoutLogGroupPostService {
    fun getAllWorkoutLogGroupPostsByGroupMember(groupMember: GroupMember): List<WorkoutLogGroupPost>

    fun getAllWorkoutLogGroupPostsByWorkoutLogPost(workoutLogPost: WorkoutLogPost): List<WorkoutLogGroupPost>

    fun getWorkoutGroupLogPostById(id: Long): WorkoutLogGroupPost?

    fun createWorkoutGroupLogPost(workoutLogGroupPost: WorkoutLogGroupPost): WorkoutLogGroupPost

    fun updateWorkoutGroupLogPost(
        id: Long,
        workoutLogGroupPost: WorkoutLogGroupPost,
    ): WorkoutLogGroupPost?

    fun deleteWorkoutGroupLogPost(id: Long): Boolean
}
