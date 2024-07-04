package com.fxzly.bakeryinventorymanagement.ui.RecipeUI

import android.net.Uri
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import coil.compose.rememberImagePainter
import com.fxzly.bakeryinventorymanagement.data.datamodel.Recipe
import com.fxzly.bakeryinventorymanagement.ui.viewmodel.RecipeViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditRecipeScreen(
    navController: NavHostController,
    recipeId: Int,
    recipeViewModel: RecipeViewModel
) {
    val recipe by recipeViewModel.getRecipeById(recipeId).collectAsState(initial = null)

    recipe?.let { recipe ->
        var recipeName by remember { mutableStateOf(recipe.recipe_name) }
        var recipeDesc by remember { mutableStateOf(recipe.recipe_desc) }
        var recipeImgUri by remember { mutableStateOf<Uri?>(null) }

        val context = LocalContext.current

        // Launcher for selecting an image from gallery
        val imagePickerLauncher = rememberLauncherForActivityResult(
            contract = ActivityResultContracts.GetContent()
        ) { uri: Uri? ->
            recipeImgUri = uri
            if (uri != null) {
                Log.d("EditRecipeScreen", "Image URI: $uri")
            } else {
                Log.d("EditRecipeScreen", "No image selected")
            }
        }

        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text("Edit Recipe") },
                    navigationIcon = {
                        IconButton(onClick = { navController.popBackStack() }) {
                            Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                        }
                    }
                )
            }
        ) { innerPadding ->
            Column(
                modifier = Modifier
                    .padding(
                        start = innerPadding.calculateStartPadding(LocalLayoutDirection.current),
                        end = innerPadding.calculateEndPadding(LocalLayoutDirection.current),
                        top = innerPadding.calculateTopPadding()
                    )
                    .verticalScroll(rememberScrollState())
                    .padding(16.dp)
                    .fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp),
                    elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = Color(0xFFFFE1E1),
                        contentColor = MaterialTheme.colorScheme.onPrimaryContainer
                    )
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        OutlinedTextField(
                            value = recipeName,
                            onValueChange = { recipeName = it },
                            label = { Text("Recipe Name") },
                            modifier = Modifier.fillMaxWidth()
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        OutlinedTextField(
                            value = recipeDesc,
                            onValueChange = { recipeDesc = it },
                            label = { Text("Recipe Description") },
                            modifier = Modifier.fillMaxWidth()
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Button(
                            onClick = {
                                Log.d("EditRecipeScreen", "Select Image button clicked")
                                imagePickerLauncher.launch("image/*")
                            },
                            colors = ButtonDefaults.buttonColors(
                                containerColor = Color(0xFFE6A4B4)
                            ),
                            modifier = Modifier.align(Alignment.CenterHorizontally)
                        ) {
                            Text("Select Image")
                        }
                        Spacer(modifier = Modifier.height(8.dp))
                        recipeImgUri?.let {
                            Image(
                                painter = rememberImagePainter(it),
                                contentDescription = null,
                                modifier = Modifier
                                    .size(128.dp)
                                    .align(Alignment.CenterHorizontally)
                            )
                        }
                    }
                }
                Spacer(modifier = Modifier.height(16.dp))
                Button(
                    onClick = {
                        if (recipeName.isNotEmpty() && recipeDesc.isNotEmpty()) {
                            val imagePath = recipeImgUri?.let { uri -> saveImageToInternalStorage(context, uri) } ?: recipe.recipe_img
                            val updatedRecipe = Recipe(
                                recipe_id = recipe.recipe_id,
                                recipe_name = recipeName,
                                recipe_desc = recipeDesc,
                                recipe_img = imagePath
                            )
                            recipeViewModel.updateRecipe(updatedRecipe)
                            navController.popBackStack()
                        } else {
                            Toast.makeText(context, "Please fill in all fields", Toast.LENGTH_SHORT).show()
                        }
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFFE6A4B4)
                    ),
                    modifier = Modifier.fillMaxWidth(0.8f)
                ) {
                    Text("Update Recipe")
                }
            }
        }
    } ?: Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator()
    }
}