package com.luizmatias.workout_tracker.features.workout_log_group_post.service

import com.luizmatias.workout_tracker.config.exception.common_exceptions.NotFoundException
import com.luizmatias.workout_tracker.features.group_member.model.GroupMember
import com.luizmatias.workout_tracker.features.workout_log_group_post.model.WorkoutLogGroupPost
import com.luizmatias.workout_tracker.features.workout_log_post.model.WorkoutLogPost
import com.luizmatias.workout_tracker.features.workout_log_group_post.repository.WorkoutLogGroupPostRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service

@Service
class WorkoutGroupLogPostServiceImpl @Autowired constructor(
    private val workoutLogGroupPostRepository: WorkoutLogGroupPostRepository,
) : WorkoutLogGroupPostService {
    override fun getAllWorkoutLogGroupPostsByGroupMember(
        groupMember: GroupMember,
        pageable: Pageable,
    ): Page<WorkoutLogGroupPost> = workoutLogGroupPostRepository.findAllByGroupMember(groupMember, pageable)

    override fun getAllWorkoutLogGroupPostsByWorkoutLogPost(
        workoutLogPost: WorkoutLogPost,
        pageable: Pageable,
    ): Page<WorkoutLogGroupPost> = workoutLogGroupPostRepository.findAllByWorkoutLogPost(workoutLogPost, pageable)

    override fun getWorkoutGroupLogPostById(id: Long): WorkoutLogGroupPost =
        workoutLogGroupPostRepository.findById(id).orElseThrow {
            NotFoundException("Workout log group post not found.")
        }

    override fun createWorkoutGroupLogPost(workoutLogGroupPost: WorkoutLogGroupPost): WorkoutLogGroupPost =
        workoutLogGroupPostRepository.save(workoutLogGroupPost)

    override fun updateWorkoutGroupLogPost(
        id: Long,
        workoutLogGroupPost: WorkoutLogGroupPost,
    ): WorkoutLogGroupPost {
        if (!workoutLogGroupPostRepository.existsById(id)) {
            throw NotFoundException("Workout log group post not found.")
        }

        return workoutLogGroupPostRepository.save(workoutLogGroupPost.copy(id = id))
    }

    override fun deleteWorkoutGroupLogPost(id: Long) {
        if (!workoutLogGroupPostRepository.existsById(id)) {
            throw NotFoundException("Workout log group post not found.")
        }
        workoutLogGroupPostRepository.deleteById(id)
    }
}
