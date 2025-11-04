package com.example.csc_371_individual_project_2.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
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
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.platform.LocalContext
import com.example.csc_371_individual_project_2.data.UserPrefs

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegisterScreen(
    onRegistered: () -> Unit,
    onBack: () -> Unit
){
    var firstName by remember {mutableStateOf("")}
    var lastName by remember {mutableStateOf("")}
    var dob by remember {mutableStateOf("")}
    var email by remember {mutableStateOf("")}
    var password by remember {mutableStateOf("")}

    // Validation / Error Handling
    var firstNameError by remember {mutableStateOf<String?>(null)}
    var lastNameError by remember { mutableStateOf<String?>(null) }
    var dobError by remember { mutableStateOf<String?>(null) }
    var emailError by remember {mutableStateOf<String?>(null)}
    var passwordError by remember {mutableStateOf<String?>(null)}

    // User Prefs
    val context = LocalContext.current

    Scaffold(
        topBar = { TopAppBar(title = { Text("Registration") },

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
                value = firstName,
                onValueChange = { firstName = it; firstNameError = null },
                label = { Text("First name") },
                singleLine = true,
                modifier = Modifier.fillMaxWidth()
            )
            if (firstNameError != null) Text(firstNameError!!, color = MaterialTheme.colorScheme.error)

            OutlinedTextField(
                value = lastName,
                onValueChange = { lastName = it; lastNameError = null },
                label = { Text("Family name") },
                singleLine = true,
                modifier = Modifier.fillMaxWidth()
            )
            if (lastNameError != null) Text(lastNameError!!, color = MaterialTheme.colorScheme.error)

            OutlinedTextField(
                value = dob,
                onValueChange = { dob = it; dobError = null },
                label = { Text("Date of birth (MM/DD/YYYY)") },
                singleLine = true,
                modifier = Modifier.fillMaxWidth()
            )
            if (dobError != null) Text(dobError!!, color = MaterialTheme.colorScheme.error)

            OutlinedTextField(
                value = email,
                onValueChange = { email = it; emailError = null },
                label = { Text("Email") },
                singleLine = true,
                modifier = Modifier.fillMaxWidth()
            )
            if (emailError != null) Text(emailError!!, color = MaterialTheme.colorScheme.error)

            OutlinedTextField(
                value = password,
                onValueChange = { password = it; passwordError = null},
                label = { Text("Password") },
                singleLine = true,
                visualTransformation = PasswordVisualTransformation(),
                modifier = Modifier.fillMaxWidth()
            )
            if (passwordError != null) Text(passwordError!!, color = MaterialTheme.colorScheme.error)

            Button(
                onClick = {
                    // Validation logic w/ Regex
                    var valid = true

                    if (firstName.length < 3 || firstName.length > 30) {
                        firstNameError = "First name must be between 3 and 30 characters."
                        valid = false
                    }

                    val lastNameRegex = Regex("^[A-Za-z]+$")
                    if (!lastNameRegex.matches(lastName)) {
                        lastNameError = "Family name must contain only letters."
                        valid = false
                    }

                    val dobRegex = Regex("^(0[1-9]|1[0-2])/([0-2][0-9]|3[01])/([0-9]{4})$")
                    if (!dobRegex.matches(dob)) {
                        dobError = "Invalid date format. Use MM/DD/YYYY."
                        valid = false
                    }

                    val emailRegex = Regex("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$")
                    if (!emailRegex.matches(email)) {
                        emailError = "Invalid email address."
                        valid = false
                    }

                    if (password.length < 6) {
                        passwordError = "Password must be at least 6 characters long."
                        valid = false
                    }

                    if (valid) {
                        UserPrefs.saveUser(context, firstName, email, password)
                        onRegistered()
                    }
                },
                modifier = Modifier.fillMaxWidth()
            ) { Text("Register") }

        }
    }
}