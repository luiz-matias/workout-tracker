package com.luizmatias.workout_tracker.service.group_member

import com.luizmatias.workout_tracker.model.group.Group
import com.luizmatias.workout_tracker.model.group_members.GroupMember
import com.luizmatias.workout_tracker.model.user.User
import com.luizmatias.workout_tracker.repository.GroupMemberRepository
import com.luizmatias.workout_tracker.repository.GroupRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class GroupMemberServiceImpl @Autowired constructor(
    private val groupMemberRepository: GroupMemberRepository
) : GroupMemberService {
    override fun getAllGroupMembersByGroup(group: Group): List<GroupMember> {
        return groupMemberRepository.findAllByGroup(group)
    }

    override fun getAllGroupMembersByUser(user: User): List<GroupMember> {
        return groupMemberRepository.findAllByUser(user)
    }

    override fun updateGroupMember(id: Long, groupMember: GroupMember): GroupMember? {
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