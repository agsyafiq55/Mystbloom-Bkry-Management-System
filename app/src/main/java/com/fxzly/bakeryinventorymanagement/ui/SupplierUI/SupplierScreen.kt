package com.fxzly.bakeryinventorymanagement.ui.SupplierUI

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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.fxzly.bakeryinventorymanagement.data.datamodel.Supplier
import com.fxzly.bakeryinventorymanagement.ui.component.BottomBarNav
import com.fxzly.bakeryinventorymanagement.ui.navigation.ScreenNav
import com.fxzly.bakeryinventorymanagement.ui.viewfactory.AppViewModelProvider
import com.fxzly.bakeryinventorymanagement.ui.viewmodel.SupplierViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SupplierScreen(navController: NavHostController) {
    val viewModel: SupplierViewModel = viewModel(factory = AppViewModelProvider.Factory)
    val suppliers = viewModel.suppliers.collectAsState().value

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Suppliers") },
                actions = {
                    IconButton(onClick = { navController.navigate(ScreenNav.ADD_SUPPLIER_UI) }) {
                        Box(
                            modifier = Modifier
                                .size(40.dp)
                                .clip(RoundedCornerShape(8.dp)) // Set the corner radius for rounded corners
                                .background(Color(0xFFE6A4B4))
                        ) {
                            Icon(
                                Icons.Default.Add,
                                contentDescription = "Add Supplier",
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
            items(suppliers) { supplier ->
                SupplierCard(navController, supplier)
            }
        }
    }
}


@Composable
fun SupplierCard(navController: NavHostController, supplier: Supplier) {
    Card(
        modifier = Modifier
            .padding(14.dp)
            .fillMaxWidth()
            .clickable { navController.navigate("supplier_detail/${supplier.supplier_id}")
            },
        elevation = CardDefaults.cardElevation(8.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color(0xFFF3D0D7), // Updated color
            contentColor = MaterialTheme.colorScheme.onPrimaryContainer
        ),
        shape = RoundedCornerShape(16.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = supplier.supplier_name,
                fontWeight = FontWeight.Bold,
                fontSize = 25.sp
            )
            Spacer(modifier = Modifier.height(10.dp))
            Text(
                text = supplier.supplier_desc,
                fontSize = 15.sp
            )
        }
    }
}