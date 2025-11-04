package com.example.csc_371_individual_project_2.ui.screens

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color

@Composable
fun AnimatedGradientBackground(
    modifier: Modifier = Modifier,
) {
    val infinite = rememberInfiniteTransition(label = "bg") // Looping Animation
    val phase by infinite.animateFloat( // Animated from 0f -> 1f
        initialValue = 0f, // Start
        targetValue = 1f, // End
        animationSpec = infiniteRepeatable( // Forever Repeat
            animation = tween(8000, easing = LinearEasing), // 8s per cycle
            repeatMode = RepeatMode.Reverse // Reverses at end of loop
        ),
        label = "phase"
    )

    // Colors
    val c1 = Color(0xFF2B2D42)
    val c2 = Color(0xFF00BCD4).copy(alpha = 0.20f)
    val c3 = Color(0xFF26A69A).copy(alpha = 0.18f)
    val c4 = Color(0xFF000000)

    // Calculate moving gradient endpoints
    val margin = 600f // Extra span beyond screen edges to avoid snapping
    val start = Offset(x = -margin + 1200f * phase, y = -margin) // Moves left→right then reverses
    val end = Offset(x = margin - 1200f * phase, y = margin) // Opposite side for diagonal feel

    // Linear Gradient
    val brush = Brush.linearGradient(
        colors = listOf(c1, c2, c3, c4),
        start = start,
        end = end
    )

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(brush)
    )
}