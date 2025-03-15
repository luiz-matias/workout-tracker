package com.luizmatias.workout_tracker.controller

import com.luizmatias.workout_tracker.config.api.exception.common_exceptions.UserNotFoundException
import com.luizmatias.workout_tracker.dto.common.PageRequestDTO
import com.luizmatias.workout_tracker.dto.common.PageResponseDTO
import com.luizmatias.workout_tracker.dto.group.GroupCreationDTO
import com.luizmatias.workout_tracker.dto.group.GroupInviteDTO
import com.luizmatias.workout_tracker.dto.group.GroupResponseDTO
import com.luizmatias.workout_tracker.dto.mapper.toGroup
import com.luizmatias.workout_tracker.dto.mapper.toGroupResponseDTO
import com.luizmatias.workout_tracker.dto.mapper.toPageRequest
import com.luizmatias.workout_tracker.dto.mapper.toPageResponseDTO
import com.luizmatias.workout_tracker.model.group.Group
import com.luizmatias.workout_tracker.model.user.UserPrincipal
import com.luizmatias.workout_tracker.service.group.GroupService
import com.luizmatias.workout_tracker.service.user.UserService
import jakarta.validation.Valid
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/groups")
class GroupController @Autowired constructor(
    private val userService: UserService,
    private val groupService: GroupService,
) {
    @GetMapping("", "/")
    fun getAll(
        @AuthenticationPrincipal principal: UserPrincipal,
        @Valid pageRequestDTO: PageRequestDTO,
    ): ResponseEntity<PageResponseDTO<GroupResponseDTO>> {
        val user = userService.getUserByEmail(principal.username) ?: throw UserNotFoundException()
        return ResponseEntity.ok(
            groupService.getAllGroups(
                user,
                pageRequestDTO.toPageRequest(),
            ).toPageResponseDTO(Group::toGroupResponseDTO),
        )
    }

    @GetMapping("/{id}")
    fun getById(
        @PathVariable id: Long,
        @AuthenticationPrincipal principal: UserPrincipal,
    ): ResponseEntity<GroupResponseDTO> {
        val user = userService.getUserByEmail(principal.username) ?: throw UserNotFoundException()
        return ResponseEntity.ok(groupService.getGroupById(id, user).toGroupResponseDTO())
    }

    @PostMapping("", "/")
    fun create(
        @RequestBody @Valid groupCreationDTO: GroupCreationDTO,
        @AuthenticationPrincipal principal: UserPrincipal,
    ): ResponseEntity<GroupResponseDTO> {
        val user = userService.getUserByEmail(principal.username) ?: throw UserNotFoundException()
        val group = groupService.createGroup(groupCreationDTO.toGroup(user))
        return ResponseEntity(group.toGroupResponseDTO(), HttpStatus.CREATED)
    }

    @GetMapping("/{id}/get-invite")
    fun getInvite(
        @PathVariable id: Long,
        @AuthenticationPrincipal principal: UserPrincipal,
    ): ResponseEntity<GroupInviteDTO> {
        val user = userService.getUserByEmail(principal.username) ?: throw UserNotFoundException()
        val token = groupService.createInviteToken(id, user)
        return ResponseEntity(GroupInviteDTO(token), HttpStatus.CREATED)
    }
}
