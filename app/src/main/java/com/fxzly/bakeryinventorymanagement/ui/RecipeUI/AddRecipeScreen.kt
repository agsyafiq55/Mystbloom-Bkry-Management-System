package com.fxzly.bakeryinventorymanagement.ui.RecipeUI

import android.content.Context
import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import coil.compose.rememberImagePainter
import com.fxzly.bakeryinventorymanagement.data.datamodel.Recipe
import com.fxzly.bakeryinventorymanagement.ui.viewmodel.RecipeViewModel
import java.io.File
import java.io.FileOutputStream
import java.io.IOException

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddRecipeScreen(navController: NavHostController, recipeViewModel: RecipeViewModel) {
    var recipeName by remember { mutableStateOf("") }
    var recipeDesc by remember { mutableStateOf("") }
    var recipeImgUri by remember { mutableStateOf<Uri?>(null) }

    val context = LocalContext.current

    // Launcher for selecting an image from the gallery
    val imagePickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        recipeImgUri = uri
        if (uri != null) {
            Log.d("AddRecipeScreen", "Image URI: $uri")
        } else {
            Log.d("AddRecipeScreen", "No image selected")
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Add Recipe") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .padding(16.dp)
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Card to hold recipe fields
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
                colors = CardDefaults.cardColors(
                    containerColor = Color(0xFFFFE1E1), // Set the card's background color
                    contentColor = MaterialTheme.colorScheme.onPrimaryContainer // Set the card's content color
                )
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    OutlinedTextField(
                        value = recipeName,
                        onValueChange = { recipeName = it },
                        label = { Text("Recipe Name") },
                        modifier = Modifier.fillMaxWidth(),
                        isError = recipeName.isEmpty()
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    OutlinedTextField(
                        value = recipeDesc,
                        onValueChange = { recipeDesc = it },
                        label = { Text("Recipe Description") },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(200.dp),
                        isError = recipeDesc.isEmpty(),
                        maxLines = 10
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Button(
                        onClick = {
                            Log.d("AddRecipeScreen", "Select Image button clicked")
                            imagePickerLauncher.launch("image/*")
                        },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color(0xFFE6A4B4) // Set the button's background color
                        ),
                        modifier = Modifier.align(Alignment.CenterHorizontally) // Center the button
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
                        val imagePath = recipeImgUri?.let { uri -> saveImageToInternalStorage(context, uri) }
                        if (imagePath != null) {
                            val newRecipe = Recipe(
                                recipe_name = recipeName,
                                recipe_desc = recipeDesc,
                                recipe_img = imagePath
                            )
                            recipeViewModel.insertRecipe(newRecipe)
                            navController.popBackStack()
                        } else {
                            Toast.makeText(context, "Failed to save image", Toast.LENGTH_SHORT).show()
                        }
                    } else {
                        Toast.makeText(context, "Please fill in all fields", Toast.LENGTH_SHORT).show()
                    }
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFFE6A4B4) // Set the button's background color
                ),
                modifier = Modifier.fillMaxWidth(0.8f)
            ) {
                Text("Add Recipe")
            }
        }
    }
}

fun saveImageToInternalStorage(context: Context, uri: Uri): String? {
    return try {
        val bitmap = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            ImageDecoder.decodeBitmap(ImageDecoder.createSource(context.contentResolver, uri))
        } else {
            @Suppress("DEPRECATION")
            MediaStore.Images.Media.getBitmap(context.contentResolver, uri)
        }
        val file = File(context.filesDir, "${System.currentTimeMillis()}.jpg")
        val fos = FileOutputStream(file)
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos)
        fos.close()
        file.absolutePath
    } catch (e: IOException) {
        e.printStackTrace()
        null
    }
}