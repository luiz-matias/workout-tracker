package com.luizmatias.workout_tracker.features.user.service

import com.luizmatias.workout_tracker.features.user.dto.toPrincipal
import com.luizmatias.workout_tracker.features.user.repository.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.stereotype.Service

@Service
class UserDetailsServiceImpl @Autowired constructor(
    private val userRepository: UserRepository,
) : UserDetailsService {
    override fun loadUserByUsername(username: String?): UserDetails? {
        if (username == null) {
            return null
        }

        val user = userRepository.findByEmail(username) ?: return null

        return user.toPrincipal()
    }
}
