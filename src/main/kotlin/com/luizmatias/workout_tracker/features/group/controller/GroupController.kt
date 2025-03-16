package com.luizmatias.workout_tracker.features.group.controller

import com.luizmatias.workout_tracker.features.common.dto.MessageResponseDTO
import com.luizmatias.workout_tracker.features.common.dto.PageRequestDTO
import com.luizmatias.workout_tracker.features.common.dto.PageResponseDTO
import com.luizmatias.workout_tracker.features.common.dto.toPageRequest
import com.luizmatias.workout_tracker.features.common.dto.toPageResponseDTO
import com.luizmatias.workout_tracker.features.group.dto.GroupCreationDTO
import com.luizmatias.workout_tracker.features.group.dto.GroupInviteDTO
import com.luizmatias.workout_tracker.features.group.dto.GroupResponseDTO
import com.luizmatias.workout_tracker.features.group.dto.toGroupCreation
import com.luizmatias.workout_tracker.features.group.dto.toGroupEdit
import com.luizmatias.workout_tracker.features.group.dto.toGroupResponseDTO
import com.luizmatias.workout_tracker.features.group.model.Group
import com.luizmatias.workout_tracker.features.group.service.GroupService
import com.luizmatias.workout_tracker.features.group_member.service.GroupMemberService
import com.luizmatias.workout_tracker.features.user.model.UserPrincipal
import com.luizmatias.workout_tracker.features.user.service.UserService
import jakarta.validation.Valid
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/groups")
class GroupController @Autowired constructor(
    private val userService: UserService,
    private val groupService: GroupService,
    private val groupMemberService: GroupMemberService,
) {
    @GetMapping("", "/")
    fun getAll(
        @AuthenticationPrincipal principal: UserPrincipal,
        @Valid pageRequestDTO: PageRequestDTO,
    ): ResponseEntity<PageResponseDTO<GroupResponseDTO>> {
        val user = userService.getUserByEmail(principal.username)
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
        val user = userService.getUserByEmail(principal.username)
        return ResponseEntity.ok(groupService.getGroupById(id, user).toGroupResponseDTO())
    }

    @PostMapping("", "/")
    fun create(
        @RequestBody @Valid groupCreationDTO: GroupCreationDTO,
        @AuthenticationPrincipal principal: UserPrincipal,
    ): ResponseEntity<GroupResponseDTO> {
        val user = userService.getUserByEmail(principal.username)
        val group = groupService.createGroup(groupCreationDTO.toGroupCreation(user))
        return ResponseEntity(group.toGroupResponseDTO(), HttpStatus.CREATED)
    }

    @GetMapping("/{id}/get-invite")
    fun getInvite(
        @PathVariable id: Long,
        @AuthenticationPrincipal principal: UserPrincipal,
    ): ResponseEntity<GroupInviteDTO> {
        val user = userService.getUserByEmail(principal.username)
        val token = groupService.createInviteToken(id, user)
        return ResponseEntity(GroupInviteDTO(token), HttpStatus.CREATED)
    }

    @GetMapping("/{id}/exit")
    fun exitFromGroup(
        @PathVariable id: Long,
        @AuthenticationPrincipal principal: UserPrincipal,
    ): ResponseEntity<MessageResponseDTO> {
        val user = userService.getUserByEmail(principal.username)
        groupMemberService.exitFromGroup(id, user)
        return ResponseEntity.ok(MessageResponseDTO("Successfully exited the group."))
    }

    @PutMapping("/{id}")
    fun editGroup(
        @PathVariable id: Long,
        @RequestBody @Valid groupUpdateDTO: GroupCreationDTO,
        @AuthenticationPrincipal principal: UserPrincipal,
    ): ResponseEntity<GroupResponseDTO> {
        val user = userService.getUserByEmail(principal.username)
        val group = groupService.updateGroup(id, groupUpdateDTO.toGroupEdit(), user)
        return ResponseEntity.ok(group.toGroupResponseDTO())
    }

    @DeleteMapping("/{id}")
    fun deleteGroup(
        @PathVariable id: Long,
        @AuthenticationPrincipal principal: UserPrincipal,
    ): ResponseEntity<MessageResponseDTO> {
        val user = userService.getUserByEmail(principal.username)
        groupService.deleteGroup(id, user)
        return ResponseEntity.ok(MessageResponseDTO("Group deleted successfully."))
    }
}
