package com.example.csc_371_individual_project_2.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.example.csc_371_individual_project_2.data.UserPrefs
import androidx.compose.material3.Divider
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale


/**
 * RESULTS
 *  - Displays results with accompanying feedback quote
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ResultScreen(
    score: Int,
    total: Int,
    onHome: () -> Unit,
    onRetry: () -> Unit
) {

    val context = LocalContext.current
    val high = remember { UserPrefs.getHighScore(context)}
    val history = remember {UserPrefs.getHistory(context).takeLast(5).reversed()}


    Scaffold(
        topBar = { TopAppBar(title = { Text("Quiz Results") }) }
    ) { inner ->
        Column(
            modifier = Modifier
                .padding(inner)
                .padding(24.dp),
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            Text(
                text = "You scored $score out of $total!",
                style = MaterialTheme.typography.headlineSmall
            )

            // Progress Indicator
            LinearProgressIndicator(
                progress = { score / total.toFloat() },
                modifier = Modifier.fillMaxWidth()
            )

            // Feedback
            val feedback = when {
                score == total -> "Perfect score!"
                score >= total * 0.7 -> "Great job!"
                score >= total * 0.4 -> "Not bad!"
                else -> "Keep practicing!"
            }

            Text(
                text = feedback,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.8f)
            )


            Text(
                text = "High score: $high",
                style = MaterialTheme.typography.titleMedium
            )

            Divider()

            Text("Recent attempts", style = MaterialTheme.typography.titleSmall)

            if (history.isEmpty()) {
                Text("No history yet.")
            } else {
                history.forEach { a ->
                    val whenStr = SimpleDateFormat("MMM d, yyyy h:mm a", Locale.getDefault())
                        .format(Date(a.timeMillis))
                    Text("• $whenStr — ${a.score}/${a.total}")
                }
            }

            // Retry Button
            Button(
                onClick = onRetry,
                modifier = Modifier.fillMaxWidth()
            ) { Text("Retry Quiz") }

            // Return Button
            OutlinedButton(
                onClick = onHome,
                modifier = Modifier.fillMaxWidth()
            ) { Text("Return Home") }
        }
    }
}