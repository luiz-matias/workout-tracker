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
)