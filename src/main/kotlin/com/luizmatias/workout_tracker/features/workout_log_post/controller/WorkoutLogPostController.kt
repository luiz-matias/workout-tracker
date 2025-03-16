package com.luizmatias.workout_tracker.features.workout_log_post.controller

import com.luizmatias.workout_tracker.features.common.dto.MessageResponseDTO
import com.luizmatias.workout_tracker.features.common.dto.PageRequestDTO
import com.luizmatias.workout_tracker.features.common.dto.PageResponseDTO
import com.luizmatias.workout_tracker.features.common.dto.toPageRequest
import com.luizmatias.workout_tracker.features.common.dto.toPageResponseDTO
import com.luizmatias.workout_tracker.features.user.model.UserPrincipal
import com.luizmatias.workout_tracker.features.workout_log_post.dto.WorkoutLogPostCreationDTO
import com.luizmatias.workout_tracker.features.workout_log_post.dto.WorkoutLogPostResponseDTO
import com.luizmatias.workout_tracker.features.workout_log_post.dto.toWorkoutLogPostCreation
import com.luizmatias.workout_tracker.features.workout_log_post.dto.toWorkoutLogPostDTO
import com.luizmatias.workout_tracker.features.workout_log_post.dto.toWorkoutLogPostEdit
import com.luizmatias.workout_tracker.features.workout_log_post.model.WorkoutLogPost
import com.luizmatias.workout_tracker.features.workout_log_post.service.WorkoutLogPostService
import jakarta.validation.Valid
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/workout_log_posts")
class WorkoutLogPostController @Autowired constructor(
    private val workoutLogPostService: WorkoutLogPostService,
) {
    @GetMapping("", "/")
    fun getAll(
        @AuthenticationPrincipal principal: UserPrincipal,
        @Valid pageRequestDTO: PageRequestDTO,
    ): ResponseEntity<PageResponseDTO<WorkoutLogPostResponseDTO>> {
        workoutLogPostService.getAllWorkoutLogPostsByUser(principal.user, pageRequestDTO.toPageRequest())
        return ResponseEntity.ok(
            workoutLogPostService.getAllWorkoutLogPostsByUser(
                principal.user,
                pageRequestDTO.toPageRequest(),
            ).toPageResponseDTO(WorkoutLogPost::toWorkoutLogPostDTO),
        )
    }

    @GetMapping("/{id}")
    fun getById(
        @PathVariable id: Long,
        @AuthenticationPrincipal principal: UserPrincipal,
    ): ResponseEntity<WorkoutLogPostResponseDTO> =
        ResponseEntity.ok(workoutLogPostService.getWorkoutLogPostById(id, principal.user).toWorkoutLogPostDTO())

    @PostMapping("", "/")
    fun createWorkoutLogPost(
        @RequestBody @Valid workoutLogPostCreationDTO: WorkoutLogPostCreationDTO,
        @AuthenticationPrincipal principal: UserPrincipal,
    ): ResponseEntity<WorkoutLogPostResponseDTO> {
        val workoutLogPost =
            workoutLogPostService.createWorkoutLogPost(
                workoutLogPostCreationDTO.toWorkoutLogPostCreation(principal.user),
            )
        return ResponseEntity(workoutLogPost.toWorkoutLogPostDTO(), HttpStatus.CREATED)
    }

    @PutMapping("/{id}")
    fun editWorkoutLogPost(
        @PathVariable id: Long,
        @RequestBody @Valid workoutLogPostUpdateDTO: WorkoutLogPostCreationDTO,
        @AuthenticationPrincipal principal: UserPrincipal,
    ): ResponseEntity<WorkoutLogPostResponseDTO> {
        val workoutLogPost =
            workoutLogPostService.updateWorkoutLogPost(
                id,
                workoutLogPostUpdateDTO.toWorkoutLogPostEdit(principal.user),
                principal.user,
            )
        return ResponseEntity.ok(workoutLogPost.toWorkoutLogPostDTO())
    }

    @DeleteMapping("/{id}")
    fun deleteWorkoutLogPost(
        @PathVariable id: Long,
        @AuthenticationPrincipal principal: UserPrincipal,
    ): ResponseEntity<MessageResponseDTO> {
        workoutLogPostService.deleteWorkoutLogPost(id, principal.user)
        return ResponseEntity.ok(MessageResponseDTO("Workout log post deleted successfully."))
    }
}
