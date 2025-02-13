package com.luizmatias.workout_tracker.controller

import com.luizmatias.workout_tracker.config.api.exception.common_exceptions.NotFoundException
import com.luizmatias.workout_tracker.model.group.Group
import com.luizmatias.workout_tracker.service.group.GroupService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/groups")
class GroupController @Autowired constructor(private val groupService: GroupService) {

    @GetMapping("", "/")
    fun getAll(): ResponseEntity<List<Group>> {
        return ResponseEntity.ok(groupService.getAllGroups())
    }

    @GetMapping("/{id}")
    fun getById(@PathVariable id: Long): ResponseEntity<Group> {
        return groupService.getGroupById(id)?.let { ResponseEntity.ok(it) }
            ?: throw NotFoundException("Group not found")
    }

    @PostMapping("", "/")
    fun create(@RequestBody group: Group): Group {
        return groupService.createGroup(group)
    }

    @PutMapping("/{id}")
    fun update(@PathVariable id: Long, @RequestBody group: Group): ResponseEntity<Group> {
        return groupService.updateGroup(id, group)?.let {
            ResponseEntity.ok(it)
        } ?: throw NotFoundException("Group not found")
    }

    @DeleteMapping("/{id}")
    fun delete(@PathVariable id: Long): ResponseEntity<Void> {
        return if (groupService.deleteGroup(id)) {
            ResponseEntity.noContent().build()
        } else {
            throw NotFoundException("Group not found")
        }
    }

}