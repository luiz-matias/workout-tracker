package com.luizmatias.workout_tracker.features.workout_log_group_post.controller

import com.luizmatias.workout_tracker.features.common.dto.MessageResponseDTO
import com.luizmatias.workout_tracker.features.user.model.UserPrincipal
import com.luizmatias.workout_tracker.features.workout_log_group_post.dto.ShareWorkoutLogPostDTO
import com.luizmatias.workout_tracker.features.workout_log_group_post.dto.WorkoutLogGroupPostResponseDTO
import com.luizmatias.workout_tracker.features.workout_log_group_post.dto.toWorkoutLogGroupPostDTO
import com.luizmatias.workout_tracker.features.workout_log_group_post.model.WorkoutLogGroupPost
import com.luizmatias.workout_tracker.features.workout_log_group_post.service.WorkoutLogGroupPostService
import jakarta.validation.Valid
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/workout_log_group_posts")
class WorkoutLogGroupPostController @Autowired constructor(
    private val workoutLogGroupPostService: WorkoutLogGroupPostService,
) {
    @PostMapping("/share")
    fun shareWorkoutLogPostIntoGroups(
        @RequestBody @Valid shareWorkoutLogPostDTO: ShareWorkoutLogPostDTO,
        @AuthenticationPrincipal principal: UserPrincipal,
    ): ResponseEntity<List<WorkoutLogGroupPostResponseDTO>> {
        val createdWorkoutShares =
            workoutLogGroupPostService.createWorkoutGroupLogPosts(
                shareWorkoutLogPostDTO.workoutLogPostId,
                shareWorkoutLogPostDTO.groupIds,
                principal.user,
            )
        return ResponseEntity(
            createdWorkoutShares.map(WorkoutLogGroupPost::toWorkoutLogGroupPostDTO),
            HttpStatus.CREATED,
        )
    }

    @PostMapping("/{id}")
    fun deleteWorkoutLogGroupPost(
        @PathVariable id: Long,
        @AuthenticationPrincipal principal: UserPrincipal,
    ): ResponseEntity<MessageResponseDTO> {
        workoutLogGroupPostService.deleteWorkoutGroupLogPost(id, principal.user)
        return ResponseEntity.ok(MessageResponseDTO("Workout log group post deleted."))
    }
}
