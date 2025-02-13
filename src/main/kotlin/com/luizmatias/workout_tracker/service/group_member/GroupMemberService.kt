package com.luizmatias.workout_tracker.service.group_member

import com.luizmatias.workout_tracker.model.group.Group
import com.luizmatias.workout_tracker.model.group_members.GroupMember
import com.luizmatias.workout_tracker.model.user.User

interface GroupMemberService {

    fun getAllGroupMembersByGroup(group: Group): List<GroupMember>

    fun getAllGroupMembersByUser(user: User): List<GroupMember>

    fun updateGroupMember(id: Long, groupMember: GroupMember): GroupMember?

    fun deleteGroupMember(id: Long): Boolean

}