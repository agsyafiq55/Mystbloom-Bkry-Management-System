package com.fxzly.bakeryinventorymanagement.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

private val LightColors = lightColorScheme(
    primary = Color(0xFF2F2F30), // Change to your desired color
    onPrimary = Color.White, // Change to your desired color
    background = Color(0xFFE6D8DC), // Set the background color
    surface = Color(0xFFE6D8DC) // Set the surface color
    // Add other color customizations if needed
)

private val DarkColors = darkColorScheme(
    primary = Color(0xFF2F2F30), // Change to your desired color
    onPrimary = Color.Black, // Change to your desired color
    background = Color(0xFF121212), // Set the background color
    surface = Color(0xFF1E1E1E) // Set the surface color
    // Add other color customizations if needed
)

val CustomTypography = Typography(
    bodyLarge = TextStyle(
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
        color = Color(0xFF2F2F30) // Change this to your desired text color
    ),
    bodyMedium = TextStyle(
        color = Color(0xFF2F2F30) // Change this to your desired text color

    ),
    titleLarge = TextStyle(
        fontWeight = FontWeight.Bold,
        fontSize = 20.sp,
        color = Color(0xFF2F2F30) // Change this to your desired text color
    ),
    titleMedium = TextStyle(
        fontWeight = FontWeight.Bold,
        fontSize = 16.sp,
        color = Color(0xFF2F2F30) // Change this to your desired text color
    ),
    titleSmall = TextStyle(
        color = Color(0xFF2F2F30) // Change this to your desired text color
    ),
    // Add other text styles as needed
)

@Composable
fun BakeryInventoryManagementTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    /*val colors = if (darkTheme) {
        DarkColors
    } else {
        LightColors
    }*/

    val colors = LightColors

    MaterialTheme(
        colorScheme = colors,
        typography = CustomTypography, // Replace with your typography if needed
        content = content
    )
}
