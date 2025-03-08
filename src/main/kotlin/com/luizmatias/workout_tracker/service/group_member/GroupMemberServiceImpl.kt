package com.luizmatias.workout_tracker.service.group_member

import com.luizmatias.workout_tracker.config.api.exception.common_exceptions.BusinessRuleConflictException
import com.luizmatias.workout_tracker.config.api.exception.common_exceptions.NotFoundException
import com.luizmatias.workout_tracker.model.group.Group
import com.luizmatias.workout_tracker.model.group_members.GroupMember
import com.luizmatias.workout_tracker.model.temporary_token.TemporaryToken.Companion.isExpired
import com.luizmatias.workout_tracker.model.temporary_token.TemporaryToken.Companion.isValidType
import com.luizmatias.workout_tracker.model.temporary_token.TokenType
import com.luizmatias.workout_tracker.model.user.User
import com.luizmatias.workout_tracker.repository.GroupMemberRepository
import com.luizmatias.workout_tracker.service.group.GroupService
import com.luizmatias.workout_tracker.service.temporary_token.TemporaryTokenService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.time.Instant
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable

@Service
class GroupMemberServiceImpl @Autowired constructor(
    private val groupService: GroupService,
    private val groupMemberRepository: GroupMemberRepository,
    private val temporaryTokenService: TemporaryTokenService,
) : GroupMemberService {
    override fun getAllGroupMembersByGroup(group: Group, pageable: Pageable): Page<GroupMember> =
        groupMemberRepository.findAllByGroup(group, pageable)

    override fun getAllGroupMembersByUser(user: User, pageable: Pageable): Page<GroupMember> = groupMemberRepository.findAllByUser(user, pageable)

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
        val group =
            groupService.getGroupById(groupId, temporaryToken.createdBy)
                ?: throw NotFoundException("Group from invite not found.")

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

    override fun updateGroupMember(
        id: Long,
        groupMember: GroupMember,
    ): GroupMember? {
        if (groupMemberRepository.existsById(id)) {
            return groupMemberRepository.save(groupMember.copy(id = id))
        }
        return null
    }

    override fun deleteGroupMember(id: Long): Boolean {
        if (groupMemberRepository.existsById(id)) {
            groupMemberRepository.deleteById(id)
            return true
        }
        return false
    }
}
