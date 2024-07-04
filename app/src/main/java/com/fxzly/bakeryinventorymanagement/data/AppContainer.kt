package com.fxzly.bakeryinventorymanagement.data

import android.app.Application
import com.fxzly.bakeryinventorymanagement.data.repository.IngredientRepository
import com.fxzly.bakeryinventorymanagement.data.repository.ProductRepository
import com.fxzly.bakeryinventorymanagement.data.repository.RecipeRepository
import com.fxzly.bakeryinventorymanagement.data.repository.SupplierRepository
import com.fxzly.bakeryinventorymanagement.data.repository.UserRepository

interface AppContainer {
    val productRepository: ProductRepository
    val ingredientRepository: IngredientRepository
    val recipeRepository: RecipeRepository
    val supplierRepository: SupplierRepository
    val userRepository: UserRepository
}

class AppDataContainer(private val application: Application) : AppContainer {
    override val supplierRepository: SupplierRepository by lazy {
        SupplierRepository(BakeryDatabase.getDatabase(application).supplierDao())
    }

    override val userRepository: UserRepository by lazy {
        UserRepository(BakeryDatabase.getDatabase(application).userDao())
    }
    override val productRepository: ProductRepository by lazy {
        ProductRepository(BakeryDatabase.getDatabase(application).productDao())
    }
    override val ingredientRepository: IngredientRepository by lazy {
        IngredientRepository(BakeryDatabase.getDatabase(application).ingredientDao())
    }

    override val recipeRepository: RecipeRepository by lazy {
        RecipeRepository(BakeryDatabase.getDatabase(application).recipeDao())
    }
}


