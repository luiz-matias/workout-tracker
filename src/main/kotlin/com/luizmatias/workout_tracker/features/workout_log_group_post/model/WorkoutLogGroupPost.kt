package com.luizmatias.workout_tracker.features.workout_log_group_post.model

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.luizmatias.workout_tracker.features.group_member.model.GroupMember
import com.luizmatias.workout_tracker.features.workout_log_post.model.WorkoutLogPost
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import jakarta.persistence.Table
import java.time.Instant
import org.springframework.data.annotation.CreatedDate

@Entity
@Table(name = "workout_log_group_posts")
data class WorkoutLogGroupPost(
    @Id
    @GeneratedValue
    val id: Long?,
    @field:JsonIgnoreProperties("workoutLogGroupPosts")
    @ManyToOne
    @JoinColumn(name = "workout_id")
    val workoutLogPost: WorkoutLogPost,
    @field:JsonIgnoreProperties("workoutLogGroupPosts")
    @ManyToOne
    @JoinColumn(name = "group_members_id")
    val groupMember: GroupMember,
    @CreatedDate
    val createdAt: Instant,
    val deletedAt: Instant? = null,
)
