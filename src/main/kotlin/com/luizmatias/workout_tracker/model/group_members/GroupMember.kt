package com.luizmatias.workout_tracker.model.group_members

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
    @ManyToOne
    @JoinColumn(name = "user_id")
    val user: User,
    @ManyToOne
    @JoinColumn(name = "group_id")
    val group: Group,
    @OneToMany(mappedBy = "groupMember", fetch = FetchType.LAZY)
    val workoutLogGroupPosts: List<WorkoutLogGroupPost>,
    @CreatedDate
    val enteredAt: Instant,
    val exitedAt: Instant? = null
)