package com.luizmatias.workout_tracker.controller

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.PropertyNamingStrategies
import com.luizmatias.workout_tracker.features.temporary_token.controller.TemporaryTokenController
import com.luizmatias.workout_tracker.common.andExpectErrorBody
import com.luizmatias.workout_tracker.common.any
import com.luizmatias.workout_tracker.common.setupAuthenticatedMockMvc
import com.luizmatias.workout_tracker.config.exception.common_exceptions.BusinessRuleConflictException
import com.luizmatias.workout_tracker.config.exception.common_exceptions.NotFoundException
import com.luizmatias.workout_tracker.features.common.dto.MessageResponseDTO
import com.luizmatias.workout_tracker.features.temporary_token.dto.PasswordResetRequestDTO
import com.luizmatias.workout_tracker.features.group_member.model.GroupMember
import com.luizmatias.workout_tracker.features.user.model.User
import com.luizmatias.workout_tracker.features.group_member.service.GroupMemberService
import com.luizmatias.workout_tracker.features.user.service.UserService
import kotlin.test.BeforeTest
import kotlin.test.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.bean.override.mockito.MockitoBean
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class TemporaryTokenControllerTests {
    @MockitoBean
    private lateinit var userService: UserService

    @MockitoBean
    private lateinit var groupMemberService: GroupMemberService

    @Mock
    private lateinit var user: User

    @Mock
    private lateinit var groupMember: GroupMember

    private lateinit var mockMvc: MockMvc

    private val objectMapper = ObjectMapper()

    @BeforeTest
    fun setup() {
        objectMapper.propertyNamingStrategy = PropertyNamingStrategies.SNAKE_CASE
        mockMvc = setupAuthenticatedMockMvc(TemporaryTokenController(userService, groupMemberService))
    }

    @Test
    fun `should return 200 when verifying email successfully`() {
        val emailToken = "email_token"
        val response = MessageResponseDTO("Email verified successfully.")

        Mockito.`when`(userService.verifyEmail(emailToken)).thenReturn(user)

        mockMvc
            .perform(
                get("/token/verify-email/$emailToken")
                    .contentType(MediaType.APPLICATION_JSON)
                    .accept(MediaType.APPLICATION_JSON),
            )
            .andExpect(status().isOk)
            .andExpect(jsonPath("message").value(response.message))
    }

    @Test
    fun `should return 404 when verifying email with not found token`() {
        val emailToken = "email_token"

        Mockito.`when`(userService.verifyEmail(emailToken)).thenAnswer { throw NotFoundException() }

        mockMvc
            .perform(
                get("/token/verify-email/$emailToken")
                    .contentType(MediaType.APPLICATION_JSON)
                    .accept(MediaType.APPLICATION_JSON),
            )
            .andExpect(status().isNotFound)
            .andExpectErrorBody()
    }

    @Test
    fun `should return 409 when verifying email with expired or invalid token`() {
        val emailToken = "email_token"

        Mockito.`when`(userService.verifyEmail(emailToken)).thenAnswer { throw BusinessRuleConflictException() }

        mockMvc
            .perform(
                get("/token/verify-email/$emailToken")
                    .contentType(MediaType.APPLICATION_JSON)
                    .accept(MediaType.APPLICATION_JSON),
            )
            .andExpect(status().isConflict)
            .andExpectErrorBody()
    }

    @Test
    fun `should return 200 when resetting password with valid data`() {
        val passwordToken = "password_token"
        val request = PasswordResetRequestDTO("Secret1212!")
        val response = MessageResponseDTO("Password has been reset successfully.")

        Mockito.`when`(userService.resetPassword(passwordToken, request.password)).thenReturn(user)

        mockMvc
            .perform(
                post("/token/password-reset/$passwordToken")
                    .contentType(MediaType.APPLICATION_JSON)
                    .accept(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(request)),
            )
            .andExpect(status().isOk)
            .andExpect(jsonPath("message").value(response.message))
    }

    @Test
    fun `should return 404 when resetting password with token not found`() {
        val passwordToken = "password_token"
        val request = PasswordResetRequestDTO("Secret1212!")

        Mockito.`when`(userService.resetPassword(passwordToken, request.password)).thenAnswer {
            throw NotFoundException()
        }

        mockMvc
            .perform(
                post("/token/password-reset/$passwordToken")
                    .contentType(MediaType.APPLICATION_JSON)
                    .accept(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(request)),
            )
            .andExpect(status().isNotFound)
            .andExpectErrorBody()
    }

    @Test
    fun `should return 409 when resetting password with expired or invalid token`() {
        val passwordToken = "password_token"
        val request = PasswordResetRequestDTO("Secret1212!")

        Mockito.`when`(userService.resetPassword(passwordToken, request.password)).thenAnswer {
            throw BusinessRuleConflictException()
        }

        mockMvc
            .perform(
                post("/token/password-reset/$passwordToken")
                    .contentType(MediaType.APPLICATION_JSON)
                    .accept(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(request)),
            )
            .andExpect(status().isConflict)
            .andExpectErrorBody()
    }

    @Test
    fun `should return 200 when accepting group invite with valid token`() {
        val groupToken = "group_token"
        val response = MessageResponseDTO("User successfully joined the group.")

        Mockito.`when`(userService.getUserByEmail("john.doe@email.com")).thenReturn(user)
        Mockito.`when`(groupMemberService.acceptInviteToGroup(groupToken, user)).thenReturn(groupMember)

        mockMvc
            .perform(
                get("/token/join/$groupToken")
                    .contentType(MediaType.APPLICATION_JSON)
                    .accept(MediaType.APPLICATION_JSON),
            )
            .andExpect(status().isOk)
            .andExpect(jsonPath("message").value(response.message))
    }

    @Test
    fun `should return 409 when accepting group invite with expired or invalid token`() {
        val groupToken = "group_token"
        Mockito.`when`(groupMemberService.acceptInviteToGroup(Mockito.anyString(), any(User::class.java))).thenAnswer {
            throw BusinessRuleConflictException()
        }

        mockMvc
            .perform(
                get("/token/join/$groupToken")
                    .contentType(MediaType.APPLICATION_JSON)
                    .accept(MediaType.APPLICATION_JSON),
            )
            .andExpect(status().isConflict)
            .andExpectErrorBody()
    }
}
