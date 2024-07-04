package com.fxzly.bakeryinventorymanagement.ui.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.fxzly.bakeryinventorymanagement.data.datamodel.Ingredient
import com.fxzly.bakeryinventorymanagement.data.repository.IngredientRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class IngredientViewModel(
    application: Application,
    private val ingredientRepository: IngredientRepository
) : AndroidViewModel(application) {

    val allIngredients: StateFlow<List<Ingredient>> = ingredientRepository.getAllIngredients()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    fun insertIngredient(ingredient: Ingredient) {
        viewModelScope.launch {
            ingredientRepository.insertIngredient(ingredient)}
    }

    fun updateIngredient(ingredient: Ingredient) {
        viewModelScope.launch {
            ingredientRepository.updateIngredient(ingredient)
        }
    }

    fun deleteIngredient(ingredient: Ingredient) {
        viewModelScope.launch {
            ingredientRepository.deleteIngredient(ingredient)
        }
    }
    fun getIngredientById(ingredientId: Int): Flow<Ingredient?> = ingredientRepository.getIngredientById(ingredientId)
}