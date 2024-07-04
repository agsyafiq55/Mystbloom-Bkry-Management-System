package com.fxzly.bakeryinventorymanagement.ui.ProfileUI

import android.content.Context
import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.material3.Button
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import coil.compose.rememberAsyncImagePainter
import com.fxzly.bakeryinventorymanagement.R
import com.fxzly.bakeryinventorymanagement.ui.component.BottomBarNav
import com.fxzly.bakeryinventorymanagement.ui.viewfactory.AppViewModelProvider
import com.fxzly.bakeryinventorymanagement.ui.viewmodel.ProfileViewModel
import java.io.File
import java.io.FileOutputStream
import java.io.IOException

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(navController: NavHostController, userID: Int) {
    val viewModel: ProfileViewModel = viewModel(factory = AppViewModelProvider.Factory)
    val user by viewModel.user.collectAsStateWithLifecycle()
    var selectedImageUri by remember { mutableStateOf<Uri?>(null) }
    val context = LocalContext.current

    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        selectedImageUri = uri
        uri?.let {
            val imagePath = saveImageToInternalStorage(context, it)
            imagePath?.let { path ->
                viewModel.updateUserImage(userID, path)
            }
        }
    }

    LaunchedEffect(userID) {
        viewModel.fetchUser(userID)
    }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(title = { Text("Profile") })
        },
        bottomBar = { BottomBarNav(navController) }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(contentAlignment = Alignment.TopEnd) {
                val imageUri = selectedImageUri ?: Uri.parse(
                    user?.user_img?.takeIf { it.isNotEmpty() }
                        ?: "android.resource://${context.packageName}/${R.drawable.avatar}"
                )
                Image(
                    painter = rememberAsyncImagePainter(model = imageUri),
                    contentDescription = "Avatar",
                    modifier = Modifier.size(150.dp)
                        .clip(CircleShape),
                    contentScale = ContentScale.Crop // Fill the entire area
                )
                IconButton(onClick = { launcher.launch("image/*") }) {
                    Icon(imageVector = Icons.Filled.Edit, contentDescription = "Edit Avatar")
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            user?.let {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    elevation = CardDefaults.cardElevation(4.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = Color(0xFFF3D0D7),
                        contentColor = MaterialTheme.colorScheme.onPrimaryContainer
                    )
                ) {
                    Column(
                        modifier = Modifier
                            .padding(16.dp)
                            .fillMaxWidth()
                    ) {
                        Row {
                            Spacer(modifier = Modifier.height(40.dp))
                            Text(
                                text = "Username: ",
                                style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Bold)
                            )
                            Text(text = it.username, style = MaterialTheme.typography.bodyLarge)
                        }
                        Row {
                            Spacer(modifier = Modifier.height(40.dp))
                            Text(
                                text = "Full Name: ",
                                style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Bold)
                            )
                            Text(text = "${it.first_name} ${it.last_name}", style = MaterialTheme.typography.bodyLarge)
                        }
                        Row {
                            Spacer(modifier = Modifier.height(40.dp))
                            Text(
                                text = "Role: ",
                                style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Bold)
                            )
                            Text(text = it.role, style = MaterialTheme.typography.bodyLarge)
                        }
                        Row {
                            Text(
                                text = "Contact No: ",
                                style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Bold)
                            )
                            Text(text = it.contact_no, style = MaterialTheme.typography.bodyLarge)
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                Spacer(modifier = Modifier.width(18.dp))
                Button(
                    onClick = {
                        navController.navigate("edit_profile/${user?.userID}")
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFFE6A4B4)
                    ),
                    modifier = Modifier.weight(1f)
                ) {
                    Text("Edit Profile")
                }
                Spacer(modifier = Modifier.width(16.dp))
                Button(
                    onClick = {
                        // Show Toast message
                        Toast.makeText(context, "Goodbye, See You Soon ! \uD83D\uDE14", Toast.LENGTH_SHORT).show()

                        navController.navigate("login")
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFFE6A4B4)
                    ),
                    modifier =Modifier.weight(1f)
                ) {
                    Text("Logout")
                }
                Spacer(modifier = Modifier.width(18.dp))
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