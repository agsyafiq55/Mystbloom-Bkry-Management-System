package com.fxzly.bakeryinventorymanagement.data.repository

import com.fxzly.bakeryinventorymanagement.data.dao.ProductDao
import com.fxzly.bakeryinventorymanagement.data.datamodel.Product
import kotlinx.coroutines.flow.Flow

class ProductRepository(private val productDao: ProductDao) {
    fun getAllProducts(): Flow<List<Product>> {
        return productDao.getAllProducts()
    }

    fun getProduct(productId: Int): Flow<Product?> {
        return productDao.getProductById(productId)
    }

    suspend fun insertProduct(product: Product) {
        productDao.insertProduct(product)
    }

    suspend fun updateProduct(product: Product) {
        productDao.updateProduct(product)
    }

    suspend fun deleteProduct(product: Product) {
        productDao.deleteProduct(product)
    }
}