package com.luizmatias.workout_tracker.model.workout_log_post

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.luizmatias.workout_tracker.model.user.User
import com.luizmatias.workout_tracker.model.workout_log_group_post.WorkoutLogGroupPost
import jakarta.persistence.*
import org.springframework.data.annotation.CreatedDate
import java.time.Instant

@Entity
@Table(name = "workout_log_posts")
data class WorkoutLogPost(
    @Id @GeneratedValue val id: Long?,
    @field:JsonIgnoreProperties("groups", "createdGroups", "workoutLogPosts", "temporaryTokens")
    @ManyToOne
    @JoinColumn(name = "user_id")
    val user: User,
    val name: String,
    val description: String,
    val photoUrl: String,
    @field:JsonIgnoreProperties("workoutLogPost")
    @OneToMany(mappedBy = "workoutLogPost", fetch = FetchType.LAZY, cascade = [CascadeType.PERSIST, CascadeType.MERGE])
    val workoutLogGroupPosts: List<WorkoutLogGroupPost>,
    @CreatedDate
    val createdAt: Instant,
    val deletedAt: Instant? = null
)