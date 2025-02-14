package com.luizmatias.workout_tracker.service.workout_log_post

import com.luizmatias.workout_tracker.model.user.User
import com.luizmatias.workout_tracker.model.workout_log_post.WorkoutLogPost
import com.luizmatias.workout_tracker.repository.WorkoutLogPostRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class WorkoutLogPostServiceImpl @Autowired constructor(
    private val workoutLogPostRepository: WorkoutLogPostRepository,
) : WorkoutLogPostService {
    override fun getAllWorkoutLogPostsByUser(user: User): List<WorkoutLogPost> =
        workoutLogPostRepository.findAllByUser(user)

    override fun getWorkoutLogPostById(id: Long): WorkoutLogPost? = workoutLogPostRepository.findById(id).orElse(null)

    override fun createWorkoutLogPost(workoutLogPost: WorkoutLogPost): WorkoutLogPost =
        workoutLogPostRepository.save(workoutLogPost)

    override fun updateWorkoutLogPost(
        id: Long,
        workoutLogPost: WorkoutLogPost,
    ): WorkoutLogPost? {
        if (workoutLogPostRepository.existsById(id)) {
            return workoutLogPostRepository.save(workoutLogPost.copy(id = id))
        }
        return null
    }

    override fun deleteWorkoutLogPost(id: Long): Boolean {
        if (workoutLogPostRepository.existsById(id)) {
            workoutLogPostRepository.deleteById(id)
            return true
        }
        return false
    }
}
