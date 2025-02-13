package com.luizmatias.workout_tracker.service.group

import com.luizmatias.workout_tracker.model.group.Group

interface GroupService {

    fun getAllGroups(): List<Group>

    fun getGroupById(id: Long): Group?

    fun createGroup(group: Group): Group

    fun updateGroup(id: Long, group: Group): Group?

    fun deleteGroup(id: Long): Boolean

}