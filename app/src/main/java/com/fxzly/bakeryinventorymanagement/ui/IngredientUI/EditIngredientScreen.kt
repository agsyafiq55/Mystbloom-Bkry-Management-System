package com.fxzly.bakeryinventorymanagement.ui.IngredientUI

import android.net.Uri
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
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
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import coil.compose.rememberImagePainter
import com.fxzly.bakeryinventorymanagement.ui.viewmodel.IngredientViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditIngredientScreen(
    navController: NavHostController,
    ingredientId: Int,
    ingredientViewModel: IngredientViewModel
) {
    val ingredient by ingredientViewModel.getIngredientById(ingredientId).collectAsState(initial = null)
    var ingredientName by remember { mutableStateOf("") }
    var ingredientDesc by remember { mutableStateOf("") }
    var ingredientPrice by remember { mutableStateOf("") }
    var ingredientStock by remember { mutableStateOf("") }
    var ingredientImgUri by remember { mutableStateOf<Uri?>(null) }
    var priceError by remember { mutableStateOf(false) }
    var stockError by remember { mutableStateOf(false) }

    val context = LocalContext.current

    val imagePickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        ingredientImgUri = uri
        if (uri != null) {
            Log.d("EditIngredientScreen", "Image URI: $uri")
        } else {
            Log.d("EditIngredientScreen", "No image selected")
        }
    }

    LaunchedEffect(ingredient) {
        ingredient?.let {
            ingredientName = it.ingredient_name
            ingredientDesc = it.ingredient_desc
            ingredientPrice = it.ingredient_price.toString()
            ingredientStock = it.ingredient_stock.toString()
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Edit Ingredient") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { innerPadding ->
        ingredient?.let {
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
                            value = ingredientName,
                            onValueChange = { ingredientName = it },
                            label = { Text("Ingredient Name") },
                            modifier = Modifier.fillMaxWidth()
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        OutlinedTextField(
                            value = ingredientDesc,
                            onValueChange = { ingredientDesc = it },
                            label = { Text("Ingredient Description") },
                            modifier = Modifier.fillMaxWidth()
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
                                Log.d("EditIngredientScreen", "Select Image button clicked")
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
                                    .align(Alignment.CenterHorizontally)
                            )
                        }
                    }
                }
                Spacer(modifier = Modifier.height(16.dp))
                Button(
                    onClick = {
                        if (ingredientName.isNotEmpty() && ingredientDesc.isNotEmpty() && ingredientPrice.isNotEmpty() && ingredientStock.isNotEmpty() && !priceError && !stockError) {
                            val imagePath = ingredientImgUri?.let { uri -> saveImageToInternalStorage(context, uri) } ?: it.ingredient_img
                            val updatedIngredient = it.copy(
                                ingredient_name = ingredientName,
                                ingredient_desc = ingredientDesc,
                                ingredient_price = ingredientPrice.toFloatOrNull() ?: it.ingredient_price,
                                ingredient_stock = ingredientStock.toIntOrNull() ?: it.ingredient_stock,
                                ingredient_img = imagePath
                            )
                            ingredientViewModel.updateIngredient(updatedIngredient)
                            navController.popBackStack()
                        } else {
                            Toast.makeText(context, "Please fill in all fields correctly", Toast.LENGTH_SHORT).show()
                        }
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFFE6A4B4)
                    ),
                    modifier = Modifier.fillMaxWidth(0.8f)
                ) {
                    Text("Update Ingredient")
                }
            }
        } ?: Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator()
        }
    }
}
