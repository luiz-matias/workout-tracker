package com.luizmatias.workout_tracker.model.group_members

import com.fasterxml.jackson.annotation.JsonIgnore
import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.luizmatias.workout_tracker.model.group.Group
import com.luizmatias.workout_tracker.model.user.User
import com.luizmatias.workout_tracker.model.workout_log_group_post.WorkoutLogGroupPost
import jakarta.persistence.*
import org.springframework.data.annotation.CreatedDate
import java.time.Instant

@Entity
@Table(name = "group_members")
data class GroupMember(
    @Id @GeneratedValue
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
    val exitedAt: Instant? = null
)