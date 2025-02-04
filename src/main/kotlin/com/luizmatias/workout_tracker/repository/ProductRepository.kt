package com.luizmatias.workout_tracker.repository

import com.luizmatias.workout_tracker.model.Product
import jakarta.transaction.Transactional
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository

@Repository
interface ProductRepository : JpaRepository<Product, Long> {

    @Modifying
    @Transactional
    @Query("DELETE FROM Product")
    fun deleteAllProducts()

}