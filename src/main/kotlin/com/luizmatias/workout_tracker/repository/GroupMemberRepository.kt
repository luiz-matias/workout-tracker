package com.luizmatias.workout_tracker.repository

import com.luizmatias.workout_tracker.model.group.Group
import com.luizmatias.workout_tracker.model.group_members.GroupMember
import com.luizmatias.workout_tracker.model.user.User
import jakarta.transaction.Transactional
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository

@Repository
interface GroupMemberRepository : JpaRepository<GroupMember, Long> {
    @Modifying
    @Transactional
    @Query("DELETE FROM GroupMember")
    fun deleteAllGroupMembers()

    @Query("SELECT gm FROM GroupMember gm WHERE gm.group = :group")
    fun findAllByGroup(group: Group): List<GroupMember>

    @Query("SELECT gm FROM GroupMember gm WHERE gm.user = :user")
    fun findAllByUser(user: User): List<GroupMember>

    @Query(
        "SELECT CASE WHEN COUNT(gm) > 0 THEN true ELSE false END FROM GroupMember gm" +
            "WHERE gm.user = :user AND gm.group = :group AND gm.exitedAt IS NULL",
    )
    fun existsByUserAndGroup(
        user: User,
        group: Group,
    ): Boolean
}
