package com.luizmatias.workout_tracker.service.workout_log_post

import com.luizmatias.workout_tracker.config.api.exception.common_exceptions.NotFoundException
import com.luizmatias.workout_tracker.model.user.User
import com.luizmatias.workout_tracker.model.workout_log_post.WorkoutLogPost
import com.luizmatias.workout_tracker.repository.WorkoutLogPostRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service

@Service
class WorkoutLogPostServiceImpl @Autowired constructor(
    private val workoutLogPostRepository: WorkoutLogPostRepository,
) : WorkoutLogPostService {
    override fun getAllWorkoutLogPostsByUser(
        user: User,
        pageable: Pageable,
    ): Page<WorkoutLogPost> = workoutLogPostRepository.findAllByUser(user, pageable)

    override fun getWorkoutLogPostById(id: Long): WorkoutLogPost =
        workoutLogPostRepository.findById(id).orElseThrow { NotFoundException("Workout log post not found.") }

    override fun createWorkoutLogPost(workoutLogPost: WorkoutLogPost): WorkoutLogPost =
        workoutLogPostRepository.save(workoutLogPost)

    override fun updateWorkoutLogPost(
        id: Long,
        workoutLogPost: WorkoutLogPost,
    ): WorkoutLogPost {
        if (!workoutLogPostRepository.existsById(id)) {
            throw NotFoundException("Workout log post not found.")
        }

        return workoutLogPostRepository.save(workoutLogPost.copy(id = id))
    }

    override fun deleteWorkoutLogPost(id: Long) {
        if (!workoutLogPostRepository.existsById(id)) {
            throw NotFoundException("Workout log post not found.")
        }

        workoutLogPostRepository.deleteById(id)
    }
}
