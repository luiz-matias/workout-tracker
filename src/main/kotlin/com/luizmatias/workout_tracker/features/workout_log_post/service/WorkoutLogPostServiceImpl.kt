package com.luizmatias.workout_tracker.features.workout_log_post.service

import com.luizmatias.workout_tracker.config.exception.common_exceptions.ForbiddenException
import com.luizmatias.workout_tracker.config.exception.common_exceptions.NotFoundException
import com.luizmatias.workout_tracker.features.user.model.User
import com.luizmatias.workout_tracker.features.workout_log_post.model.WorkoutLogPost
import com.luizmatias.workout_tracker.features.workout_log_post.repository.WorkoutLogPostRepository
import java.time.Instant
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

    override fun getWorkoutLogPostById(
        id: Long,
        user: User,
    ): WorkoutLogPost {
        val workoutLogPost =
            workoutLogPostRepository.findById(id).orElseThrow {
                NotFoundException(WORKOUT_LOG_POST_NOT_FOUND_MESSAGE)
            }

        if (!userCanManageWorkoutLogPost(user, workoutLogPost)) {
            throw ForbiddenException("User does not have access to this workout log post.")
        }

        return workoutLogPost
    }

    override fun createWorkoutLogPost(workoutLogPost: WorkoutLogPost): WorkoutLogPost =
        workoutLogPostRepository.save(workoutLogPost)

    override fun updateWorkoutLogPost(
        id: Long,
        workoutLogPost: WorkoutLogPost,
        user: User,
    ): WorkoutLogPost {
        val storedWorkoutLogPost =
            workoutLogPostRepository.findById(id).orElseThrow {
                NotFoundException(WORKOUT_LOG_POST_NOT_FOUND_MESSAGE)
            }

        if (!userCanManageWorkoutLogPost(user, storedWorkoutLogPost)) {
            throw ForbiddenException("User can't update this workout log post.")
        }

        return workoutLogPostRepository.save(
            storedWorkoutLogPost.copy(
                id = id,
                title = workoutLogPost.title,
                description = workoutLogPost.description,
                photoUrl = workoutLogPost.photoUrl,
            ),
        )
    }

    override fun deleteWorkoutLogPost(
        id: Long,
        user: User,
    ) {
        val storedWorkoutLogPost =
            workoutLogPostRepository.findById(id).orElseThrow {
                NotFoundException(WORKOUT_LOG_POST_NOT_FOUND_MESSAGE)
            }

        if (!userCanManageWorkoutLogPost(user, storedWorkoutLogPost)) {
            throw ForbiddenException("User can't delete this workout log post.")
        }

        workoutLogPostRepository.save(
            storedWorkoutLogPost.copy(
                deletedAt = Instant.now(),
            ),
        )
    }

    private fun userCanManageWorkoutLogPost(
        user: User,
        workoutLogPost: WorkoutLogPost,
    ): Boolean = user.isAdmin() || workoutLogPost.user.id != user.id

    companion object {
        private const val WORKOUT_LOG_POST_NOT_FOUND_MESSAGE = "Workout log post not found."
    }
}
