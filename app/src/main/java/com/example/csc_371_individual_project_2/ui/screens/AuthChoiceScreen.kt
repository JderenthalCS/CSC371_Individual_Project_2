package com.example.csc_371_individual_project_2.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun AuthChoiceScreen(
    onLogin: () -> Unit,  // Callback for when user taps Login
    onRegister: () -> Unit // Callback for when user taps Register
) {
    Box( // Root Container
        modifier = Modifier
            .fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        AnimatedGradientBackground() // Calls Animation
        Card( // Material card to hold the screen content
            modifier = Modifier
                .padding(24.dp),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surface
            ),
            elevation = CardDefaults.cardElevation(8.dp) // Applies a small shadow
        ) {
            Column( // Vertical stack for the card’s internal content
                modifier = Modifier
                    .padding(24.dp)
                    .widthIn(min = 280.dp, max = 420.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text( // Displays title
                    text = "Welcome to CodeIQ",
                    style = MaterialTheme.typography.headlineSmall
                )
                Text( // Displays subtitle
                    text = "Sharpen your logic, one question at a time.",
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.8f)
                )
                Button( // Button for Login
                    onClick = onLogin,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(text = "Log In")
                }
                OutlinedButton( // Button for Register
                    onClick = onRegister,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(text = "Register")
                }
            }
        }
    }
}