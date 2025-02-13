package com.luizmatias.workout_tracker.service.user

import com.luizmatias.workout_tracker.config.api.exception.common_exceptions.BusinessRuleConflictException
import com.luizmatias.workout_tracker.config.api.exception.common_exceptions.NotFoundException
import com.luizmatias.workout_tracker.model.temporary_token.TemporaryToken
import com.luizmatias.workout_tracker.model.temporary_token.TemporaryToken.Companion.isExpired
import com.luizmatias.workout_tracker.model.temporary_token.TemporaryToken.Companion.isValidType
import com.luizmatias.workout_tracker.model.temporary_token.TokenType
import com.luizmatias.workout_tracker.model.user.User
import com.luizmatias.workout_tracker.repository.UserRepository
import com.luizmatias.workout_tracker.service.notification.NotificationSenderService
import com.luizmatias.workout_tracker.service.temporary_token.TemporaryTokenService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import java.time.Instant
import java.time.temporal.ChronoUnit
import java.util.*

@Service
class UserServiceImpl @Autowired constructor(
    private val userRepository: UserRepository,
    private val notificationSenderService: NotificationSenderService,
    private val temporaryTokenService: TemporaryTokenService,
    private val passwordEncoder: PasswordEncoder
) : UserService {

    override fun getUserByEmail(email: String): User? {
        return userRepository.findByEmail(email)
    }

    override fun registerUser(user: User): User {
        val userEncrypted = user.copy(password = passwordEncoder.encode(user.password))
        if (userRepository.findByEmail(user.email) != null) {
            throw BusinessRuleConflictException("email already in use by another user.")
        }
        val savedUser = userRepository.save(userEncrypted)

        val verifyEmailToken = temporaryTokenService.createTemporaryToken(
            TemporaryToken(
                id = null,
                createdBy = savedUser,
                token = UUID.randomUUID().toString(),
                type = TokenType.VERIFY_EMAIL,
                expiresAt = Instant.now().plus(1, ChronoUnit.HOURS)
            )
        )

        notificationSenderService.send(
            user.email,
            "Welcome to Workout Tracker",
            "Welcome to Workout Tracker, ${user.firstName}! Please verify your email by using the following token: ${verifyEmailToken.token}"
        )
        return savedUser
    }

    override fun updateUser(id: Long, user: User): User? {
        if (userRepository.existsById(id)) {
            return userRepository.save(user.copy(id = id))
        }
        return null
    }

    override fun changePassword(id: Long, existingPassword: String, newPassword: String): User {
        val user = userRepository.findById(id).orElse(null) ?: throw NotFoundException("User not found.")

        if (!passwordEncoder.matches(existingPassword, user.password)) {
            throw BusinessRuleConflictException("Invalid existing password.")
        }

        val savedUser = userRepository.save(user.copy(password = passwordEncoder.encode(newPassword)))
        notificationSenderService.send(
            user.email,
            "Password changed",
            "Your password has been changed successfully."
        )
        return savedUser
    }

    override fun resetPassword(token: String, newPassword: String): User {
        val temporaryToken =
            temporaryTokenService.getTemporaryTokenByToken(token) ?: throw NotFoundException("Token not found.")

        if (temporaryToken.isExpired()) {
            temporaryTokenService.deleteTemporaryToken(temporaryToken)
            throw BusinessRuleConflictException("Token expired.")
        }

        if (!temporaryToken.isValidType(TokenType.RESET_PASSWORD)) {
            throw BusinessRuleConflictException("Invalid token.")
        }

        val user = temporaryToken.createdBy

        val savedUser = userRepository.save(user.copy(password = passwordEncoder.encode(newPassword)))
        notificationSenderService.send(
            user.email,
            "Password changed",
            "Your password has been changed successfully. If that was not you, please contact our support immediately."
        )
        return savedUser
    }

    override fun verifyEmail(token: String): User {
        val temporaryToken =
            temporaryTokenService.getTemporaryTokenByToken(token) ?: throw NotFoundException("Token not found.")

        if (temporaryToken.isExpired()) {
            temporaryTokenService.deleteTemporaryToken(temporaryToken)
            throw BusinessRuleConflictException("Token expired.")
        }

        if (!temporaryToken.isValidType(TokenType.VERIFY_EMAIL)) {
            throw BusinessRuleConflictException("Invalid token.")
        }

        val user = temporaryToken.createdBy

        val savedUser = userRepository.save(user.copy(isEmailVerified = true))
        temporaryTokenService.deleteTemporaryToken(temporaryToken)
        return savedUser
    }

    override fun deleteUser(id: Long): Boolean {
        if (userRepository.existsById(id)) {
            userRepository.deleteById(id)
            return true
        }
        return false
    }

}