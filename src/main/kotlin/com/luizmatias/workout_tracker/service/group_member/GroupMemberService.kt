package com.luizmatias.workout_tracker.service.group_member

import com.luizmatias.workout_tracker.model.group.Group
import com.luizmatias.workout_tracker.model.group_members.GroupMember
import com.luizmatias.workout_tracker.model.user.User
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable

interface GroupMemberService {
    fun getAllGroupMembersByGroup(
        group: Group,
        pageable: Pageable,
    ): Page<GroupMember>

    fun getAllGroupMembersByUser(
        user: User,
        pageable: Pageable,
    ): Page<GroupMember>

    fun acceptInviteToGroup(
        groupToken: String,
        user: User,
    ): GroupMember

    fun updateGroupMember(
        id: Long,
        groupMember: GroupMember,
    ): GroupMember?

    fun deleteGroupMember(id: Long): Boolean
}
