package com.luizmatias.workout_tracker

import com.luizmatias.workout_tracker.config.db.seeder.DatabaseSeeder
import java.util.TimeZone
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class WorkoutTrackerApplication

fun main(args: Array<String>) {
    TimeZone.setDefault(TimeZone.getTimeZone("UTC"))
    val context = runApplication<WorkoutTrackerApplication>(*args)
    context.getBean(DatabaseSeeder::class.java).seed()
}
