package com.luizmatias.workout_tracker.service.product

import com.luizmatias.workout_tracker.model.Product

interface ProductService {

    fun getAllProducts(): List<Product>

    fun getProductById(id: Long): Product?

    fun createProduct(product: Product): Product

    fun updateProduct(id: Long, product: Product): Product?

    fun deleteProduct(id: Long): Boolean

}