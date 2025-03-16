package com.luizmatias.workout_tracker.controller

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.PropertyNamingStrategies
import com.luizmatias.workout_tracker.common.andExpectErrorBody
import com.luizmatias.workout_tracker.config.exception.common_exceptions.BusinessRuleConflictException
import com.luizmatias.workout_tracker.config.exception.common_exceptions.UnauthorizedException
import com.luizmatias.workout_tracker.features.auth.dto.ForgotPasswordRequestDTO
import com.luizmatias.workout_tracker.features.auth_token.dto.RefreshTokenRequestDTO
import com.luizmatias.workout_tracker.features.auth_token.dto.TokenDTO
import com.luizmatias.workout_tracker.features.auth.dto.AuthCredentialsDTO
import com.luizmatias.workout_tracker.features.auth.dto.AuthRegisterDTO
import com.luizmatias.workout_tracker.features.auth.dto.AuthResponseDTO
import com.luizmatias.workout_tracker.features.user.dto.UserResponseDTO
import com.luizmatias.workout_tracker.features.auth.service.AuthService
import com.luizmatias.workout_tracker.features.auth_token.service.RefreshTokenService
import kotlin.test.BeforeTest
import kotlin.test.Test
import org.mockito.Mockito
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.bean.override.mockito.MockitoBean
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class AuthControllerTests {
    @MockitoBean
    private lateinit var authService: AuthService

    @MockitoBean
    private lateinit var refreshTokenService: RefreshTokenService

    @Autowired
    private lateinit var mockMvc: MockMvc

    private val objectMapper = ObjectMapper()

    @BeforeTest
    fun setup() {
        objectMapper.propertyNamingStrategy = PropertyNamingStrategies.SNAKE_CASE
    }

    @Test
    fun `should return 200 when registering user`() {
        val request =
            AuthRegisterDTO(
                "John",
                "Doe",
                "john.doe@email.com",
                "https://image.example.com/image.png",
                "Secret1212!",
                "john.doe",
                "john.doe",
            )
        val response =
            AuthResponseDTO(
                UserResponseDTO(
                    1,
                    "John",
                    "Doe",
                    "john.doe@email.com",
                    "https://image.example.com/image.png",
                    "john.doe",
                    "john.doe",
                ),
                TokenDTO(
                    "access_token",
                    "refresh_token",
                ),
            )

        Mockito.`when`(authService.register(request)).thenReturn(response)

        mockMvc
            .perform(
                post("/auth/register")
                    .contentType(MediaType.APPLICATION_JSON)
                    .accept(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(request)),
            )
            .andExpect(status().isOk)
            .andExpect(jsonPath("$.user.id").value(1))
            .andExpect(jsonPath("$.user.first_name").value("John"))
            .andExpect(jsonPath("$.user.last_name").value("Doe"))
            .andExpect(jsonPath("$.user.email").value("john.doe@email.com"))
            .andExpect(jsonPath("$.user.profile_picture_url").value("https://image.example.com/image.png"))
            .andExpect(jsonPath("$.user.instagram_username").value("john.doe"))
            .andExpect(jsonPath("$.user.twitter_username").value("john.doe"))
    }

    @Test
    fun `should return 400 when registering user with invalid data`() {
        val request =
            AuthRegisterDTO(
                "A",
                "B",
                "invalid-email.com",
                "X",
                "12345678",
                "X",
                "X",
            )

        mockMvc
            .perform(
                post("/auth/register")
                    .contentType(MediaType.APPLICATION_JSON)
                    .accept(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(request)),
            )
            .andExpect(status().isBadRequest)
            .andExpectErrorBody(
                "firstName",
                "lastName",
                "email",
                "profilePictureUrl",
                "password",
                "instagramUsername",
                "twitterUsername",
            )
    }

    @Test
    fun `should return 409 when registering user with already existing email`() {
        val request =
            AuthRegisterDTO(
                "John",
                "Doe",
                "john.doe@email.com",
                "https://image.example.com/image.png",
                "Secret1212!",
                "john.doe",
                "john.doe",
            )

        Mockito.`when`(authService.register(request)).thenAnswer { throw BusinessRuleConflictException() }

        mockMvc
            .perform(
                post("/auth/register")
                    .contentType(MediaType.APPLICATION_JSON)
                    .accept(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(request)),
            )
            .andExpect(status().isConflict)
            .andExpectErrorBody()
    }

    @Test
    fun `should return 200 when making login`() {
        val authResponse =
            AuthResponseDTO(
                UserResponseDTO(
                    1,
                    "John",
                    "Doe",
                    "john@email.com",
                    "https://image.example.com/image.png",
                    "john.doe",
                    "john.doe",
                ),
                TokenDTO(
                    "access_token",
                    "refresh_token",
                ),
            )

        Mockito
            .`when`(this.authService.login(AuthCredentialsDTO("john@email.com", "Secret1212!")))
            .thenReturn(authResponse)

        mockMvc
            .perform(
                post("/auth/login")
                    .contentType(MediaType.APPLICATION_JSON)
                    .accept(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(AuthCredentialsDTO("john@email.com", "Secret1212!"))),
            )
            .andExpect(status().isOk)
            .andExpect(jsonPath("$.user.id").value(1))
            .andExpect(jsonPath("$.user.first_name").value("John"))
            .andExpect(jsonPath("$.user.last_name").value("Doe"))
            .andExpect(jsonPath("$.user.email").value("john@email.com"))
            .andExpect(jsonPath("$.user.profile_picture_url").value("https://image.example.com/image.png"))
            .andExpect(jsonPath("$.user.instagram_username").value("john.doe"))
            .andExpect(jsonPath("$.user.twitter_username").value("john.doe"))
    }

    @Test
    fun `should return 401 when making login with invalid credentials`() {
        Mockito
            .`when`(this.authService.login(AuthCredentialsDTO("john@email.com", "Secret1212!")))
            .thenAnswer { throw UnauthorizedException("Invalid credentials") }

        mockMvc
            .perform(
                post("/auth/login")
                    .contentType(MediaType.APPLICATION_JSON)
                    .accept(MediaType.APPLICATION_JSON)
                    .content(
                        objectMapper
                            .writeValueAsString(
                                AuthCredentialsDTO(
                                    "john@email.com",
                                    "Secret1212!",
                                ),
                            ),
                    ),
            )
            .andExpect(status().isUnauthorized)
            .andExpectErrorBody()
    }

    @Test
    fun `should return 400 when making login with invalid email`() {
        Mockito
            .`when`(this.authService.login(AuthCredentialsDTO("johnemail.com", "Secret1212!")))
            .thenAnswer { throw UnauthorizedException("Invalid credentials") }

        mockMvc
            .perform(
                post("/auth/login")
                    .contentType(MediaType.APPLICATION_JSON)
                    .accept(MediaType.APPLICATION_JSON)
                    .content(
                        objectMapper
                            .writeValueAsString(
                                AuthCredentialsDTO(
                                    "johnemail.com",
                                    "Secret1212!",
                                ),
                            ),
                    ),
            )
            .andExpect(status().isBadRequest)
            .andExpectErrorBody("email")
    }

    @Test
    fun `should return 400 when making login with empty email`() {
        Mockito
            .`when`(this.authService.login(AuthCredentialsDTO("johnemail.com", "Secret1212!")))
            .thenAnswer { throw UnauthorizedException("Invalid credentials") }

        mockMvc
            .perform(
                post("/auth/login")
                    .contentType(MediaType.APPLICATION_JSON)
                    .accept(MediaType.APPLICATION_JSON)
                    .content(
                        objectMapper
                            .writeValueAsString(
                                AuthCredentialsDTO(
                                    "",
                                    "Secret1212!",
                                ),
                            ),
                    ),
            )
            .andExpect(status().isBadRequest)
            .andExpectErrorBody("email")
    }

    @Test
    fun `should return 400 when making login with empty password`() {
        Mockito
            .`when`(this.authService.login(AuthCredentialsDTO("johnemail.com", "Secret1212!")))
            .thenAnswer { throw UnauthorizedException("Invalid credentials") }

        mockMvc
            .perform(
                post("/auth/login")
                    .contentType(MediaType.APPLICATION_JSON)
                    .accept(MediaType.APPLICATION_JSON)
                    .content(
                        objectMapper
                            .writeValueAsString(
                                AuthCredentialsDTO(
                                    "john@email.com",
                                    "",
                                ),
                            ),
                    ),
            )
            .andExpect(status().isBadRequest)
            .andExpectErrorBody("password")
    }

    @Test
    fun `should return 200 when calling forgot password`() {
        val request = ForgotPasswordRequestDTO("john@email.com")

        mockMvc
            .perform(
                post("/auth/forgot-password")
                    .contentType(MediaType.APPLICATION_JSON)
                    .accept(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(request)),
            )
            .andExpect(status().isOk)
            .andExpect(jsonPath("message").exists())
    }

    @Test
    fun `should return 400 when calling forgot password with invalid email`() {
        val request = ForgotPasswordRequestDTO("johnemail.com")

        mockMvc
            .perform(
                post("/auth/forgot-password")
                    .contentType(MediaType.APPLICATION_JSON)
                    .accept(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(request)),
            )
            .andExpect(status().isBadRequest)
            .andExpectErrorBody("email")
    }

    @Test
    fun `should return 400 when calling forgot password with empty email`() {
        val request = ForgotPasswordRequestDTO("")

        mockMvc
            .perform(
                post("/auth/forgot-password")
                    .contentType(MediaType.APPLICATION_JSON)
                    .accept(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(request)),
            )
            .andExpect(status().isBadRequest)
            .andExpectErrorBody("email")
    }

    @Test
    fun `should return 200 when calling refresh token`() {
        val request = RefreshTokenRequestDTO("refresh_token")
        val response = TokenDTO("access_token", "refresh_token")

        Mockito.`when`(refreshTokenService.regenerateTokens(Mockito.anyString())).thenReturn(response)

        mockMvc
            .perform(
                post("/auth/refresh-token")
                    .contentType(MediaType.APPLICATION_JSON)
                    .accept(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(request)),
            )
            .andExpect(status().isOk)
            .andExpect(jsonPath("access_token").value("access_token"))
            .andExpect(jsonPath("refresh_token").value("refresh_token"))
    }

    @Test
    fun `should return 401 when calling refresh token and it is not found`() {
        val request = RefreshTokenRequestDTO("refresh_token")

        Mockito.`when`(refreshTokenService.regenerateTokens(Mockito.anyString())).thenAnswer {
            throw UnauthorizedException()
        }

        mockMvc
            .perform(
                post("/auth/refresh-token")
                    .contentType(MediaType.APPLICATION_JSON)
                    .accept(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(request)),
            )
            .andExpect(status().isUnauthorized)
            .andExpectErrorBody()
    }

    @Test
    fun `should return 400 when calling refresh token and it is empty`() {
        val request = RefreshTokenRequestDTO("")

        mockMvc
            .perform(
                post("/auth/refresh-token")
                    .contentType(MediaType.APPLICATION_JSON)
                    .accept(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(request)),
            )
            .andExpect(status().isBadRequest)
            .andExpectErrorBody("refreshToken")
    }
}
