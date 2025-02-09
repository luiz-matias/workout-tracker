package com.luizmatias.workout_tracker

import com.luizmatias.workout_tracker.db.seeder.DatabaseSeeder
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class WorkoutTrackerApplication

fun main(args: Array<String>) {
    val context = runApplication<WorkoutTrackerApplication>(*args)
    context.getBean(DatabaseSeeder::class.java).seed()
}
