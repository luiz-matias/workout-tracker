package com.luizmatias.workout_tracker.model.user

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.luizmatias.workout_tracker.model.group.Group
import com.luizmatias.workout_tracker.model.group_members.GroupMember
import com.luizmatias.workout_tracker.model.temporary_token.TemporaryToken
import com.luizmatias.workout_tracker.model.workout_log_post.WorkoutLogPost
import jakarta.persistence.CascadeType
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.FetchType
import jakarta.persistence.GeneratedValue
import jakarta.persistence.Id
import jakarta.persistence.OneToMany
import jakarta.persistence.Table
import java.time.Instant
import org.springframework.data.annotation.CreatedDate

@Entity
@Table(name = "users")
data class User(
    @Id
    @GeneratedValue
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
    @Enumerated(EnumType.STRING)
    val role: AccountRole,
    @field:JsonIgnoreProperties("user")
    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY, cascade = [CascadeType.PERSIST, CascadeType.MERGE])
    val groups: List<GroupMember>,
    @field:JsonIgnoreProperties("createdBy")
    @OneToMany(mappedBy = "createdBy", fetch = FetchType.LAZY, cascade = [CascadeType.ALL])
    val createdGroups: List<Group>,
    @field:JsonIgnoreProperties("user")
    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY, cascade = [CascadeType.ALL])
    val workoutLogPosts: List<WorkoutLogPost>,
    @field:JsonIgnoreProperties("createdBy")
    @OneToMany(mappedBy = "createdBy", fetch = FetchType.LAZY, cascade = [CascadeType.ALL])
    val temporaryTokens: List<TemporaryToken>,
    @Column(nullable = false)
    val isEnabled: Boolean,
    @CreatedDate
    val createdAt: Instant,
    val deletedAt: Instant? = null,
)
