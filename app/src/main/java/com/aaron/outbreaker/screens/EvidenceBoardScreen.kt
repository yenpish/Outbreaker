package com.aaron.outbreaker.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.aaron.outbreaker.GameState
import androidx.compose.ui.Alignment
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable

@Composable
fun EvidenceBoardScreen(
    gameState: GameState,
    onUpdate: (GameState) -> Unit,
    onCommander: () -> Unit,
    onBack: () -> Unit
) {

    var selectedLocation by remember {
        mutableStateOf(gameState.suspectedLocation)
    }

    var selectedCause by remember {
        mutableStateOf(gameState.suspectedCause)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(24.dp)
            .verticalScroll(rememberScrollState())
    ) {

        Text(
            text = "EVIDENCE BOARD",
            fontSize = 28.sp
        )

        Spacer(modifier = Modifier.height(20.dp))

        if (gameState.evidenceByLocation.isEmpty()) {

            Text("No evidence collected yet.")

        } else {

            gameState.evidenceByLocation.forEach { (location, evidenceList) ->

                var expanded by remember {
                    mutableStateOf(false)
                }

                Card(
                    modifier = Modifier.fillMaxWidth()
                ) {

                    Column(
                        modifier = Modifier.padding(16.dp)
                    ) {

                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable {
                                    expanded = !expanded
                                },
                            horizontalArrangement =
                                Arrangement.SpaceBetween
                        ) {

                            Text(
                                text = location,
                                fontSize = 22.sp
                            )

                            Text(
                                text =
                                    if (expanded) "▲"
                                    else "▼"
                            )
                        }

                        val evidenceText =
                            evidenceList.joinToString(" ")

                        if (expanded) {

                            Spacer(
                                modifier = Modifier.height(8.dp)
                            )

                            evidenceList.forEach { evidence ->

                                Text(
                                    text = "• $evidence"
                                )

                                Spacer(
                                    modifier = Modifier.height(4.dp)
                                )
                            }
                        }
                    }
                }

                Spacer(
                    modifier = Modifier.height(12.dp)
                )
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        HorizontalDivider()

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            "Most Likely Cause",
            fontSize = 20.sp
        )

        Text(
            "Based on the evidence gathered, what is the most likely cause of the outbreak?"
        )

        Spacer(modifier = Modifier.height(8.dp))

        val causes = listOf(
            "🧼 Poor Hygiene",
            "👥 Crowded Gathering",
            "🍽️ Food Contamination",
            "😷 Respiratory Spread"
        )

        causes.forEach { cause ->

            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {

                RadioButton(
                    selected =
                        selectedCause == cause,

                    onClick = {
                        selectedCause = cause
                    }
                )

                Text(cause)
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        Button(
            onClick = {

                onUpdate(
                    gameState.copy(
                        suspectedLocation =
                            selectedLocation,

                        suspectedCause =
                            selectedCause
                    )
                )
            }
        ) {
            Text("Submit Hypothesis")
        }

        Spacer(modifier = Modifier.height(8.dp))

        OutlinedButton(
            onClick = {

                selectedCause = ""

                onUpdate(
                    gameState.copy(
                        suspectedCause = ""
                    )
                )
            }
        ) {
            Text("Clear Selection")
        }

        Spacer(modifier = Modifier.height(12.dp))

        if (gameState.suspectedLocation.isNotBlank()) {

            Text(
                "Current Hypothesis:"
            )

            Text(
                gameState.suspectedCause
            )

        }

        Spacer(modifier = Modifier.height(12.dp))

        if (
            gameState.suspectedCause.isNotBlank()
        ) {

            Button(
                onClick = onCommander
            ) {
                Text("Open Command Center")
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        Button(
            onClick = onBack
        ) {
            Text("Back")
        }
    }
}