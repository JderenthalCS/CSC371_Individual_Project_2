package com.example.csc_371_individual_project_2.ui.screens

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.scale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay
import com.example.csc_371_individual_project_2.R


/**
 * SPLASHSCREEN
 *  - Shows CodeIQ logo w/ short animation effect
 *  - Calls onFinished() so MainActivity can navigate to AuthChoice
 */
@Composable
fun SplashScreen(
    onFinished: () -> Unit
) {
    var start by remember { mutableStateOf(false)} // Triggers Animation

    // Animation: Logo Scaling
    val scale by animateFloatAsState(
        targetValue = if (start) 1f else 0.85f, // Size when animation starts
        animationSpec = tween(900, easing = FastOutSlowInEasing), // 900ms smooth curve
        label = "splash-scale"
    )

    // Animation: Element Opacity
    val alpha by animateFloatAsState(
        targetValue = if (start) 1f else 0f, // Fades from invisible to visible
        animationSpec = tween(900), // 900ms easing fade
        label = "splash-alpha"
    )

    // Runs side effects on first display
    LaunchedEffect(Unit){
        start = true // Starts both animations
        delay(3000) // Splash visible for 3 seconds
        onFinished()  // Navigates nextScreen
    }

    // Design

    Box(
        modifier = Modifier
            .fillMaxSize() // Makes box take full WxH
            .background(MaterialTheme.colorScheme.background), //  Background Color
        contentAlignment = Alignment.Center // Centers content
    ) {
        Column( // Vertical Arrangement: Logo x Text
            horizontalAlignment = Alignment.CenterHorizontally // Centers content
        ){
            Image( // Displays CodeIQ logo
                painter = painterResource(id = R.drawable.codeiq_logo),
                contentDescription = "CodeIQ Logo",
                modifier = Modifier
                    .size(420.dp) // Logo Size
                    .scale(scale)       // Scaling Animation
                    .alpha(alpha)       // Fade-in Animation
            )

//            Spacer(modifier = Modifier.height(16.dp))    // Adds Space below logo
//
//            Text(
//                text = "CodeIQ",
//                color = MaterialTheme.colorScheme.onBackground,
//                fontSize = 28.sp,
//                fontWeight = FontWeight.SemiBold,
//                modifier = Modifier.alpha(alpha)
//
//
//            )
//            Spacer(modifier = Modifier.height(4.dp))    // Adds Space below title
//
//            Text(
//                text = "Computer Science & DSA Trivia",
//                color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.8f),
//                fontSize = 14.sp,
//                modifier = Modifier.alpha(alpha)
//            )
        }
    }
}