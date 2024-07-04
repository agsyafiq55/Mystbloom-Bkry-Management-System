package com.fxzly.bakeryinventorymanagement.ui.IngredientUI

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
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import coil.compose.rememberImagePainter
import com.fxzly.bakeryinventorymanagement.data.datamodel.Ingredient
import com.fxzly.bakeryinventorymanagement.ui.viewmodel.IngredientViewModel
import java.io.File
import java.io.FileOutputStream
import java.io.IOException

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddIngredientScreen(navController: NavHostController, ingredientViewModel: IngredientViewModel) {
    var ingredientName by remember { mutableStateOf("") }
    var ingredientDesc by remember { mutableStateOf("") }
    var ingredientPrice by remember { mutableStateOf("") }
    var ingredientStock by remember { mutableStateOf("") }
    var ingredientImgUri by remember { mutableStateOf<Uri?>(null) }
    var priceError by remember { mutableStateOf(false) }
    var stockError by remember { mutableStateOf(false) }

    val context = LocalContext.current

    // Launcher for selecting an image from the gallery
    val imagePickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        ingredientImgUri = uri
        if (uri != null) {
            Log.d("AddIngredientScreen", "Image URI: $uri")
        } else {
            Log.d("AddIngredientScreen", "No image selected")
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Add Ingredient") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { padding ->
        LazyColumn(
            modifier = Modifier
                .padding(padding)
                .padding(16.dp)
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            item {
                // Card to hold ingredient fields
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
                            value = ingredientName,
                            onValueChange = { ingredientName = it },
                            label = { Text("Ingredient Name") },
                            modifier = Modifier.fillMaxWidth(),
                            isError = ingredientName.isEmpty()
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        OutlinedTextField(
                            value = ingredientDesc,
                            onValueChange = { ingredientDesc = it },
                            label = { Text("Ingredient Description") },
                            modifier = Modifier.fillMaxWidth().height(150.dp),
                            isError = ingredientDesc.isEmpty(),
                            maxLines = 5 // Limit the number of visible lines
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        OutlinedTextField(
                            value = ingredientPrice,
                            onValueChange = {
                                ingredientPrice = it
                                priceError = !ingredientPrice.matches(Regex("^\\d*\\.?\\d*\$"))
                            },
                            label = { Text("Ingredient Price") },
                            modifier = Modifier.fillMaxWidth(),
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                            isError = priceError
                        )
                        if (priceError) {
                            Text(
                                text = "Please enter a valid price",
                                color = MaterialTheme.colorScheme.error,
                                style = MaterialTheme.typography.bodySmall
                            )
                        }
                        Spacer(modifier = Modifier.height(8.dp))
                        OutlinedTextField(
                            value = ingredientStock,
                            onValueChange = {
                                ingredientStock = it
                                stockError = !ingredientStock.matches(Regex("^\\d*\$"))
                            },
                            label = { Text("Ingredient Stock") },
                            modifier = Modifier.fillMaxWidth(),
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                            isError = stockError
                        )
                        if (stockError) {
                            Text(
                                text = "Please enter a valid stock count",
                                color = MaterialTheme.colorScheme.error,
                                style = MaterialTheme.typography.bodySmall
                            )
                        }
                        Spacer(modifier = Modifier.height(8.dp))
                        Button(
                            onClick = {
                                Log.d("AddIngredientScreen", "Select Image button clicked")
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
                        ingredientImgUri?.let {
                            Image(
                                painter = rememberImagePainter(it),
                                contentDescription = null,
                                modifier = Modifier
                                    .size(128.dp)
                                    .align(Alignment.CenterHorizontally) // Center image
                            )
                        }
                    }
                }
                Spacer(modifier = Modifier.height(16.dp))
                Button(
                    onClick = {
                        if (ingredientName.isNotEmpty() && ingredientDesc.isNotEmpty() && ingredientPrice.isNotEmpty() && ingredientStock.isNotEmpty() && !priceError && !stockError) {
                            val imagePath = ingredientImgUri?.let { uri -> saveImageToInternalStorage(context, uri) }
                            if (imagePath != null) {
                                val ingredient = Ingredient(
                                    ingredient_name = ingredientName,
                                    ingredient_desc = ingredientDesc,
                                    ingredient_price = ingredientPrice.toFloat(),
                                    ingredient_stock = ingredientStock.toInt(),
                                    ingredient_img = imagePath
                                )
                                ingredientViewModel.insertIngredient(ingredient)
                                navController.popBackStack()
                            } else {
                                Toast.makeText(context, "Failed to save image", Toast.LENGTH_SHORT).show()
                            }
                        } else {
                            Toast.makeText(context, "Please fill in all fields correctly", Toast.LENGTH_SHORT).show()
                        }
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFFE6A4B4)
                    ),
                    modifier = Modifier.fillMaxWidth(0.8f)
                ) {
                    Text("Add Ingredient")
                }
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