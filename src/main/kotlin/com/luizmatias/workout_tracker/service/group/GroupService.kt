package com.luizmatias.workout_tracker.service.group

import com.luizmatias.workout_tracker.model.group.Group
import com.luizmatias.workout_tracker.model.user.User
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable

interface GroupService {
    fun getAllGroups(
        user: User,
        pageable: Pageable,
    ): Page<Group>

    fun getGroupById(
        id: Long,
        user: User,
    ): Group?

    fun createGroup(group: Group): Group

    fun createInviteToken(
        groupId: Long,
        user: User,
    ): String

    fun updateGroup(
        id: Long,
        group: Group,
    ): Group?

    fun deleteGroup(id: Long): Boolean
}
