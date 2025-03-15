package com.luizmatias.workout_tracker.service.group

import com.luizmatias.workout_tracker.model.group.Group
import com.luizmatias.workout_tracker.model.user.User
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable

/**
 * Service for managing groups. This service is accountable for managing CRUD operations related to groups.
 */
interface GroupService {
    /**
     * Get all groups that a user is part of.
     */
    fun getAllGroups(
        user: User,
        pageable: Pageable,
    ): Page<Group>

    /**
     * Get a group by its ID.
     */
    fun getGroupById(
        id: Long,
        user: User,
    ): Group

    /**
     * Create a new group.
     */
    fun createGroup(group: Group): Group

    /**
     * Create an invitation token for a group.
     */
    fun createInviteToken(
        groupId: Long,
        user: User,
    ): String

    /**
     * Update a group.
     */
    fun updateGroup(
        id: Long,
        group: Group,
        user: User,
    ): Group

    /**
     * Delete a group.
     */
    fun deleteGroup(
        id: Long,
        user: User,
    )
}
