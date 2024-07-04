package com.fxzly.bakeryinventorymanagement.ui.component

import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.vectorResource
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.fxzly.bakeryinventorymanagement.R

@Composable
fun BottomBarNav(navController: NavHostController) {
    val context = LocalContext.current
    val userId = remember {
        val sharedPreferences = context.getSharedPreferences("user_session", android.content.Context.MODE_PRIVATE)
        sharedPreferences.getInt("user_id", 0)
    }

    val items = listOf(
        ScreenNav.Product,
        ScreenNav.Ingredients,
        ScreenNav.Supplier,
        ScreenNav.Recipe,
        ScreenNav.Profile
    )

    NavigationBar(containerColor = Color(0xFFE6A4B4)) {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentDestination = navBackStackEntry?.destination
        items.forEach { screen ->
            val isSelected = if (screen == ScreenNav.Profile) {
                currentDestination?.route?.startsWith("${ScreenNav.Profile.route}/") == true
            } else {
                currentDestination?.hierarchy?.any { it.route == screen.route } == true
            }

            NavigationBarItem(
                icon = {
                    Icon(
                        imageVector = when (screen) {
                            ScreenNav.Product -> ImageVector.vectorResource(id = R.drawable.ic_product)
                            ScreenNav.Ingredients -> ImageVector.vectorResource(id = R.drawable.ic_ingredient)
                            ScreenNav.Supplier -> ImageVector.vectorResource(id = R.drawable.ic_supplier)
                            ScreenNav.Recipe -> ImageVector.vectorResource(id = R.drawable.ic_recipe)
                            ScreenNav.Profile -> ImageVector.vectorResource(id = R.drawable.ic_profile)
                            else -> throw IllegalArgumentException("Unknown screen")
                        },
                        contentDescription = null
                    )
                },

                label = { Text(screen.route.replaceFirstChar { it.uppercase() }) },
                selected = isSelected,
                onClick = {
                    val route = if (screen == ScreenNav.Profile) {
                        "${ScreenNav.Profile.route}/$userId"
                    } else {
                        screen.route
                    }
                    navController.navigate(route) {
                        popUpTo(navController.graph.findStartDestination().id) {
                            saveState = true
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                },

            )
        }
    }
}
