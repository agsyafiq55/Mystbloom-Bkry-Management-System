package com.fxzly.bakeryinventorymanagement.ui.RecipeUI

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import coil.compose.rememberImagePainter
import com.fxzly.bakeryinventorymanagement.data.datamodel.Recipe
import com.fxzly.bakeryinventorymanagement.ui.component.BottomBarNav
import com.fxzly.bakeryinventorymanagement.ui.navigation.ScreenNav
import com.fxzly.bakeryinventorymanagement.ui.viewfactory.AppViewModelProvider
import com.fxzly.bakeryinventorymanagement.ui.viewmodel.RecipeViewModel
import java.io.File

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RecipeScreen(navController: NavHostController, recipeViewModel: RecipeViewModel) {
    val viewModel: RecipeViewModel = viewModel(factory = AppViewModelProvider.Factory)
    val recipes by viewModel.allRecipes.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Recipes") },
                actions = {
                    IconButton(onClick = { navController.navigate(ScreenNav.ADD_RECIPE_UI) }) {
                        Box(
                            modifier = Modifier
                                .size(40.dp)
                                .clip(RoundedCornerShape(8.dp)) // Set the corner radius for rounded corners
                                .background(Color(0xFFE6A4B4))
                        ) {
                            Icon(
                                Icons.Default.Add,
                                contentDescription = "Add Product",
                                tint = Color.White, // Set the icon color to contrast with the background
                                modifier = Modifier.align(Alignment.Center) // Center the icon inside the box
                            )
                        }
                    }
                }
            )
        },
        bottomBar = { BottomBarNav(navController) }
    ) { padding ->
        LazyColumn(
            contentPadding = padding,
            modifier = Modifier.fillMaxSize()
        ) {
            items(recipes) { recipe ->
                RecipeCard(navController, recipe)
            }
        }
    }
}

@Composable
fun RecipeCard(navController: NavHostController, recipe: Recipe) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .clickable {
                navController.navigate("${ScreenNav.RECIPE_DETAIL_UI}/${recipe.recipe_id}")
            },
        elevation = CardDefaults.cardElevation(8.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color(0xFFF3D0D7), // New color
            contentColor = MaterialTheme.colorScheme.onPrimaryContainer
        ),
        shape = RoundedCornerShape(16.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Start,
                modifier = Modifier.fillMaxWidth()
            ) {
                val imagePath = recipe.recipe_img
                if (imagePath != null) {
                    Image(
                        painter = rememberImagePainter(File(imagePath)),
                        contentDescription = null,
                        modifier = Modifier
                            .width(80.dp)
                            .height(80.dp)
                            .clip(RoundedCornerShape(16.dp)), // Add rounded corners
                        contentScale = ContentScale.Crop
                    )
                }
                Spacer(modifier = Modifier.width(15.dp))
                Text(
                    text = "${recipe.recipe_name} Recipe",
                    style = MaterialTheme.typography.titleLarge,
                    textAlign = TextAlign.Center,
                )
            }
        }
    }
}