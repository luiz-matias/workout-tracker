package com.luizmatias.workout_tracker.features.group_member.service

import com.luizmatias.workout_tracker.config.exception.common_exceptions.BusinessRuleConflictException
import com.luizmatias.workout_tracker.config.exception.common_exceptions.NotFoundException
import com.luizmatias.workout_tracker.features.group.model.Group
import com.luizmatias.workout_tracker.features.group.service.GroupService
import com.luizmatias.workout_tracker.features.group_member.model.GroupMember
import com.luizmatias.workout_tracker.features.group_member.repository.GroupMemberRepository
import com.luizmatias.workout_tracker.features.temporary_token.model.TemporaryToken.Companion.isExpired
import com.luizmatias.workout_tracker.features.temporary_token.model.TemporaryToken.Companion.isValidType
import com.luizmatias.workout_tracker.features.temporary_token.model.TokenType
import com.luizmatias.workout_tracker.features.temporary_token.service.TemporaryTokenService
import com.luizmatias.workout_tracker.features.user.model.User
import java.time.Instant
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service

@Service
class GroupMemberServiceImpl @Autowired constructor(
    private val groupService: GroupService,
    private val groupMemberRepository: GroupMemberRepository,
    private val temporaryTokenService: TemporaryTokenService,
) : GroupMemberService {
    override fun getAllGroupRegistrationsByGroup(
        group: Group,
        pageable: Pageable,
    ): Page<GroupMember> = groupMemberRepository.findAllByGroup(group, pageable)

    override fun getAllGroupRegistrationsByUser(
        user: User,
        pageable: Pageable,
    ): Page<GroupMember> = groupMemberRepository.findAllByUser(user, pageable)

    override fun acceptInviteToGroup(
        groupToken: String,
        user: User,
    ): GroupMember {
        val temporaryToken =
            temporaryTokenService
                .getTemporaryTokenByToken(groupToken) ?: throw NotFoundException("Invite not found.")

        if (temporaryToken.isExpired()) {
            temporaryTokenService.deleteTemporaryToken(temporaryToken)
            throw BusinessRuleConflictException("Invite expired.")
        }

        if (!temporaryToken.isValidType(TokenType.GROUP_INVITE)) {
            throw BusinessRuleConflictException("Invalid token.")
        }

        val groupId =
            temporaryToken.extraData?.toLongOrNull() ?: throw NotFoundException("Group from invite not found.")
        val group = groupService.getGroupById(groupId, temporaryToken.createdBy)

        if (groupMemberRepository.existsByUserAndGroup(user, group)) {
            throw BusinessRuleConflictException("User already in group.")
        }

        return groupMemberRepository.save(
            GroupMember(
                id = null,
                user = user,
                group = group,
                workoutLogGroupPosts = emptyList(),
                joinedAt = Instant.now(),
            ),
        )
    }

    override fun exitFromGroup(
        groupId: Long,
        user: User,
    ) {
        val group = try {
            groupService.getGroupById(groupId, user)
        } catch (e: BusinessRuleConflictException) {
            throw BusinessRuleConflictException("User not allowed to exit this group.")
        }
        var groupMember =
            groupMemberRepository.findByGroupAndUser(group, user)
                ?: throw NotFoundException("User is not in this group.")

        groupMember =
            groupMember.copy(
                exitedAt = Instant.now(),
            )
        groupMemberRepository.save(groupMember)
    }
}
