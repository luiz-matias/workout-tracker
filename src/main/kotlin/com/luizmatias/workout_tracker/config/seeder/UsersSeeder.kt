package com.luizmatias.workout_tracker.config.seeder

import com.luizmatias.workout_tracker.model.AccountRole
import com.luizmatias.workout_tracker.model.User
import com.luizmatias.workout_tracker.repository.UserRepository
import net.datafaker.Faker
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Component

@Component
class UsersSeeder @Autowired constructor(
    private val userRepository: UserRepository,
    private val passwordEncoder: PasswordEncoder
) {

    private val faker = Faker()
    private val logger = LoggerFactory.getLogger(UsersSeeder::class.java)

    fun addUsers() {
        logger.info("Deleting existing users...")
        userRepository.deleteAllUsers()
        logger.info("Users deleted!")

        logger.info("Adding users...")
        userRepository.save(
            User(
                id = null,
                email = "luizmatias@luizmatias.com",
                password = passwordEncoder.encode("123"),
                role = AccountRole.USER,
                isEmailVerified = true,
                isCredentialsExpired = false,
                isAccountExpired = false,
                isLocked = false,
                isEnabled = true,
            )
        )
        val users = mutableListOf<User>()
        repeat(9) {
            users.add(
                User(
                    id = null,
                    email = faker.internet().emailAddress(),
                    password = passwordEncoder.encode(faker.internet().password(8, 25)),
                    role = AccountRole.USER,
                    isEmailVerified = true,
                    isCredentialsExpired = false,
                    isAccountExpired = false,
                    isLocked = false,
                    isEnabled = true,
                )
            )
        }
        userRepository.saveAll(users)
        logger.info("Users added!")
    }

}