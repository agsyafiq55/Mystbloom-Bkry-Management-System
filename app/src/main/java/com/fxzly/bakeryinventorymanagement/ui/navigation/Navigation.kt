package com.fxzly.bakeryinventorymanagement.ui.navigation

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.fxzly.bakeryinventorymanagement.ui.IngredientUI.AddIngredientScreen
import com.fxzly.bakeryinventorymanagement.ui.IngredientUI.EditIngredientScreen
import com.fxzly.bakeryinventorymanagement.ui.IngredientUI.IngredientDetailScreen
import com.fxzly.bakeryinventorymanagement.ui.IngredientUI.IngredientScreen
import com.fxzly.bakeryinventorymanagement.ui.ProductUI.AddProductScreen
import com.fxzly.bakeryinventorymanagement.ui.ProductUI.EditProductScreen
import com.fxzly.bakeryinventorymanagement.ui.ProductUI.ProductDetailScreen
import com.fxzly.bakeryinventorymanagement.ui.ProductUI.ProductScreen
import com.fxzly.bakeryinventorymanagement.ui.ProfileUI.EditProfileScreen
import com.fxzly.bakeryinventorymanagement.ui.ProfileUI.ProfileScreen
import com.fxzly.bakeryinventorymanagement.ui.RecipeUI.AddRecipeScreen
import com.fxzly.bakeryinventorymanagement.ui.RecipeUI.EditRecipeScreen
import com.fxzly.bakeryinventorymanagement.ui.RecipeUI.RecipeDetailScreen
import com.fxzly.bakeryinventorymanagement.ui.RecipeUI.RecipeScreen
import com.fxzly.bakeryinventorymanagement.ui.SupplierUI.AddSupplierScreen
import com.fxzly.bakeryinventorymanagement.ui.SupplierUI.EditSupplierScreen
import com.fxzly.bakeryinventorymanagement.ui.SupplierUI.SupplierDetailScreen
import com.fxzly.bakeryinventorymanagement.ui.SupplierUI.SupplierScreen
import com.fxzly.bakeryinventorymanagement.ui.authentication.LoginScreen
import com.fxzly.bakeryinventorymanagement.ui.authentication.RegisterScreen
import com.fxzly.bakeryinventorymanagement.ui.viewfactory.AppViewModelProvider
import com.fxzly.bakeryinventorymanagement.ui.viewmodel.IngredientViewModel
import com.fxzly.bakeryinventorymanagement.ui.viewmodel.ProductViewModel
import com.fxzly.bakeryinventorymanagement.ui.viewmodel.RecipeViewModel
import com.fxzly.bakeryinventorymanagement.ui.viewmodel.SupplierViewModel
import com.fxzly.bakeryinventorymanagement.ui.viewmodel.UserViewModel

object ScreenNav {
    //1. User Auth + Profile
    const val LOGIN = "login"
    const val REGISTER = "register"
    const val PROFILE = "profile"
    const val EDIT_PROFILE = "edit_profile"

    //2. Product
    const val PRODUCT_UI = "product"
    const val ADD_PRODUCT_UI = "add_product"
    const val PRODUCT_DETAIL_UI = "product_detail"
    const val EDIT_PRODUCT_UI = "edit_product"


    //3. Ingredient
    const val INGREDIENTS_UI = "ingredient"
    const val ADD_INGREDIENT_UI = "add_ingredient"
    const val INGREDIENT_DETAIL_UI = "ingredient_detail"
    const val EDIT_INGREDIENT_UI = "edit_ingredient"

    //4. Recipe
    const val RECIPE_UI = "recipe"
    const val ADD_RECIPE_UI = "add_recipe"
    const val RECIPE_DETAIL_UI = "recipe_detail"
    const val EDIT_RECIPE_UI = "edit_recipe"

    //5. Supplier
    const val SUPPLIER_UI = "supplier"
    const val ADD_SUPPLIER_UI = "add_supplier"
    const val SUPPLIER_DETAIL_UI = "supplier_detail"
    const val EDIT_SUPPLIER_UI = "edit_supplier"
}

@Composable
fun AppNavigation(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = ScreenNav.LOGIN
    ) {
        //Auth + Profile Nav
        composable(ScreenNav.LOGIN) {
            val userViewModel: UserViewModel = viewModel(factory = AppViewModelProvider.Factory)
            LoginScreen(navController, userViewModel)
        }
        composable(ScreenNav.REGISTER) {
            val userViewModel: UserViewModel = viewModel(factory = AppViewModelProvider.Factory)
            RegisterScreen(navController, userViewModel)
        }
        composable(
            route = "${ScreenNav.PROFILE}/{userId}",
            arguments = listOf(navArgument("userId") { type = NavType.IntType })
        ) { backStackEntry ->
            val userId = backStackEntry.arguments?.getInt("userId") ?: 0
            ProfileScreen(navController, userId)
        }
        composable(
            route = "${ScreenNav.EDIT_PROFILE}/{userId}",
            arguments = listOf(navArgument("userId") { type = NavType.IntType })
        ) { backStackEntry ->
            val userId = backStackEntry.arguments?.getInt("userId") ?: 0
            EditProfileScreen(navController, userId, viewModel(factory = AppViewModelProvider.Factory))
        }

        //ProductNav
        composable(ScreenNav.PRODUCT_UI) {
            val productViewModel: ProductViewModel = viewModel(factory = AppViewModelProvider.Factory)
            ProductScreen(navController)
        }
        composable(ScreenNav.ADD_PRODUCT_UI) {
            val productViewModel: ProductViewModel = viewModel(factory = AppViewModelProvider.Factory)
            AddProductScreen(navController, productViewModel)
        }
        composable(
            route = "${ScreenNav.PRODUCT_DETAIL_UI}/{productId}",
            arguments = listOf(navArgument("productId") { type = NavType.IntType })
        ) { backStackEntry ->
            val productId = backStackEntry.arguments?.getInt("productId") ?: 0
            val productViewModel: ProductViewModel = viewModel(factory = AppViewModelProvider.Factory)
            ProductDetailScreen(navController, productId, productViewModel)
        }
        composable(
            route = "${ScreenNav.EDIT_PRODUCT_UI}/{productId}",
            arguments = listOf(navArgument("productId") { type = NavType.IntType })
        ) { backStackEntry ->
            val productId = backStackEntry.arguments?.getInt("productId") ?: 0
            val productViewModel: ProductViewModel = viewModel(factory = AppViewModelProvider.Factory)
            EditProductScreen(navController, productId, productViewModel)
        }

        //IngredientNav
        composable(ScreenNav.INGREDIENTS_UI) {
            val ingredientViewModel: IngredientViewModel = viewModel(factory = AppViewModelProvider.Factory)
            IngredientScreen(navController)
        }
        composable(ScreenNav.ADD_INGREDIENT_UI) {
            val ingredientViewModel: IngredientViewModel = viewModel(factory = AppViewModelProvider.Factory)
            AddIngredientScreen(navController, ingredientViewModel)
        }
        composable(
            route = "${ScreenNav.INGREDIENT_DETAIL_UI}/{ingredientId}",
            arguments = listOf(navArgument("ingredientId") { type = NavType.IntType })
        ) { backStackEntry ->
            val ingredientId = backStackEntry.arguments?.getInt("ingredientId") ?: 0
            val ingredientViewModel: IngredientViewModel = viewModel(factory = AppViewModelProvider.Factory)
            IngredientDetailScreen(navController, ingredientId, ingredientViewModel)
        }
        composable(
            route = "${ScreenNav.EDIT_INGREDIENT_UI}/{ingredientId}",
            arguments = listOf(navArgument("ingredientId") { type = NavType.IntType })
        ) { backStackEntry ->
            val ingredientId = backStackEntry.arguments?.getInt("ingredientId") ?: 0
            val ingredientViewModel: IngredientViewModel = viewModel(factory = AppViewModelProvider.Factory)
            EditIngredientScreen(navController, ingredientId, ingredientViewModel)
        }

        //RecipeNav
        composable(ScreenNav.RECIPE_UI) {
            val recipeViewModel: RecipeViewModel = viewModel(factory = AppViewModelProvider.Factory)
            RecipeScreen(navController, recipeViewModel)
        }
        composable(ScreenNav.ADD_RECIPE_UI) {
            val recipeViewModel: RecipeViewModel = viewModel(factory = AppViewModelProvider.Factory)
            AddRecipeScreen(navController, recipeViewModel)
        }
        composable(
            route = "${ScreenNav.RECIPE_DETAIL_UI}/{recipeId}",
            arguments = listOf(navArgument("recipeId") { type = NavType.IntType })
        ) { backStackEntry ->
            val recipeId = backStackEntry.arguments?.getInt("recipeId") ?: 0
            val recipeViewModel: RecipeViewModel = viewModel(factory = AppViewModelProvider.Factory)
            RecipeDetailScreen(navController, recipeId, recipeViewModel)
        }
        composable(
            route = "${ScreenNav.EDIT_RECIPE_UI}/{recipeId}",
            arguments = listOf(navArgument("recipeId") { type = NavType.IntType })
        ) { backStackEntry ->
            val recipeId = backStackEntry.arguments?.getInt("recipeId") ?: 0
            val recipeViewModel: RecipeViewModel = viewModel(factory = AppViewModelProvider.Factory)
            EditRecipeScreen(navController, recipeId, recipeViewModel)
        }

        //SupplierNav
        composable(ScreenNav.SUPPLIER_UI) {
            val supplierViewModel: SupplierViewModel = viewModel(factory = AppViewModelProvider.Factory)
            SupplierScreen(navController)
        }
        composable(ScreenNav.ADD_SUPPLIER_UI) {
            val supplierViewModel: SupplierViewModel = viewModel(factory = AppViewModelProvider.Factory)
            AddSupplierScreen(navController, supplierViewModel)
        }
        composable(
            route = "${ScreenNav.SUPPLIER_DETAIL_UI}/{supplierId}",
            arguments = listOf(navArgument("supplierId") { type = NavType.IntType })
        ) { backStackEntry ->
            val supplierId = backStackEntry.arguments?.getInt("supplierId") ?: 0
            SupplierDetailScreen(navController, supplierId, viewModel(factory = AppViewModelProvider.Factory))
        }
        composable(
            route = "${ScreenNav.EDIT_SUPPLIER_UI}/{supplierId}",
            arguments = listOf(navArgument("supplierId") { type = NavType.IntType })
        ) { backStackEntry ->
            val supplierId = backStackEntry.arguments?.getInt("supplierId") ?: 0
            EditSupplierScreen(navController, supplierId, viewModel(factory = AppViewModelProvider.Factory))
        }
    }
}