package com.luizmatias.workout_tracker.service.group_member

import com.luizmatias.workout_tracker.model.group.Group
import com.luizmatias.workout_tracker.model.group_members.GroupMember
import com.luizmatias.workout_tracker.model.user.User
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
        group: Group,
        user: User,
    )
}
