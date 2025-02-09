package com.luizmatias.workout_tracker.config.db.seeder

import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

@Component
class WorkoutTrackerSeeder @Autowired constructor(
    private val usersSeeder: UsersSeeder,
    private val productsSeeder: ProductsSeeder,
) : DatabaseSeeder {

    private val logger = LoggerFactory.getLogger(WorkoutTrackerSeeder::class.java)

    override fun seed() {
        logger.info("Seeding database...")
        usersSeeder.addUsers()
        productsSeeder.addProducts()
        logger.info("Database seeding completed.")
    }

}