package com.luizmatias.workout_tracker.service.group

import com.luizmatias.workout_tracker.model.group.Group
import com.luizmatias.workout_tracker.repository.GroupRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class GroupServiceImpl @Autowired constructor(
    private val groupRepository: GroupRepository
) : GroupService {
    override fun getAllGroups(): List<Group> {
        return groupRepository.findAll()
    }

    override fun getGroupById(id: Long): Group? {
        return groupRepository.findById(id).orElse(null)
    }


    override fun createGroup(group: Group): Group {
        return groupRepository.save(group)
    }

    override fun updateGroup(id: Long, group: Group): Group? {
        if (groupRepository.existsById(id)) {
            return groupRepository.save(group.copy(id = id))
        }
        return null
    }

    override fun deleteGroup(id: Long): Boolean {
        if (groupRepository.existsById(id)) {
            groupRepository.deleteById(id)
            return true
        }
        return false
    }


}