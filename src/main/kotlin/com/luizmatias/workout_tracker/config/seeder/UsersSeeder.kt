package com.luizmatias.workout_tracker.config.seeder

import com.luizmatias.workout_tracker.model.user.AccountRole
import com.luizmatias.workout_tracker.model.user.User
import com.luizmatias.workout_tracker.repository.UserRepository
import net.datafaker.Faker
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Component
import java.util.*

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
                name = "Luiz Matias",
                email = "luizmatias@luizmatias.com",
                isEmailVerified = true,
                profilePictureUrl = "https://api.dicebear.com/9.x/notionists/svg?seed=Luiz%20Matias",
                password = passwordEncoder.encode("123"),
                instagramUsername = "souluizmatias",
                twitterUsername = "luizmatiasdev",
                role = AccountRole.ADMIN,
                isEnabled = true,
                createdAt = Date()
            )
        )
        val users = mutableListOf<User>()
        repeat(9) {
            val name = faker.name().fullName()
            users.add(
                User(
                    id = null,
                    name = "Luiz Matias",
                    email = faker.internet().emailAddress(),
                    isEmailVerified = faker.bool().bool(),
                    profilePictureUrl = "https://api.dicebear.com/9.x/notionists/svg?seed=${name.replace(" ", "%20")}",
                    password = passwordEncoder.encode(faker.internet().password(8, 25, true, true, true)),
                    instagramUsername = faker.internet().username(),
                    twitterUsername = faker.internet().username(),
                    role = AccountRole.USER,
                    isEnabled = true,
                    createdAt = Date()
                )
            )
        }
        userRepository.saveAll(users)
        logger.info("Users added!")
    }

}