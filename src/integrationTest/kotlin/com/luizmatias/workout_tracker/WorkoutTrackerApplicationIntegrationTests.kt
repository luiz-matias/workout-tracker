package com.luizmatias.workout_tracker

import org.junit.jupiter.api.Test
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import org.springframework.util.Assert

@SpringBootTest
@ActiveProfiles("test")
class WorkoutTrackerApplicationIntegrationTests {

    @Test
    fun contextLoads() {
        println("Context loaded successfully!")
        Thread.sleep(15000)
    }

}
