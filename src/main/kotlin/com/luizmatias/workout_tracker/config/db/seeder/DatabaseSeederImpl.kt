package com.luizmatias.workout_tracker.config.db.seeder

import com.luizmatias.workout_tracker.model.group.Group
import com.luizmatias.workout_tracker.model.group.GroupMeasurementStrategy
import com.luizmatias.workout_tracker.model.group_members.GroupMember
import com.luizmatias.workout_tracker.model.user.AccountRole
import com.luizmatias.workout_tracker.model.user.User
import com.luizmatias.workout_tracker.model.workout_log_group_post.WorkoutLogGroupPost
import com.luizmatias.workout_tracker.model.workout_log_post.WorkoutLogPost
import com.luizmatias.workout_tracker.repository.*
import jakarta.transaction.Transactional
import net.datafaker.Faker
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.scheduling.annotation.Async
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Component
import java.time.Instant
import java.util.*

@Component
@Transactional
class DatabaseSeederImpl @Autowired constructor(
    private val userRepository: UserRepository,
    private val refreshTokenRepository: RefreshTokenRepository,
    private val groupRepository: GroupRepository,
    private val groupMemberRepository: GroupMemberRepository,
    private val workoutLogPostRepository: WorkoutLogPostRepository,
    private val workoutLogGroupPostRepository: WorkoutLogGroupPostRepository,
    private val passwordEncoder: PasswordEncoder
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
        userRepository.deleteAllUsers()
        logger.info("Data deleted!")
    }

    @Async
    fun addUsers(numberOfUsers: Int = 1_000) {
        logger.info("Adding users...")
        val password = passwordEncoder.encode("123")
        userRepository.save(
            User(
                id = null,
                firstName = "Luiz",
                lastName = "Matias",
                email = "luizmatias@luizmatias.com",
                isEmailVerified = true,
                profilePictureUrl = "https://api.dicebear.com/9.x/notionists/svg?seed=Luiz%20Matias",
                password = password,
                instagramUsername = "souluizmatias",
                twitterUsername = "luizmatiasdev",
                role = AccountRole.ADMIN,
                groups = emptyList(),
                createdGroups = emptyList(),
                workoutLogPosts = emptyList(),
                isEnabled = true,
                createdAt = Instant.now()
            )
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
                    profilePictureUrl = "https://api.dicebear.com/9.x/notionists/svg?seed=${firstName}%20${lastName}",
                    password = userPassword,
                    instagramUsername = faker.internet().username(),
                    twitterUsername = faker.internet().username(),
                    role = AccountRole.USER,
                    groups = emptyList(),
                    createdGroups = emptyList(),
                    workoutLogPosts = emptyList(),
                    isEnabled = true,
                    createdAt = Instant.now()
                )
            )
        }
        userRepository.saveAll(users)
        logger.info("$numberOfUsers users added!")
    }

    @Async
    private fun addGroups(numberOfGroups: Int = 300) {
        logger.info("Adding groups...")
        val groups = mutableListOf<Group>()
        val users = userRepository.findAll()
        repeat(numberOfGroups) {
            groups.add(
                Group(
                    id = null,
                    name = faker.lorem().maxLengthSentence(20),
                    description = faker.lorem().maxLengthSentence(100),
                    bannerUrl = "https://api.dicebear.com/9.x/notionists/svg?seed=${
                        faker.lorem().maxLengthSentence(5)
                    }",
                    measurementStrategy = GroupMeasurementStrategy.NUMBER_OF_ACTIVITIES,
                    members = emptyList(),
                    createdBy = users.random(),
                    createdAt = Instant.now()
                )
            )
        }
        groupRepository.saveAll(groups)
        logger.info("$numberOfGroups groups added!")
    }

    @Async
    private fun addGroupMembers(numberOfUsersPerGroup: Int = 3) {
        logger.info("Adding users to groups (N..N relationship)...")
        val groupMembers = mutableListOf<GroupMember>()
        val users = userRepository.findAll()
        groupRepository.findAll().forEach { group ->
            repeat(numberOfUsersPerGroup) {
                val user = users.random()
                groupMembers.add(
                    GroupMember(
                        id = null,
                        user = user,
                        group = group,
                        workoutLogGroupPosts = emptyList(),
                        enteredAt = Instant.now(),
                        exitedAt = null
                    )
                )
                users.remove(user)
            }
        }
        groupMemberRepository.saveAll(groupMembers)
        logger.info("${groupMembers.count()} member registrations were created to add $numberOfUsersPerGroup users into all groups!")
    }

    @Async
    private fun addWorkoutLogPosts(numberOfPostsPerUser: Int = 30) {
        logger.info("Adding workout log posts to users...")
        val workoutLogPosts = mutableListOf<WorkoutLogPost>()
        userRepository.findAll().forEach { user ->
            repeat(numberOfPostsPerUser) {
                workoutLogPosts.add(
                    WorkoutLogPost(
                        id = null,
                        user = user,
                        name = faker.lorem().maxLengthSentence(20),
                        photoUrl = "https://api.dicebear.com/9.x/notionists/svg?seed=${faker.lorem().sentence(5)}",
                        description = faker.lorem().maxLengthSentence(100),
                        workoutLogGroupPosts = emptyList(),
                        createdAt = Instant.now()
                    )
                )
            }
        }
        workoutLogPostRepository.saveAll(workoutLogPosts)
        logger.info("$numberOfPostsPerUser workout posts added to each user!")
    }

    @Async
    private fun addWorkoutLogGroupPosts() {
        logger.info("Sharing workout logs from users into groups (N..N relationship)...")
        val workoutLogGroupPosts = mutableListOf<WorkoutLogGroupPost>()
        userRepository.findAll().forEach { user ->
            groupMemberRepository.findAllByUser(user).forEach { groupMember ->
                workoutLogPostRepository.findAllByUser(user).forEach { workoutLogPost ->
                    workoutLogGroupPosts.add(
                        WorkoutLogGroupPost(
                            id = null,
                            workoutLogPost = workoutLogPost,
                            groupMember = groupMember,
                            createdAt = Instant.now(),
                        )
                    )
                }
            }
        }
        workoutLogGroupPostRepository.saveAll(workoutLogGroupPosts)
        logger.info("${workoutLogGroupPosts.count()} workout log posts were shared from all users to fill all groups!")
    }

}