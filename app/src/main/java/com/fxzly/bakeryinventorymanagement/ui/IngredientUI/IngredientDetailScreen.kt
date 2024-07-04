package com.fxzly.bakeryinventorymanagement.ui.IngredientUI

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil.compose.rememberImagePainter
import com.fxzly.bakeryinventorymanagement.ui.component.BottomBarNav
import com.fxzly.bakeryinventorymanagement.ui.viewmodel.IngredientViewModel
import java.io.File

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun IngredientDetailScreen(
    navController: NavHostController,
    ingredientId: Int,
    ingredientViewModel: IngredientViewModel
) {
    val ingredient by ingredientViewModel.getIngredientById(ingredientId).collectAsState(initial = null)

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Ingredient Details") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        },
        bottomBar = { BottomBarNav(navController) }
    ) { padding ->
        Box(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            ingredient?.let { ingredient ->
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Card(
                        modifier = Modifier
                            .padding(16.dp)
                            .fillMaxWidth(),
                        shape = RoundedCornerShape(12.dp),
                        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
                        colors = CardDefaults.cardColors(
                            containerColor = Color(android.graphics.Color.parseColor("#F3D0D7")),
                            contentColor = MaterialTheme.colorScheme.onPrimaryContainer
                        )
                    ){
                        Column(
                            modifier = Modifier
                                .padding(16.dp)
                                .verticalScroll(rememberScrollState()),
                            horizontalAlignment = Alignment.Start
                        ) {
                            // Load and display ingredient image from internal storage
                            val imagePath = ingredient.ingredient_img
                            if (imagePath != null) {
                                Image(
                                    painter = rememberImagePainter(File(imagePath)),
                                    contentDescription = null,
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .height(400.dp)
                                        .clip(RoundedCornerShape(12.dp)),
                                    contentScale = ContentScale.Crop
                                )
                            }
                            Spacer(modifier = Modifier.height(8.dp))
                            Text(text = ingredient.ingredient_name, style = MaterialTheme.typography.titleLarge)
                            Spacer(modifier = Modifier.height(8.dp))
                            Text(text = ingredient.ingredient_desc,
                                style = MaterialTheme.typography.bodyMedium,
                                textAlign = TextAlign.Justify
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                            Text(text = "Price:",
                                style = MaterialTheme.typography.titleMedium,
                                textAlign = TextAlign.Left
                            )
                            Text(text = "RM${ingredient.ingredient_price}/kg",
                                style = MaterialTheme.typography.bodyMedium,
                                textAlign = TextAlign.Left
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                            Text(text = "Stock:",
                                style = MaterialTheme.typography.titleMedium,
                                textAlign = TextAlign.Left
                            )
                            Text(text = "${ingredient.ingredient_stock} units",
                                style = MaterialTheme.typography.bodyMedium,
                                textAlign = TextAlign.Left
                            )
                            Spacer(modifier = Modifier.height(16.dp))
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Button(
                                    onClick = { navController.navigate("edit_ingredient/${ingredient.ingredient_id}") },
                                    colors = ButtonDefaults.buttonColors(
                                        containerColor = Color(android.graphics.Color.parseColor("#E6A4B4")) // Set the button's background color
                                    ),
                                    modifier = Modifier.weight(1f)
                                ) {
                                    Text("Edit", fontSize = 16.sp)
                                }
                                Spacer(modifier = Modifier.width(15.dp))
                                Button(
                                    onClick = {
                                        ingredientViewModel.deleteIngredient(ingredient)
                                        navController.popBackStack()
                                    },
                                    colors = ButtonDefaults.buttonColors(
                                        containerColor = Color(android.graphics.Color.parseColor("#E6A4B4")) // Set the button's background color
                                    ),
                                    modifier = Modifier.weight(1f)
                                ) {
                                    Text("Delete", fontSize = 16.sp)
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}