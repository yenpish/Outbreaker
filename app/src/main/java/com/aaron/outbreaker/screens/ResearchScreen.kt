package com.aaron.outbreaker.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.aaron.outbreaker.GameState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.text.font.FontWeight

@Composable
fun ResearchScreen(
    gameState: GameState,
    onUpdate: (GameState) -> Unit,
    onBack: () -> Unit
) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(24.dp),

        horizontalAlignment = Alignment.Start
    ) {

        Text(
            text = "Research Center",
            fontSize = 28.sp
        )

        Spacer(modifier = Modifier.height(20.dp))

        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(16.dp)
        ) {
            Text(
                text = "💰 Research Budget: ${gameState.budget}",
                modifier = Modifier.padding(16.dp)
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(16.dp)
        ) {

            Column(
                modifier = Modifier.padding(16.dp)
            ) {

                Text(
                    "🧪 RAPID TESTING",
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text("Cost: 25")

                Text(
                    "Mass Testing becomes more effective."
                )

                Spacer(modifier = Modifier.height(12.dp))

                if (!gameState.rapidTesting) {

                    Button(
                        modifier = Modifier.fillMaxWidth(),
                        onClick = {

                            if (gameState.budget >= 25) {

                                onUpdate(
                                    gameState.copy(
                                        budget = gameState.budget - 25,
                                        rapidTesting = true
                                    )
                                )
                            }
                        }
                    ) {
                        Text("Research")
                    }

                } else {

                    Text(
                        "✓ Researched",
                        color = MaterialTheme.colorScheme.secondary,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(16.dp)
        ) {

            Column(
                modifier = Modifier.padding(16.dp)
            ) {

                Text(
                    "🧼 SANITATION INITIATIVE",
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text("Cost: 25")

                Text(
                    "Hygiene Program becomes more effective."
                )

                Spacer(modifier = Modifier.height(12.dp))

                if (!gameState.sanitationInitiative) {

                    Button(
                        modifier = Modifier.fillMaxWidth(),
                        onClick = {

                            if (gameState.budget >= 25) {

                                onUpdate(
                                    gameState.copy(
                                        budget = gameState.budget - 25,
                                        sanitationInitiative = true
                                    )
                                )
                            }
                        }
                    ) {
                        Text("Research")
                    }

                } else {

                    Text(
                        "✓ Researched",
                        color = MaterialTheme.colorScheme.secondary,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(16.dp)
        ) {

            Column(
                modifier = Modifier.padding(16.dp)
            ) {

                Text(
                    "📢 PUBLIC AWARENESS NETWORK",
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text("Cost: 25")

                Text(
                    "Awareness Campaign becomes more effective."
                )

                Spacer(modifier = Modifier.height(12.dp))

                if (!gameState.publicAwarenessNetwork) {

                    Button(
                        modifier = Modifier.fillMaxWidth(),
                        onClick = {

                            if (gameState.budget >= 25) {

                                onUpdate(
                                    gameState.copy(
                                        budget = gameState.budget - 25,
                                        publicAwarenessNetwork = true
                                    )
                                )
                            }
                        }
                    ) {
                        Text("Research")
                    }

                } else {

                    Text(
                        "✓ Researched",
                        color = MaterialTheme.colorScheme.secondary,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(16.dp)
        ) {

            Column(
                modifier = Modifier.padding(16.dp)
            ) {

                Text(
                    "📋 EMERGENCY PLANNING",
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text("Cost: 30")

                Text(
                    "Gain 1 additional Operations Point."
                )

                Spacer(modifier = Modifier.height(12.dp))

                if (!gameState.emergencyPlanning) {

                    Button(
                        modifier = Modifier.fillMaxWidth(),
                        onClick = {

                            if (gameState.budget >= 30) {

                                onUpdate(
                                    gameState.copy(
                                        budget = gameState.budget - 30,
                                        emergencyPlanning = true
                                    )
                                )
                            }
                        }
                    ) {
                        Text("Research")
                    }

                } else {

                    Text(
                        "✓ Researched",
                        color = MaterialTheme.colorScheme.secondary,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        Button(
            modifier = Modifier.fillMaxWidth(),
            onClick = onBack
        ) {
            Text("Back")
        }
    }
}