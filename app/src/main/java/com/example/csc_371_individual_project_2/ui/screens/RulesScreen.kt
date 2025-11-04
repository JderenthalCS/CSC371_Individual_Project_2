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
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RulesScreen(
    onStartQuiz: () -> Unit,
    onBack: () -> Unit,
    onOpenHistory: () -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("How the Quiz Works") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.Filled.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { inner ->
        Column(
            modifier = Modifier
                .padding(inner)
                .padding(24.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Text(text = "Rules", style = MaterialTheme.typography.headlineSmall)
            Text(
                text = "• You will answer 5 questions.\n" +
                        "• At least one single-answer multiple choice and one multi-select.\n" +
                        "• You must confirm each answer before it is recorded.\n" +
                        "• A timer will be shown for each question.\n" +
                        "• Your score and history will be saved on this device."
            )
            Button(
                onClick = onStartQuiz,
                modifier = Modifier.fillMaxWidth()
            ) { Text("Start Quiz") }

            OutlinedButton(                        // <-- add this block
                onClick = onOpenHistory,           // opens the History screen
                modifier = Modifier.fillMaxWidth()
            ) { Text("View History") }
        }
    }
}