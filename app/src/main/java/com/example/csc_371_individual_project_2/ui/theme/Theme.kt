package com.example.csc_371_individual_project_2.ui.theme

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext

// Palette
private val Indigo = Color(0xFF2B2D42)      // Deep Indigo (Background)
private val Cyan   = Color(0xFF00BCD4)      // Bright Cyan (Primary Accent)
private val Teal   = Color(0xFF26A69A)      // Soft Teal (Secondary Accent)
private val OffWhite = Color(0xFFF1F1F1)    // Light Gray-White (Text and Icons)

val CodeIQColors = darkColorScheme(
    primary = Cyan,
    onPrimary = Color.Black,
    secondary = Teal,
    onSecondary = Color.Black,
    background = Indigo,
    onBackground = OffWhite,
    surface = Color(0xFF1E2030),
    onSurface = OffWhite
)

@Composable
fun CodeIQTheme(content: @Composable () -> Unit){
    MaterialTheme(
        colorScheme = CodeIQColors,
        typography = androidx.compose.material3.Typography(), // Default Material3 Font
        content = content
    )
}