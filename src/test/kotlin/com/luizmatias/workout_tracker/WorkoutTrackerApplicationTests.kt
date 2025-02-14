package com.luizmatias.workout_tracker

import org.junit.jupiter.api.Test
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles

@SpringBootTest
@ActiveProfiles("test")
class WorkoutTrackerApplicationTests {
    @Test
    fun contextLoads() {
        println("Context loaded!")
    }
}
