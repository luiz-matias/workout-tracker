package com.luizmatias.workout_tracker.model

import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table

@Entity
@Table(name = "products")
data class Product(
    @Id val id: Long,
    val name: String,
    val description: String,
    val price: Double
)