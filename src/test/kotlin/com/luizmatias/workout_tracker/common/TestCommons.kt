package com.luizmatias.workout_tracker.common

import com.luizmatias.workout_tracker.config.exception.advice.ExceptionHandlerAdvice
import com.luizmatias.workout_tracker.features.user.model.AccountRole
import com.luizmatias.workout_tracker.features.user.model.User
import com.luizmatias.workout_tracker.features.user.model.UserPrincipal
import java.time.Instant
import org.mockito.Mockito
import org.springframework.core.MethodParameter
import org.springframework.test.web.servlet.ResultActions
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import org.springframework.web.bind.support.WebDataBinderFactory
import org.springframework.web.context.request.NativeWebRequest
import org.springframework.web.method.support.HandlerMethodArgumentResolver
import org.springframework.web.method.support.ModelAndViewContainer

/**
 * Wraps Mockito any to allow null values as well.
 */
fun <T> any(type: Class<T>): T = Mockito.any(type)

/**
 * Asserts that the response body contains the error fields.
 * @param extras Additional fields to be checked under the `extras` json child object
 * (useful when validation errors occurs).
 */
fun ResultActions.andExpectErrorBody(vararg extras: String = arrayOf()) {
    this.andExpect(jsonPath("timestamp").exists())
    this.andExpect(jsonPath("status").exists())
    this.andExpect(jsonPath("error").exists())
    this.andExpect(jsonPath("path").exists())
    extras.forEach { this.andExpect(jsonPath("extras.$it").exists()) }
}

fun setupAuthenticatedMockMvc(vararg controllers: Any) =
    MockMvcBuilders
        .standaloneSetup(*controllers)
        .setControllerAdvice(ExceptionHandlerAdvice())
        .setCustomArgumentResolvers(authenticationPrincipalProvider)
        .build()

private val authenticationUser =
    User(
        id = 1,
        firstName = "John",
        lastName = "Doe",
        email = "john.doe@email.com",
        isEmailVerified = true,
        profilePictureUrl = "https://api.dicebear.com/9.x/notionists/svg?seed=John%20Doe",
        password = "123",
        instagramUsername = "john.doe",
        twitterUsername = "john.doe",
        role = AccountRole.USER,
        groups = emptyList(),
        createdGroups = emptyList(),
        workoutLogPosts = emptyList(),
        temporaryTokens = emptyList(),
        isEnabled = true,
        createdAt = Instant.now(),
    )

private val authenticationPrincipalProvider =
    object : HandlerMethodArgumentResolver {
        override fun supportsParameter(parameter: MethodParameter): Boolean =
            parameter.parameterType.isAssignableFrom(UserPrincipal::class.java)

        override fun resolveArgument(
            parameter: MethodParameter,
            mavContainer: ModelAndViewContainer?,
            webRequest: NativeWebRequest,
            binderFactory: WebDataBinderFactory?,
        ): Any? = UserPrincipal(authenticationUser)
    }
