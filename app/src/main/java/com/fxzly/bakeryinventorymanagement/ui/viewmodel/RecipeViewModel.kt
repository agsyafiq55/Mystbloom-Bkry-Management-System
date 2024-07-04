package com.fxzly.bakeryinventorymanagement.ui.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.fxzly.bakeryinventorymanagement.data.datamodel.Recipe
import com.fxzly.bakeryinventorymanagement.data.repository.RecipeRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class RecipeViewModel(
    application: Application,
    private val recipeRepository: RecipeRepository
) : AndroidViewModel(application) {

    val allRecipes: StateFlow<List<Recipe>> = recipeRepository.getAllRecipes()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    fun insertRecipe(recipe: Recipe) {
        viewModelScope.launch {
            recipeRepository.insertRecipe(recipe)
        }
    }

    fun updateRecipe(recipe: Recipe) {
        viewModelScope.launch {
            recipeRepository.updateRecipe(recipe)
        }
    }

    fun deleteRecipe(recipe: Recipe) {
        viewModelScope.launch {
            recipeRepository.deleteRecipe(recipe)
        }
    }

    fun getRecipeById(recipeId: Int): Flow<Recipe?> = recipeRepository.getRecipeById(recipeId)

    fun searchRecipesByName(query: String): Flow<List<Recipe>> = recipeRepository.searchRecipesByName(query)
}