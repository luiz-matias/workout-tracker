package com.luizmatias.workout_tracker.model.group

import com.luizmatias.workout_tracker.model.group_members.GroupMember
import com.luizmatias.workout_tracker.model.user.User
import jakarta.persistence.*
import org.springframework.data.annotation.CreatedDate
import java.time.Instant

@Entity
@Table(name = "groups")
data class Group(
    @Id @GeneratedValue val id: Long?,
    @Column(nullable = false)
    val name: String,
    val description: String,
    val bannerUrl: String,
    @Enumerated(EnumType.STRING) val measurementStrategy: GroupMeasurementStrategy,
    @OneToMany(mappedBy = "group", fetch = FetchType.LAZY)
    val members: List<GroupMember>,
    @ManyToOne(fetch = FetchType.LAZY)
    val createdBy: User,
    @CreatedDate
    val createdAt: Instant,
    val deletedAt: Instant? = null
)