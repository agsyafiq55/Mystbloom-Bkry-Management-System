package com.fxzly.bakeryinventorymanagement.ui.viewfactory

import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.fxzly.bakeryinventorymanagement.BakeryApplication
import com.fxzly.bakeryinventorymanagement.ui.viewmodel.IngredientViewModel
import com.fxzly.bakeryinventorymanagement.ui.viewmodel.ProductViewModel
import com.fxzly.bakeryinventorymanagement.ui.viewmodel.ProfileViewModel
import com.fxzly.bakeryinventorymanagement.ui.viewmodel.RecipeViewModel
import com.fxzly.bakeryinventorymanagement.ui.viewmodel.SupplierViewModel
import com.fxzly.bakeryinventorymanagement.ui.viewmodel.UserViewModel

object AppViewModelProvider {
    val Factory = viewModelFactory {
        initializer {
            SupplierViewModel(
                bakeryApplication(),
                bakeryApplication().container.supplierRepository
            )
        }
        initializer {
            UserViewModel(
                bakeryApplication(),
                bakeryApplication().container.userRepository
            )
        }
        initializer {
            ProfileViewModel(
                bakeryApplication().container.userRepository
            )
        }
        initializer {
            ProductViewModel(
                bakeryApplication().container.productRepository
            )
        }
        initializer {
            IngredientViewModel(
                bakeryApplication(),
                bakeryApplication().container.ingredientRepository
            )
        }
        initializer {
            RecipeViewModel(
                bakeryApplication(),
                bakeryApplication().container.recipeRepository
            )
        }
    }
}

fun CreationExtras.bakeryApplication(): BakeryApplication =
    (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as BakeryApplication)