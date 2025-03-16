package com.luizmatias.workout_tracker.features.group_member.model

import com.fasterxml.jackson.annotation.JsonIgnore
import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.luizmatias.workout_tracker.features.group.model.Group
import com.luizmatias.workout_tracker.features.user.model.User
import com.luizmatias.workout_tracker.features.workout_log_group_post.model.WorkoutLogGroupPost
import jakarta.persistence.CascadeType
import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.GeneratedValue
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import jakarta.persistence.OneToMany
import jakarta.persistence.Table
import java.time.Instant
import org.springframework.data.annotation.CreatedDate

@Entity
@Table(name = "group_members")
data class GroupMember(
    @Id
    @GeneratedValue
    val id: Long?,
    @field:JsonIgnoreProperties("groups", "createdGroups", "workoutLogPosts", "temporaryTokens")
    @ManyToOne
    @JoinColumn(name = "user_id")
    val user: User,
    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "group_id")
    val group: Group,
    @field:JsonIgnoreProperties("groupMember")
    @OneToMany(mappedBy = "groupMember", fetch = FetchType.LAZY, cascade = [CascadeType.PERSIST, CascadeType.MERGE])
    val workoutLogGroupPosts: List<WorkoutLogGroupPost>,
    @CreatedDate
    val joinedAt: Instant,
    val exitedAt: Instant? = null,
)
