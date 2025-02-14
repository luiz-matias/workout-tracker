package com.luizmatias.workout_tracker.controller

import com.luizmatias.workout_tracker.service.user.UserService
import com.luizmatias.workout_tracker.service.workout_log_post.WorkoutLogPostService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/workout_log_posts")
class WorkoutLogPostController @Autowired constructor(
    private val userService: UserService,
    private val workoutLogPostService: WorkoutLogPostService,
)
