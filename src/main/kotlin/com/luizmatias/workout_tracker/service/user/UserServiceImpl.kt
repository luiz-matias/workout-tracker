package com.luizmatias.workout_tracker.service.user

import com.luizmatias.workout_tracker.model.User
import com.luizmatias.workout_tracker.repository.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service

@Service
class UserServiceImpl @Autowired constructor(
    private val userRepository: UserRepository,
    private val passwordEncoder: PasswordEncoder
) : UserService {

    override fun getUserByEmail(email: String): User? {
        return userRepository.findByEmail(email)
    }

    override fun registerUser(user: User): User {
        val userEncrypted = user.copy(password = passwordEncoder.encode(user.password))
        return userRepository.save(userEncrypted)
    }

    override fun updateUser(id: Long, user: User): User? {
        if (userRepository.existsById(id)) {
            return userRepository.save(user)
        }
        return null
    }

    override fun deleteUser(id: Long): Boolean {
        if (userRepository.existsById(id)) {
            userRepository.deleteById(id)
            return true
        }
        return false
    }

}