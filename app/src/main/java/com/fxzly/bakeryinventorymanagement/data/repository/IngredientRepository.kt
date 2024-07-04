package com.fxzly.bakeryinventorymanagement.data.repository

import com.fxzly.bakeryinventorymanagement.data.dao.IngredientDao
import com.fxzly.bakeryinventorymanagement.data.datamodel.Ingredient
import kotlinx.coroutines.flow.Flow

class IngredientRepository(private val ingredientDao: IngredientDao) {

    fun getAllIngredients(): Flow<List<Ingredient>> = ingredientDao.getAllIngredients()
    fun getIngredientById(ingredientId: Int): Flow<Ingredient> =
        ingredientDao.getIngredientById(ingredientId)

    suspend fun insertIngredient(ingredient: Ingredient) {
        ingredientDao.insertIngredient(ingredient)
    }

    suspend fun updateIngredient(ingredient: Ingredient) {
        ingredientDao.updateIngredient(ingredient)
    }

    suspend fun deleteIngredient(ingredient: Ingredient) {
        ingredientDao.deleteIngredient(ingredient)
    }
}