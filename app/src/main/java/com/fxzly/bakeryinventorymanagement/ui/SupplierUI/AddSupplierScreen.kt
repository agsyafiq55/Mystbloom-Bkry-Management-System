package com.fxzly.bakeryinventorymanagement.ui.SupplierUI

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.fxzly.bakeryinventorymanagement.data.datamodel.Supplier
import com.fxzly.bakeryinventorymanagement.ui.component.BottomBarNav
import com.fxzly.bakeryinventorymanagement.ui.viewmodel.SupplierViewModel
import androidx.compose.ui.platform.LocalContext

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddSupplierScreen(navController: NavHostController, supplierViewModel: SupplierViewModel) {
    var supplierName by remember { mutableStateOf("") }
    var ownerName by remember { mutableStateOf("") }
    var contactNo by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    var address by remember { mutableStateOf("") }

    val context = LocalContext.current

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Add Supplier") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        },
        bottomBar = { BottomBarNav(navController) }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .padding(16.dp)
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Card to hold supplier fields
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
                        value = supplierName,
                        onValueChange = { supplierName = it },
                        label = { Text("Supplier Name") },
                        modifier = Modifier.fillMaxWidth(),
                        isError = supplierName.isEmpty()
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    OutlinedTextField(
                        value = ownerName,
                        onValueChange = { ownerName = it },
                        label = { Text("Owner Name") },
                        modifier = Modifier.fillMaxWidth(),
                        isError = ownerName.isEmpty()
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    OutlinedTextField(
                        value = contactNo,
                        onValueChange = { contactNo = it },
                        label = { Text("Contact No") },
                        modifier = Modifier.fillMaxWidth(),
                        isError = contactNo.isEmpty()
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    OutlinedTextField(
                        value = description,
                        onValueChange = { description = it },
                        label = { Text("Description") },
                        modifier = Modifier.fillMaxWidth(),
                        isError = description.isEmpty()
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    OutlinedTextField(
                        value = address,
                        onValueChange = { address = it },
                        label = { Text("Address") },
                        modifier = Modifier.fillMaxWidth(),
                        isError = address.isEmpty()
                    )
                }
            }
            Spacer(modifier = Modifier.height(16.dp))
            Button(
                onClick = {
                    if (supplierName.isNotEmpty() && ownerName.isNotEmpty() && contactNo.isNotEmpty() && description.isNotEmpty() && address.isNotEmpty()) {
                        val newSupplier = Supplier(
                            supplier_name = supplierName,
                            owner_name = ownerName,
                            supplier_contact_no = contactNo,
                            supplier_desc = description,
                            supplier_address = address
                        )
                        supplierViewModel.insertSupplier(newSupplier)
                        navController.popBackStack()
                    } else {
                        Toast.makeText(context, "Please fill in all fields", Toast.LENGTH_SHORT).show()
                    }
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFFE6A4B4) // Set the button's background color
                ),
                modifier = Modifier.fillMaxWidth(0.8f) // Adjust button width as needed
            ) {
                Text("Add Supplier")
            }
        }
    }
}