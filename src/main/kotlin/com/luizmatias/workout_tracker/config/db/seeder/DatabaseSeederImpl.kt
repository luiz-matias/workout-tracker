package com.luizmatias.workout_tracker.config.db.seeder

import com.luizmatias.workout_tracker.model.group.Group
import com.luizmatias.workout_tracker.model.group.GroupMeasurementStrategy
import com.luizmatias.workout_tracker.model.group_members.GroupMember
import com.luizmatias.workout_tracker.model.user.AccountRole
import com.luizmatias.workout_tracker.model.user.User
import com.luizmatias.workout_tracker.model.workout_log_group_post.WorkoutLogGroupPost
import com.luizmatias.workout_tracker.model.workout_log_post.WorkoutLogPost
import com.luizmatias.workout_tracker.repository.GroupMemberRepository
import com.luizmatias.workout_tracker.repository.GroupRepository
import com.luizmatias.workout_tracker.repository.RefreshTokenRepository
import com.luizmatias.workout_tracker.repository.TemporaryTokenRepository
import com.luizmatias.workout_tracker.repository.UserRepository
import com.luizmatias.workout_tracker.repository.WorkoutLogGroupPostRepository
import com.luizmatias.workout_tracker.repository.WorkoutLogPostRepository
import jakarta.transaction.Transactional
import java.time.Instant
import java.util.UUID
import net.datafaker.Faker
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Pageable
import org.springframework.scheduling.annotation.Async
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Component

@Component
@Transactional
class DatabaseSeederImpl @Autowired constructor(
    private val userRepository: UserRepository,
    private val refreshTokenRepository: RefreshTokenRepository,
    private val groupRepository: GroupRepository,
    private val groupMemberRepository: GroupMemberRepository,
    private val workoutLogPostRepository: WorkoutLogPostRepository,
    private val workoutLogGroupPostRepository: WorkoutLogGroupPostRepository,
    private val temporaryTokenRepository: TemporaryTokenRepository,
    private val passwordEncoder: PasswordEncoder,
) : DatabaseSeeder {
    private val faker = Faker()
    private val logger = LoggerFactory.getLogger(DatabaseSeederImpl::class.java)

    @Transactional
    override fun seed() {
        deletePreviousData()
        addUsers()
        addGroups()
        addGroupMembers()
        addWorkoutLogPosts()
        addWorkoutLogGroupPosts()
    }

    @Async
    private fun deletePreviousData() {
        logger.info("Deleting previous data...")
        workoutLogGroupPostRepository.deleteAllWorkoutLogGroupPosts()
        groupMemberRepository.deleteAllGroupMembers()
        groupRepository.deleteAllGroups()
        workoutLogPostRepository.deleteAllWorkoutLogPosts()
        refreshTokenRepository.deleteAllRefreshTokens()
        temporaryTokenRepository.deleteAllTemporaryTokens()
        userRepository.deleteAllUsers()
        logger.info("Data deleted!")
    }

    @Async
    fun addUsers(numberOfUsers: Int = 100) {
        logger.info("Adding $numberOfUsers users...")
        val password = passwordEncoder.encode("123")
        userRepository.save(
            User(
                id = null,
                firstName = "Luiz",
                lastName = "Matias",
                email = "luizmatias@luizmatias.com",
                isEmailVerified = true,
                profilePictureUrl = "https://api.dicebear.com/9.x/notionists/png?seed=Luiz%20Matias",
                password = password,
                instagramUsername = "souluizmatias",
                twitterUsername = "luizmatiasdev",
                role = AccountRole.ADMIN,
                groups = emptyList(),
                createdGroups = emptyList(),
                workoutLogPosts = emptyList(),
                temporaryTokens = emptyList(),
                isEnabled = true,
                createdAt = Instant.now(),
            ),
        )
        val users = mutableListOf<User>()
        val userPassword = passwordEncoder.encode(faker.internet().password(8, 25, true, true, true))
        repeat(numberOfUsers - 1) {
            val firstName = faker.name().firstName()
            val lastName = faker.name().lastName()
            users.add(
                User(
                    id = null,
                    firstName = firstName,
                    lastName = lastName,
                    email = "${UUID.randomUUID()}@${faker.internet().domainName()}",
                    isEmailVerified = faker.bool().bool(),
                    profilePictureUrl = "https://api.dicebear.com/9.x/notionists/png?seed=$firstName%20$lastName",
                    password = userPassword,
                    instagramUsername = faker.internet().username(),
                    twitterUsername = faker.internet().username(),
                    role = AccountRole.USER,
                    groups = emptyList(),
                    createdGroups = emptyList(),
                    workoutLogPosts = emptyList(),
                    temporaryTokens = emptyList(),
                    isEnabled = true,
                    createdAt = Instant.now(),
                ),
            )
        }
        userRepository.saveAll(users)
        logger.info("$numberOfUsers users added!")
    }

    @Async
    private fun addGroups(numberOfGroups: Int = 30) {
        logger.info("Adding $numberOfGroups groups...")
        val groups = mutableListOf<Group>()
        val users = userRepository.findAll()
        repeat(numberOfGroups) {
            groups.add(
                Group(
                    id = null,
                    name = faker.lorem().maxLengthSentence(20),
                    description = faker.lorem().maxLengthSentence(100),
                    bannerUrl = "https://api.dicebear.com/9.x/notionists/png?seed=${
                        faker.lorem().maxLengthSentence(5)
                    }",
                    measurementStrategy = GroupMeasurementStrategy.NUMBER_OF_ACTIVITIES,
                    members = emptyList(),
                    createdBy = users.random(),
                    createdAt = Instant.now(),
                ),
            )
        }
        groupRepository.saveAll(groups)
        logger.info("$numberOfGroups groups added!")
    }

    @Async
    private fun addGroupMembers(numberOfUsersPerGroup: Int = 5) {
        logger.info("Adding $numberOfUsersPerGroup users for each group created...")
        val groupMembers = mutableListOf<GroupMember>()
        groupRepository.findAll().forEach { group ->
            val users = userRepository.findAll()
            repeat(numberOfUsersPerGroup) {
                val user = users.random()
                groupMembers.add(
                    GroupMember(
                        id = null,
                        user = user,
                        group = group,
                        workoutLogGroupPosts = emptyList(),
                        joinedAt = Instant.now(),
                        exitedAt = null,
                    ),
                )
                users.remove(user)
            }
        }
        groupMemberRepository.saveAll(groupMembers)
        logger.info(
            "${groupMembers.count()} group joins were created to add $numberOfUsersPerGroup users into all groups!",
        )
    }

    @Async
    private fun addWorkoutLogPosts(numberOfPostsPerUser: Int = 5) {
        logger.info("Adding $numberOfPostsPerUser workout log posts for each user created...")
        val workoutLogPosts = mutableListOf<WorkoutLogPost>()
        userRepository.findAll().forEach { user ->
            repeat(numberOfPostsPerUser) {
                workoutLogPosts.add(
                    WorkoutLogPost(
                        id = null,
                        user = user,
                        name = faker.lorem().maxLengthSentence(20),
                        photoUrl = "https://api.dicebear.com/9.x/notionists/png?seed=${faker.lorem().sentence(5)}",
                        description = faker.lorem().maxLengthSentence(100),
                        workoutLogGroupPosts = emptyList(),
                        createdAt = Instant.now(),
                    ),
                )
            }
        }
        workoutLogPostRepository.saveAll(workoutLogPosts)
        logger.info("$numberOfPostsPerUser workout posts added to each user!")
    }

    @Async
    private fun addWorkoutLogGroupPosts() {
        logger.info("Sharing all workout logs for each user into all groups they are a participant...")
        val workoutLogGroupPosts = mutableListOf<WorkoutLogGroupPost>()
        userRepository.findAll().forEach { user ->
            groupMemberRepository.findAllByUser(user, Pageable.unpaged()).forEach { groupMember ->
                workoutLogPostRepository.findAllByUser(user, Pageable.unpaged()).forEach { workoutLogPost ->
                    workoutLogGroupPosts.add(
                        WorkoutLogGroupPost(
                            id = null,
                            workoutLogPost = workoutLogPost,
                            groupMember = groupMember,
                            createdAt = Instant.now(),
                        ),
                    )
                }
            }
        }
        workoutLogGroupPostRepository.saveAll(workoutLogGroupPosts)
        logger.info("${workoutLogGroupPosts.count()} workout log posts were shared from each user to fill all groups!")
    }
}
