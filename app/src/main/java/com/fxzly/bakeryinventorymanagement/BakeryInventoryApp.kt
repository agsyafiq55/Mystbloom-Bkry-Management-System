package com.fxzly.bakeryinventorymanagement

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.fxzly.bakeryinventorymanagement.ui.navigation.AppNavigation

@Composable
fun BakeryInventoryApp(navController: NavHostController = rememberNavController()) {
    AppNavigation(navController = navController)
}