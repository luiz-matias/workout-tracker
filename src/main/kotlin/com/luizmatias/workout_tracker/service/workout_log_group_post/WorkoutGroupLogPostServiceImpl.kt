package com.luizmatias.workout_tracker.service.workout_log_group_post

import com.luizmatias.workout_tracker.model.group_members.GroupMember
import com.luizmatias.workout_tracker.model.workout_log_group_post.WorkoutLogGroupPost
import com.luizmatias.workout_tracker.model.workout_log_post.WorkoutLogPost
import com.luizmatias.workout_tracker.repository.WorkoutLogGroupPostRepository
import com.luizmatias.workout_tracker.service.workout_log_group_post.WorkoutLogGroupPostService
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

    override fun getWorkoutGroupLogPostById(id: Long): WorkoutLogGroupPost? =
        workoutLogGroupPostRepository.findById(id).orElse(null)

    override fun createWorkoutGroupLogPost(workoutLogGroupPost: WorkoutLogGroupPost): WorkoutLogGroupPost =
        workoutLogGroupPostRepository.save(workoutLogGroupPost)

    override fun updateWorkoutGroupLogPost(
        id: Long,
        workoutLogGroupPost: WorkoutLogGroupPost,
    ): WorkoutLogGroupPost? {
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
