package com.luizmatias.workout_tracker.controller

import com.luizmatias.workout_tracker.config.exception.common_exceptions.NotFoundException
import com.luizmatias.workout_tracker.model.Product
import com.luizmatias.workout_tracker.service.product.ProductService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/products")
class ProductController @Autowired constructor(private val productService: ProductService) {

    @GetMapping("", "/")
    fun getAllProducts(): ResponseEntity<List<Product>> {
        return ResponseEntity.ok(productService.getAllProducts())
    }

    @GetMapping("/{id}")
    fun getProductById(@PathVariable id: Long): ResponseEntity<Product> {
        return productService.getProductById(id)?.let { ResponseEntity.ok(it) }
            ?: throw NotFoundException("Product not found")
    }

    @PostMapping("", "/")
    fun createProduct(@RequestBody product: Product): Product {
        return productService.createProduct(product)
    }

    @PutMapping("/{id}")
    fun updateProduct(@PathVariable id: Long, @RequestBody product: Product): ResponseEntity<Product> {
        return productService.updateProduct(id, product)?.let {
            ResponseEntity.ok(it)
        } ?: throw NotFoundException("Product not found")
    }

    @DeleteMapping("/{id}")
    fun deleteProduct(@PathVariable id: Long): ResponseEntity<Void> {
        return if (productService.deleteProduct(id)) {
            ResponseEntity.noContent().build()
        } else {
            throw NotFoundException("Product not found")
        }
    }

}