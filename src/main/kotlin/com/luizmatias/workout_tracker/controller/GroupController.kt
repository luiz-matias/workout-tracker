package com.luizmatias.workout_tracker.controller

import com.luizmatias.workout_tracker.config.api.exception.common_exceptions.NotFoundException
import com.luizmatias.workout_tracker.dto.group.GroupCreationDTO
import com.luizmatias.workout_tracker.dto.group.GroupInviteDTO
import com.luizmatias.workout_tracker.dto.group.GroupResponseDTO
import com.luizmatias.workout_tracker.dto.mapper.toGroup
import com.luizmatias.workout_tracker.dto.mapper.toGroupResponseDTO
import com.luizmatias.workout_tracker.model.user.UserPrincipal
import com.luizmatias.workout_tracker.service.group.GroupService
import com.luizmatias.workout_tracker.service.user.UserService
import jakarta.validation.Valid
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/groups")
class GroupController @Autowired constructor(
    private val userService: UserService,
    private val groupService: GroupService
) {

    @GetMapping("", "/")
    fun getAll(@AuthenticationPrincipal principal: UserPrincipal): ResponseEntity<List<GroupResponseDTO>> {
        val user = userService.getUserByEmail(principal.username) ?: throw NotFoundException("User not found")
        return ResponseEntity.ok(groupService.getAllGroups(user).map { it.toGroupResponseDTO() })
    }

    @GetMapping("/{id}")
    fun getById(
        @PathVariable id: Long,
        @AuthenticationPrincipal principal: UserPrincipal
    ): ResponseEntity<GroupResponseDTO> {
        val user = userService.getUserByEmail(principal.username) ?: throw NotFoundException("User not found")
        return groupService.getGroupById(id, user)?.let { ResponseEntity.ok(it.toGroupResponseDTO()) }
            ?: throw NotFoundException("Group not found")
    }

    @PostMapping("", "/")
    fun create(
        @RequestBody @Valid groupCreationDTO: GroupCreationDTO,
        @AuthenticationPrincipal principal: UserPrincipal
    ): ResponseEntity<GroupResponseDTO> {
        val user = userService.getUserByEmail(principal.username) ?: throw NotFoundException("User not found")
        val group = groupService.createGroup(groupCreationDTO.toGroup(user))
        return ResponseEntity(group.toGroupResponseDTO(), HttpStatus.CREATED)
    }

    @GetMapping("/{id}/create-invite")
    fun createInvite(
        @PathVariable id: Long,
        @AuthenticationPrincipal principal: UserPrincipal
    ): ResponseEntity<GroupInviteDTO> {
        val user = userService.getUserByEmail(principal.username) ?: throw NotFoundException("User not found")
        val token = groupService.createInviteToken(id, user)
        return ResponseEntity(GroupInviteDTO(token), HttpStatus.CREATED)
    }

}