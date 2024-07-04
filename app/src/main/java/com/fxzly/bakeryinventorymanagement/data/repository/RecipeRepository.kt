package com.fxzly.bakeryinventorymanagement.data.repository

import com.fxzly.bakeryinventorymanagement.data.dao.RecipeDao
import com.fxzly.bakeryinventorymanagement.data.datamodel.Recipe
import kotlinx.coroutines.flow.Flow

class RecipeRepository(private val recipeDao: RecipeDao) {

    fun getAllRecipes(): Flow<List<Recipe>> = recipeDao.getAllRecipes()fun getRecipeById(recipeId: Int): Flow<Recipe> = recipeDao.getRecipeById(recipeId)

    suspend fun insertRecipe(recipe: Recipe) {
        recipeDao.insertRecipe(recipe)
    }

    suspend fun updateRecipe(recipe: Recipe) {
        recipeDao.updateRecipe(recipe)
    }

    suspend fun deleteRecipe(recipe: Recipe) {
        recipeDao.deleteRecipe(recipe)
    }

    fun searchRecipesByName(query: String): Flow<List<Recipe>> = recipeDao.searchRecipesByName(query)
}