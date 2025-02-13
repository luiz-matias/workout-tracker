package com.luizmatias.workout_tracker.controller

import com.luizmatias.workout_tracker.config.api.exception.common_exceptions.NotFoundException
import com.luizmatias.workout_tracker.config.api.exception.common_exceptions.UnauthorizedException
import com.luizmatias.workout_tracker.model.user.UserPrincipal
import com.luizmatias.workout_tracker.model.workout_log_post.WorkoutLogPost
import com.luizmatias.workout_tracker.service.user.UserService
import com.luizmatias.workout_tracker.service.workout_log_post.WorkoutLogPostService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/workout_log_posts")
class WorkoutLogPostController @Autowired constructor(
    private val userService: UserService,
    private val workoutLogPostService: WorkoutLogPostService,
) {

    @GetMapping("", "/")
    fun getAllByUser(@AuthenticationPrincipal userPrincipal: UserPrincipal): ResponseEntity<List<WorkoutLogPost>> {
        val user =
            userService.getUserByEmail(userPrincipal.username) ?: throw UnauthorizedException("User not authenticated.")
        return ResponseEntity.ok(workoutLogPostService.getAllWorkoutLogPostsByUser(user))
    }

    @GetMapping("/{id}")
    fun getById(@PathVariable id: Long): ResponseEntity<WorkoutLogPost> {
        return workoutLogPostService.getWorkoutLogPostById(id)?.let { ResponseEntity.ok(it) }
            ?: throw NotFoundException("Workout not found")
    }

    @PostMapping("", "/")
    fun create(@RequestBody workoutLogPost: WorkoutLogPost): WorkoutLogPost {
        return workoutLogPostService.createWorkoutLogPost(workoutLogPost)
    }

    @PutMapping("/{id}")
    fun update(@PathVariable id: Long, @RequestBody workoutLogPost: WorkoutLogPost): ResponseEntity<WorkoutLogPost> {
        return workoutLogPostService.updateWorkoutLogPost(id, workoutLogPost)?.let {
            ResponseEntity.ok(it)
        } ?: throw NotFoundException("Workout not found")
    }

    @DeleteMapping("/{id}")
    fun delete(@PathVariable id: Long): ResponseEntity<Void> {
        return if (workoutLogPostService.deleteWorkoutLogPost(id)) {
            ResponseEntity.noContent().build()
        } else {
            throw NotFoundException("Workout not found")
        }
    }

}