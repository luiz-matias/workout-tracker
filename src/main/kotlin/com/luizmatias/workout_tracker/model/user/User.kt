package com.luizmatias.workout_tracker.model.user

import com.luizmatias.workout_tracker.model.group.Group
import com.luizmatias.workout_tracker.model.group_members.GroupMember
import com.luizmatias.workout_tracker.model.workout_log_post.WorkoutLogPost
import jakarta.persistence.*
import org.springframework.data.annotation.CreatedDate
import java.time.Instant

@Entity
@Table(name = "users")
data class User(
    @Id @GeneratedValue
    val id: Long? = null,
    @Column(nullable = false)
    val firstName: String,
    @Column(nullable = false)
    val lastName: String,
    @Column(nullable = false, unique = true)
    val email: String,
    @Column(nullable = false)
    val isEmailVerified: Boolean,
    val profilePictureUrl: String?,
    @Column(nullable = false)
    val password: String,
    val instagramUsername: String?,
    val twitterUsername: String?,
    @Column(nullable = false)
    @Enumerated(EnumType.STRING) val role: AccountRole,
    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    val groups: List<GroupMember>,
    @OneToMany(mappedBy = "createdBy", fetch = FetchType.LAZY)
    val createdGroups: List<Group>,
    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    val workoutLogPosts: List<WorkoutLogPost>,
    @Column(nullable = false)
    val isEnabled: Boolean,
    @CreatedDate
    val createdAt: Instant,
    val deletedAt: Instant? = null
)