package com.luizmatias.workout_tracker

import com.luizmatias.workout_tracker.db.seeder.DatabaseSeeder
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import java.util.*

@SpringBootApplication
class WorkoutTrackerApplication

fun main(args: Array<String>) {
    TimeZone.setDefault(TimeZone.getTimeZone("UTC"))
    val context = runApplication<WorkoutTrackerApplication>(*args)
    context.getBean(DatabaseSeeder::class.java).seed()
}
