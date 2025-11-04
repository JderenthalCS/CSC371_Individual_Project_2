package com.example.csc_371_individual_project_2.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.selection.toggleable
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.unit.dp
import androidx.compose.ui.platform.LocalContext
import com.example.csc_371_individual_project_2.data.UserPrefs
import kotlinx.coroutines.delay



sealed class Q {
    data class Single(val text: String, val options: List<String>, val correctIndex: Int) : Q()
    data class Multi(val text: String, val options: List<String>, val correctIndices: Set<Int>) : Q()
}
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun QuizScreen(
    onFinished: (score: Int, total: Int) -> Unit
)
{
    //UserPrefs
    val context = LocalContext.current

    // 5 questions (at least one single-choice and one multi-select)
    val questions = remember {
        listOf<Q>(
            Q.Single(
                text = "What is the time complexity of binary search on a sorted array?",
                options = listOf("O(n)", "O(log n)", "O(n log n)", "O(1)"),
                correctIndex = 1
            ),
            Q.Single(
                text = "Which data structure uses FIFO ordering?",
                options = listOf("Stack", "Queue", "Tree", "Graph"),
                correctIndex = 1
            ),
            Q.Multi( // <-- multi-select
                text = "Which sorting algorithms are stable?",
                options = listOf("Merge Sort", "Quick Sort", "Insertion Sort", "Heap Sort"),
                correctIndices = setOf(0, 2)
            ),
            Q.Single(
                text = "Dijkstra’s algorithm requires edges to be:",
                options = listOf("All positive weights", "All negative weights", "Unweighted only", "Directed only"),
                correctIndex = 0
            ),
            Q.Single(
                text = "Which traversal visits: Root, Left, Right?",
                options = listOf("In-order", "Post-order", "Pre-order", "Level-order"),
                correctIndex = 2
            )
        )
    }

    var idx by remember { mutableStateOf(0) } // Current Question
    var score by remember { mutableStateOf(0) } // Current Count
    var showConfirm by remember { mutableStateOf(false) }

    // Selection State
    var selectedSingle by remember { mutableStateOf(-1) } // Radio Selection
    var selectedMulti by remember { mutableStateOf(setOf<Int>()) } // Checkbox Selections

    // Timer
    val totalSeconds = 20
    var secondsLeft by remember(idx) { mutableStateOf(totalSeconds) }

    fun resetSelectionFor(q: Q) { // Reset when question changes
        when (q) {
            is Q.Single -> { selectedSingle = -1; selectedMulti = emptySet() }
            is Q.Multi -> { selectedSingle = -1; selectedMulti = emptySet() }
        }
    }

    val q = questions[idx] // Current Question

    // Automatic Countdown
    LaunchedEffect(idx) {
        secondsLeft = totalSeconds
        while (secondsLeft > 0) {
            delay(1000)
            secondsLeft--
        }

        val correct = when (q) {
            is Q.Single -> selectedSingle == q.correctIndex
            is Q.Multi -> selectedMulti == q.correctIndices
        }
        if (correct) score += 1
        if (idx < questions.lastIndex) {
            idx += 1
            resetSelectionFor(questions[idx])
        } else {
            UserPrefs.updateHighScoreIfBetter(context, score)
            UserPrefs.appendAttempt(context, score, questions.size)
            onFinished(score, questions.size)
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Question ${idx + 1} of ${questions.size}  •  ${secondsLeft}s") } // Timer Display
            )
        },
        bottomBar = {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                OutlinedButton(
                    onClick = {
                        if (idx == 0) return@OutlinedButton
                        idx -= 1
                        resetSelectionFor(questions[idx])
                    },
                    enabled = idx > 0
                ) { Text("Back") }

                Button(
                    onClick = { showConfirm = true }, // Locking-in answer
                    enabled = when (q) {
                        is Q.Single -> selectedSingle != -1
                        is Q.Multi -> selectedMulti.isNotEmpty()
                    },
                    modifier = Modifier.weight(1f)
                ) { Text(if (idx == questions.lastIndex) "Submit" else "Confirm") }
            }
        }
    ) { inner ->
        Column(
            modifier = Modifier
                .padding(inner)
                .padding(20.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Timer bar
            androidx.compose.material3.LinearProgressIndicator(
                progress = { secondsLeft / totalSeconds.toFloat() },
                modifier = Modifier.fillMaxWidth()
            )

            Text(text = when (q) { // question text
                is Q.Single -> q.text
                is Q.Multi -> q.text
            }, style = MaterialTheme.typography.headlineSmall)

            when (q) {
                is Q.Single -> {
                    q.options.forEachIndexed { i, opt ->
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .toggleable(
                                    value = selectedSingle == i,
                                    onValueChange = { selectedSingle = i },
                                    role = Role.RadioButton
                                )
                                .padding(vertical = 6.dp),
                            horizontalArrangement = Arrangement.spacedBy(12.dp)
                        ) {
                            RadioButton(selected = selectedSingle == i, onClick = { selectedSingle = i })
                            Text(opt)
                        }
                    }
                }
                is Q.Multi -> {
                    q.options.forEachIndexed { i, opt ->
                        val checked = selectedMulti.contains(i)
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .toggleable(
                                    value = checked,
                                    onValueChange = {
                                        selectedMulti = if (checked) selectedMulti - i else selectedMulti + i
                                    },
                                    role = Role.Checkbox
                                )
                                .padding(vertical = 6.dp),
                            horizontalArrangement = Arrangement.spacedBy(12.dp)
                        ) {
                            Checkbox(checked = checked, onCheckedChange = {
                                selectedMulti = if (checked) selectedMulti - i else selectedMulti + i
                            })
                            Text(opt)
                        }
                    }
                    Text(
                        text = "Select all that apply",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
                    )
                }
            }
        }
    }

    if (showConfirm) {
        AlertDialog(
            onDismissRequest = { showConfirm = false },
            title = { Text("Confirm answer?") },
            text = { Text("You won't be able to change this answer.") },
            confirmButton = {
                TextButton(onClick = {

                    // Checking Correct
                    val correct = when (q) {
                        is Q.Single -> selectedSingle == q.correctIndex
                        is Q.Multi -> selectedMulti == q.correctIndices
                    }
                    if (correct) score += 1
                    showConfirm = false

                    // Next or Finish Logic
                    if (idx < questions.lastIndex) {
                        idx += 1
                        resetSelectionFor(questions[idx])
                    } else {
                        UserPrefs.updateHighScoreIfBetter(context, score)
                        UserPrefs.appendAttempt(context, score, questions.size)
                        onFinished(score, questions.size)
                    }
                }) { Text("Yes") }
            },
            dismissButton = {
                TextButton(onClick = { showConfirm = false }) { Text("No") }
            }
        )
    }
}