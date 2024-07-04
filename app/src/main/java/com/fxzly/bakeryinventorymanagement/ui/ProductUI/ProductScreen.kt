package com.fxzly.bakeryinventorymanagement.ui.ProductUI

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
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
import com.fxzly.bakeryinventorymanagement.data.datamodel.Product
import com.fxzly.bakeryinventorymanagement.ui.component.BottomBarNav
import com.fxzly.bakeryinventorymanagement.ui.navigation.ScreenNav
import com.fxzly.bakeryinventorymanagement.ui.viewfactory.AppViewModelProvider
import com.fxzly.bakeryinventorymanagement.ui.viewmodel.ProductViewModel
import java.io.File

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductScreen(navController: NavHostController) {
    val viewModel: ProductViewModel = viewModel(factory = AppViewModelProvider.Factory)
    val products = viewModel.products.collectAsState().value

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Products") },
                actions = {
                    IconButton(onClick = { navController.navigate(ScreenNav.ADD_PRODUCT_UI) }) {
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
            items(products) { product ->
                ProductCard(navController, product)
            }
        }
    }
}

@Composable
fun ProductCard(navController: NavHostController, product: Product) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .clickable {
                navController.navigate("product_detail/${product.product_id}")
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
            horizontalAlignment = Alignment.Start
        ) {
            // Load and display product image from internal storage
            val imagePath = product.product_img
            if (imagePath != null) {
                Image(
                    painter = rememberImagePainter(File(imagePath)),
                    contentDescription = null,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp)
                        .clip(RoundedCornerShape(16.dp)), // Add rounded corners
                    contentScale = ContentScale.Crop
                )
            }

            Spacer(modifier = Modifier.height(8.dp))
            Text(
                product.product_name,
                style = MaterialTheme.typography.titleLarge,
                textAlign = TextAlign.Center
            )
            Text(
                text = "RM${product.product_price} ",
                style = MaterialTheme.typography.bodyLarge,
                textAlign = TextAlign.Center
            )
        }
    }
}