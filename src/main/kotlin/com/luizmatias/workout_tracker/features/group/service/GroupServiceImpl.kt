package com.luizmatias.workout_tracker.features.group.service

import com.luizmatias.workout_tracker.config.exception.common_exceptions.BusinessRuleConflictException
import com.luizmatias.workout_tracker.config.exception.common_exceptions.NotFoundException
import com.luizmatias.workout_tracker.features.group.model.Group
import com.luizmatias.workout_tracker.features.group.repository.GroupRepository
import com.luizmatias.workout_tracker.features.group_member.model.GroupMember
import com.luizmatias.workout_tracker.features.group_member.repository.GroupMemberRepository
import com.luizmatias.workout_tracker.features.temporary_token.model.TemporaryToken
import com.luizmatias.workout_tracker.features.temporary_token.model.TokenType
import com.luizmatias.workout_tracker.features.temporary_token.service.TemporaryTokenService
import com.luizmatias.workout_tracker.features.user.model.User
import java.time.Instant
import java.time.temporal.ChronoUnit
import java.util.UUID
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service

@Service
class GroupServiceImpl @Autowired constructor(
    private val groupRepository: GroupRepository,
    private val groupMemberRepository: GroupMemberRepository,
    private val temporaryTokenService: TemporaryTokenService,
) : GroupService {
    override fun getAllGroups(
        user: User,
        pageable: Pageable,
    ): Page<Group> = groupRepository.findAllByUser(user, pageable)

    override fun getGroupById(
        id: Long,
        user: User,
    ): Group {
        val group = groupRepository.findById(id).orElseThrow { NotFoundException(GROUP_NOT_FOUND_MESSAGE) }
        if (!userCanManageGroup(user, group)) {
            throw BusinessRuleConflictException("User not allowed to view this group.")
        }
        return group
    }

    override fun createGroup(group: Group): Group {
        val user = group.createdBy ?: throw BusinessRuleConflictException("Group must have a creator.")
        val savedGroup = groupRepository.save(group)
        groupMemberRepository.save(
            GroupMember(
                id = null,
                user = user,
                group = group,
                workoutLogGroupPosts = emptyList(),
                joinedAt = Instant.now(),
            ),
        )
        return savedGroup
    }

    override fun createInviteToken(
        groupId: Long,
        user: User,
    ): String {
        val group = groupRepository.findById(groupId).orElseThrow { NotFoundException(GROUP_NOT_FOUND_MESSAGE) }
        if (!userCanManageGroup(user, group)) {
            throw BusinessRuleConflictException("User not allowed to create invites.")
        }

        val temporaryToken =
            temporaryTokenService.createTemporaryToken(
                TemporaryToken(
                    id = null,
                    createdBy = user,
                    token = UUID.randomUUID().toString(),
                    type = TokenType.GROUP_INVITE,
                    extraData = groupId.toString(),
                    expiresAt = Instant.now().plus(GROUP_INVITE_EXPIRATION_DAYS, ChronoUnit.DAYS),
                ),
            )

        return temporaryToken.token
    }

    override fun updateGroup(
        id: Long,
        group: Group,
        user: User,
    ): Group {
        val storedGroup = groupRepository.findById(id).orElseThrow { NotFoundException(GROUP_NOT_FOUND_MESSAGE) }
        if (!userCanManageGroup(user, storedGroup)) {
            throw BusinessRuleConflictException("User not allowed to edit this group.")
        }
        return groupRepository.save(
            storedGroup.copy(
                id = id,
                name = group.name,
                description = group.description,
                bannerUrl = group.bannerUrl,
                measurementStrategy = group.measurementStrategy,
            ),
        )
    }

    override fun deleteGroup(
        id: Long,
        user: User,
    ) {
        val group = groupRepository.findById(id).orElseThrow { NotFoundException(GROUP_NOT_FOUND_MESSAGE) }
        if (!userCanDeleteGroup(user, group)) {
            throw BusinessRuleConflictException("User not allowed to delete this group.")
        }
        groupRepository.save(group.copy(deletedAt = Instant.now()))
    }

    private fun userCanManageGroup(
        user: User,
        group: Group,
    ): Boolean = user.isAdmin() || groupMemberRepository.existsByUserAndGroup(user, group)

    private fun userCanDeleteGroup(
        user: User,
        group: Group,
    ): Boolean = user.isAdmin() || group.createdBy?.id == user.id

    companion object {
        private const val GROUP_INVITE_EXPIRATION_DAYS = 7L
        private const val GROUP_NOT_FOUND_MESSAGE = "Group not found."
    }
}
