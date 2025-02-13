package com.luizmatias.workout_tracker.service.workout_log_group_post

import com.luizmatias.workout_tracker.model.group_members.GroupMember
import com.luizmatias.workout_tracker.model.workout_log_group_post.WorkoutLogGroupPost
import com.luizmatias.workout_tracker.model.workout_log_post.WorkoutLogPost
import com.luizmatias.workout_tracker.repository.WorkoutLogGroupPostRepository
import com.luizmatias.workout_tracker.service.workout_log_group_post.WorkoutLogGroupPostService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class WorkoutGroupLogPostServiceImpl @Autowired constructor(
    private val workoutLogGroupPostRepository: WorkoutLogGroupPostRepository
) : WorkoutLogGroupPostService {
    override fun getAllWorkoutLogGroupPostsByGroupMember(groupMember: GroupMember): List<WorkoutLogGroupPost> {
        return workoutLogGroupPostRepository.findAllByGroupMember(groupMember)
    }

    override fun getAllWorkoutLogGroupPostsByWorkoutLogPost(workoutLogPost: WorkoutLogPost): List<WorkoutLogGroupPost> {
        return workoutLogGroupPostRepository.findAllByWorkoutLogPost(workoutLogPost)
    }

    override fun getWorkoutGroupLogPostById(id: Long): WorkoutLogGroupPost? {
        return workoutLogGroupPostRepository.findById(id).orElse(null)
    }

    override fun createWorkoutGroupLogPost(workoutLogGroupPost: WorkoutLogGroupPost): WorkoutLogGroupPost {
        return workoutLogGroupPostRepository.save(workoutLogGroupPost)
    }

    override fun updateWorkoutGroupLogPost(id: Long, workoutLogGroupPost: WorkoutLogGroupPost): WorkoutLogGroupPost? {
        if (workoutLogGroupPostRepository.existsById(id)) {
            return workoutLogGroupPostRepository.save(workoutLogGroupPost.copy(id = id))
        }
        return null
    }

    override fun deleteWorkoutGroupLogPost(id: Long): Boolean {
        if (workoutLogGroupPostRepository.existsById(id)) {
            workoutLogGroupPostRepository.deleteById(id)
            return true
        }
        return false
    }


}