package com.luizmatias.workout_tracker.config.db.seeder

import com.luizmatias.workout_tracker.model.Product
import com.luizmatias.workout_tracker.repository.ProductRepository
import net.datafaker.Faker
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

@Component
class ProductsSeeder @Autowired constructor(private val productRepository: ProductRepository) {

    private val faker = Faker()
    private val logger = LoggerFactory.getLogger(ProductsSeeder::class.java)

    fun addProducts() {
        logger.info("Deleting existing products...")
        productRepository.deleteAllProducts()
        logger.info("Products deleted!")

        logger.info("Adding products...")
        val products = mutableListOf<Product>()
        repeat(100) { i ->
            products.add(
                Product(
                    (i + 1).toLong(),
                    faker.commerce().productName(),
                    "${faker.commerce().productName()} - ${faker.commerce().department()}",
                    faker.commerce().price(1.0, 100000.0).replace(",", ".").toDouble()
                )
            )
        }
        productRepository.saveAll(products)
        logger.info("Products added!")
    }

}