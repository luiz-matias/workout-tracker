package com.luizmatias.workout_tracker.model.workout_log_group_post

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.luizmatias.workout_tracker.model.group_members.GroupMember
import com.luizmatias.workout_tracker.model.workout_log_post.WorkoutLogPost
import jakarta.persistence.*
import org.springframework.data.annotation.CreatedDate
import java.time.Instant

@Entity
@Table(name = "workout_log_group_posts")
data class WorkoutLogGroupPost(
    @Id @GeneratedValue
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
    val deletedAt: Instant? = null
)