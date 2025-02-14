package com.luizmatias.workout_tracker.controller

import com.luizmatias.workout_tracker.config.api.exception.common_exceptions.UnauthorizedException
import com.luizmatias.workout_tracker.dto.common.MessageResponseDTO
import com.luizmatias.workout_tracker.dto.password_reset.PasswordResetRequestDTO
import com.luizmatias.workout_tracker.model.user.UserPrincipal
import com.luizmatias.workout_tracker.service.group_member.GroupMemberService
import com.luizmatias.workout_tracker.service.user.UserService
import jakarta.validation.Valid
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/token")
class TemporaryTokenController @Autowired constructor(
    private val userService: UserService,
    private val groupMemberService: GroupMemberService,
) {
    @GetMapping("/verify-email/{emailToken}")
    fun verifyEmail(
        @PathVariable emailToken: String,
    ): ResponseEntity<MessageResponseDTO> {
        userService.verifyEmail(emailToken)
        return ResponseEntity.ok(MessageResponseDTO("Email verified successfully."))
    }

    @PostMapping("/password-reset/{passwordToken}")
    fun resetPassword(
        @PathVariable passwordToken: String,
        @RequestBody @Valid passwordResetRequestDTO: PasswordResetRequestDTO,
    ): ResponseEntity<MessageResponseDTO> {
        userService.resetPassword(passwordToken, passwordResetRequestDTO.password)
        return ResponseEntity.ok(MessageResponseDTO("Password has been reset successfully."))
    }

    @GetMapping("/join/{groupToken}")
    fun joinGroupViaTemporaryToken(
        @PathVariable groupToken: String,
        @AuthenticationPrincipal principal: UserPrincipal,
    ): ResponseEntity<MessageResponseDTO> {
        val user =
            userService.getUserByEmail(principal.username)
                ?: throw UnauthorizedException("You need to be authenticated in order to accept invites.")
        groupMemberService.acceptInviteToGroup(groupToken, user)
        return ResponseEntity.ok(MessageResponseDTO("User successfully joined the group."))
    }
}
