package com.luizmatias.workout_tracker.features.temporary_token.controller

import com.luizmatias.workout_tracker.features.common.dto.MessageResponseDTO
import com.luizmatias.workout_tracker.features.temporary_token.dto.PasswordResetRequestDTO
import com.luizmatias.workout_tracker.features.user.model.UserPrincipal
import com.luizmatias.workout_tracker.features.group_member.service.GroupMemberService
import com.luizmatias.workout_tracker.features.user.service.UserService
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
        groupMemberService.acceptInviteToGroup(groupToken, principal.user)
        return ResponseEntity.ok(MessageResponseDTO("User successfully joined the group."))
    }
}
