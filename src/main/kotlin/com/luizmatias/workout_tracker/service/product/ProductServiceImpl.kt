package com.luizmatias.workout_tracker.service.product

import com.luizmatias.workout_tracker.model.Product
import com.luizmatias.workout_tracker.repository.ProductRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class ProductServiceImpl @Autowired constructor(private val productRepository: ProductRepository) : ProductService {

    override fun getAllProducts(): List<Product> = productRepository.findAll()

    override fun getProductById(id: Long): Product? = productRepository.findById(id).orElse(null)

    override fun createProduct(product: Product): Product = productRepository.save(product)

    override fun updateProduct(id: Long, product: Product): Product? {
        if (productRepository.existsById(id)) {
            return productRepository.save(product)
        }
        return null
    }

    override fun deleteProduct(id: Long): Boolean {
        if (productRepository.existsById(id)) {
            productRepository.deleteById(id)
            return true
        }
        return false
    }
}