package com.fxzly.bakeryinventorymanagement.ui.ProductUI

import android.net.Uri
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
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
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import coil.compose.rememberImagePainter
import com.fxzly.bakeryinventorymanagement.data.datamodel.Product
import com.fxzly.bakeryinventorymanagement.ui.viewmodel.ProductViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditProductScreen(
    navController: NavHostController,
    productId: Int,
    productViewModel: ProductViewModel
) {
    val product by productViewModel.getProduct(productId).collectAsState(initial = null)

    product?.let { product ->
        var productName by remember { mutableStateOf(product.product_name) }
        var productDesc by remember { mutableStateOf(product.product_desc) }
        var productPrice by remember { mutableStateOf(product.product_price.toString()) }
        var productStock by remember { mutableStateOf(product.product_stock.toString()) }
        var productImgUri by remember { mutableStateOf<Uri?>(null) }
        var priceError by remember { mutableStateOf(false) }
        var stockError by remember { mutableStateOf(false) }

        val context = LocalContext.current

        val imagePickerLauncher = rememberLauncherForActivityResult(
            contract = ActivityResultContracts.GetContent()
        ) { uri: Uri? ->
            productImgUri = uri
            if (uri != null) {
                Log.d("UpdateProductScreen", "Image URI: $uri")
            } else {
                Log.d("UpdateProductScreen", "No image selected")
            }
        }

        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text("Update Product") },
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
                // Card to hold product fields
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
                            value = productName,
                            onValueChange = { productName = it },
                            label = { Text("Product Name") },
                            modifier = Modifier.fillMaxWidth()
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        OutlinedTextField(
                            value = productDesc,
                            onValueChange = { productDesc = it },
                            label = { Text("Product Description") },
                            modifier = Modifier.fillMaxWidth()
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        OutlinedTextField(
                            value = productPrice,
                            onValueChange = {
                                productPrice = it
                                priceError = !productPrice.matches(Regex("^\\d*\\.?\\d*\$"))
                            },
                            label = { Text("Product Price") },
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
                            value = productStock,
                            onValueChange = {
                                productStock = it
                                stockError = !productStock.matches(Regex("^\\d*\$"))
                            },
                            label = { Text("Product Stock") },
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
                                Log.d("UpdateProductScreen", "Select Image button clicked")
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
                        productImgUri?.let {
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

                // Update Product button below the card
                Spacer(modifier = Modifier.height(16.dp))
                Button(
                    onClick = {
                        if (productName.isNotEmpty() && productDesc.isNotEmpty() && productPrice.isNotEmpty() && productStock.isNotEmpty() && !priceError && !stockError) {
                            val imagePath = productImgUri?.let { uri -> saveImageToInternalStorage(context, uri) } ?: product.product_img
                            val updatedProduct = Product(
                                product_id = product.product_id,
                                product_name = productName,
                                product_desc = productDesc,
                                product_price = productPrice.toFloat(),
                                product_stock = productStock.toInt(),
                                product_img = imagePath
                            )
                            productViewModel.updateProduct(updatedProduct)
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
                    Text("Update Product")
                }
            }
        }
    }
}