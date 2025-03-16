package com.luizmatias.workout_tracker.features.workout_log_group_post.service

import com.luizmatias.workout_tracker.config.exception.common_exceptions.BusinessRuleConflictException
import com.luizmatias.workout_tracker.config.exception.common_exceptions.ForbiddenException
import com.luizmatias.workout_tracker.config.exception.common_exceptions.NotFoundException
import com.luizmatias.workout_tracker.features.group_member.model.GroupMember
import com.luizmatias.workout_tracker.features.group_member.service.GroupMemberService
import com.luizmatias.workout_tracker.features.user.model.User
import com.luizmatias.workout_tracker.features.workout_log_group_post.model.WorkoutLogGroupPost
import com.luizmatias.workout_tracker.features.workout_log_group_post.repository.WorkoutLogGroupPostRepository
import com.luizmatias.workout_tracker.features.workout_log_post.model.WorkoutLogPost
import com.luizmatias.workout_tracker.features.workout_log_post.service.WorkoutLogPostService
import java.time.Instant
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service

@Service
class WorkoutGroupLogPostServiceImpl @Autowired constructor(
    private val workoutLogGroupPostRepository: WorkoutLogGroupPostRepository,
    private val workoutLogPostService: WorkoutLogPostService,
    private val groupMemberService: GroupMemberService,
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

    override fun createWorkoutGroupLogPosts(
        workoutLogPostId: Long,
        groupIds: List<Long>,
        user: User,
    ): List<WorkoutLogGroupPost> {
        val workoutLogPost = workoutLogPostService.getWorkoutLogPostById(workoutLogPostId, user)
        val groupMembers = groupMemberService
            .getAllGroupRegistrationsByUser(user, Pageable.unpaged())
            .filter { it.group.id in groupIds }

        if (groupMembers.isEmpty) {
            throw BusinessRuleConflictException("User is not part of any of the groups provided.")
        }

        val workoutLogGroupPosts = groupMembers.map { groupMember ->
            WorkoutLogGroupPost(
                id = null,
                workoutLogPost = workoutLogPost,
                groupMember = groupMember,
                createdAt = Instant.now(),
            )
        }

        return workoutLogGroupPostRepository.saveAll(workoutLogGroupPosts)
    }

    override fun deleteWorkoutGroupLogPost(id: Long, user: User) {
        val storedGroupPost = workoutLogGroupPostRepository.findById(id).orElseThrow {
            NotFoundException("Workout log group post not found.")
        }

        if (!userCanManageWorkoutGroupLogPost(user, storedGroupPost)) {
            throw ForbiddenException("User can't delete this workout log group post.")
        }

        workoutLogGroupPostRepository.save(storedGroupPost.copy(deletedAt = Instant.now()))
    }

    private fun userCanManageWorkoutGroupLogPost(user: User, workoutLogGroupPost: WorkoutLogGroupPost): Boolean {
        return user.isAdmin() || workoutLogGroupPost.groupMember.user.id == user.id
    }
}
