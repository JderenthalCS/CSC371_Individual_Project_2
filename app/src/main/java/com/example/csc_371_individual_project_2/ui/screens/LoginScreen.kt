package com.example.csc_371_individual_project_2.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.material3.TopAppBar
import androidx.compose.ui.platform.LocalContext
import com.example.csc_371_individual_project_2.data.UserPrefs


/**
 * Login
 *  - Includes Email & Password Fields w/ Validation checks (regex)
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginScreen(
    onLoggedIn: () -> Unit,
    onBack: () -> Unit
) {
    var email by remember {mutableStateOf("")}      // Holds email input
    var password by remember {mutableStateOf("")}   // Holds password input

    var emailError by remember { mutableStateOf<String?>(null) }
    var passwordError by remember { mutableStateOf<String?>(null) }

    // User Prefs
    var loginError by remember { mutableStateOf<String?>(null) }
    val context = LocalContext.current


    Scaffold(
        topBar = {TopAppBar(title = { Text("Log In") },

            // TopBar Back Button!!
            navigationIcon = {
            IconButton(onClick = onBack){
                Icon(Icons.Filled.ArrowBack, contentDescription = "Back")
            }
        }) }
    ) { inner ->
        Column(
            modifier = Modifier
                .padding(inner)
                .padding(24.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            OutlinedTextField(
                value = email,
                onValueChange = { email = it; emailError = null },
                label = { Text("Email")},
                isError = emailError != null,
                singleLine = true,
                modifier = Modifier.fillMaxWidth()
            )
            if (emailError != null) Text(emailError!!, color = MaterialTheme.colorScheme.error)

            OutlinedTextField( // Password input
                value = password,
                onValueChange = { password = it; passwordError = null },
                label = { Text("Password") },
                isError = passwordError != null,
                singleLine = true,
                visualTransformation = PasswordVisualTransformation(),
                modifier = Modifier.fillMaxWidth()
            )
            if (passwordError != null) Text(passwordError!!, color = MaterialTheme.colorScheme.error) // Displays Password Error
            if (loginError != null) Text(loginError!!, color = MaterialTheme.colorScheme.error) // Displays Login Error


            Button(
                onClick = {
                    var valid = true
                    val emailRegex = Regex("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$")
                    if (!emailRegex.matches(email)) { emailError = "Invalid email address."; valid = false }
                    if (password.length < 6) { passwordError = "Password must be at least 6 characters."; valid = false }
                    if (!valid) return@Button

                    val savedEmail = UserPrefs.getEmail(context)
                    val savedPass = UserPrefs.getPassword(context)
                    if (savedEmail == null || savedPass == null) {
                        loginError = "No account found. Please register first."
                    } else if (email == savedEmail && password == savedPass) {
                        loginError = null
                        onLoggedIn()
                    } else {
                        loginError = "Email or password is incorrect."
                    }
                },
                modifier = Modifier.fillMaxWidth()
            ) { Text("Continue") }

        }
    }
}