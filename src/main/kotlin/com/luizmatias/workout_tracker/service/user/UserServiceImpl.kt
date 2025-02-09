package com.luizmatias.workout_tracker.service.user

import com.luizmatias.workout_tracker.config.exception.common_exceptions.BusinessRuleConflictException
import com.luizmatias.workout_tracker.config.exception.common_exceptions.NotFoundException
import com.luizmatias.workout_tracker.model.user.User
import com.luizmatias.workout_tracker.repository.UserRepository
import com.luizmatias.workout_tracker.service.email.NotificationSenderService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service

@Service
class UserServiceImpl @Autowired constructor(
    private val userRepository: UserRepository,
    private val notificationSenderService: NotificationSenderService,
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
        notificationSenderService.send(
            user.email,
            "Welcome to Workout Tracker",
            "Welcome to Workout Tracker, ${user.firstName}!"
        )
        return savedUser
    }

    override fun updateUser(id: Long, user: User): User? {
        if (userRepository.existsById(id)) {
            return userRepository.save(user)
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

    override fun deleteUser(id: Long): Boolean {
        if (userRepository.existsById(id)) {
            userRepository.deleteById(id)
            return true
        }
        return false
    }

}