package com.luizmatias.workout_tracker.repository

import com.luizmatias.workout_tracker.model.group.Group
import com.luizmatias.workout_tracker.model.user.User
import jakarta.transaction.Transactional
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository

@Repository
interface GroupRepository : JpaRepository<Group, Long> {
    @Modifying
    @Transactional
    @Query("DELETE FROM Group")
    fun deleteAllGroups()

    @Query(
        "SELECT g FROM Group g " +
            "INNER JOIN GroupMember gm ON g = gm.group " +
            "INNER JOIN User u ON gm.user = u " +
            "WHERE u = :user",
    )
    fun findAllByUser(
        user: User,
        pageable: Pageable,
    ): Page<Group>
}
