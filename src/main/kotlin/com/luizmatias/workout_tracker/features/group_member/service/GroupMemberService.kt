package com.luizmatias.workout_tracker.features.group_member.service

import com.luizmatias.workout_tracker.features.group.model.Group
import com.luizmatias.workout_tracker.features.group_member.model.GroupMember
import com.luizmatias.workout_tracker.features.user.model.User
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable

/**
 * Service for managing user registrations into groups. This service is accountable for managing the relationship
 * between users and groups, such as accepting invites, exiting groups, and listing all group registrations based on a
 * group or user.
 */
interface GroupMemberService {
    /**
     * Get all group registrations based on a group.
     */
    fun getAllGroupRegistrationsByGroup(
        group: Group,
        pageable: Pageable,
    ): Page<GroupMember>

    /**
     * Get all group registrations based on a user.
     */
    fun getAllGroupRegistrationsByUser(
        user: User,
        pageable: Pageable,
    ): Page<GroupMember>

    /**
     * Accept an invitation to a group.
     */
    fun acceptInviteToGroup(
        groupToken: String,
        user: User,
    ): GroupMember

    /**
     * Exit from a group.
     */
    fun exitFromGroup(
        groupId: Long,
        user: User,
    )
}
