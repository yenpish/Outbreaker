package com.aaron.outbreaker.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.aaron.outbreaker.GameState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll

@Composable
fun OutcomeScreen(
    gameState: GameState,
    onNextDay: () -> Unit
) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(
                rememberScrollState()
            )
            .padding(24.dp)
    ) {

        Text(
            text = "WEEKLY REPORT",
            fontSize = 28.sp
        )

        Spacer(modifier = Modifier.height(20.dp))

        Card(
            modifier = Modifier.fillMaxWidth()
        ) {

            Column(
                modifier = Modifier.padding(16.dp)
            ) {

                Text(
                    "📋 CASE REPORT",
                    fontSize = 20.sp
                )

                Spacer(
                    modifier = Modifier.height(12.dp)
                )

                Text(
                    gameState.latestOutcomeReport
                )
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        Text("Infection: ${gameState.infection}")
        Text("Trust: ${gameState.trust}")
        Text("Budget: ${gameState.budget}")

        Spacer(modifier = Modifier.height(24.dp))

        Button(
            onClick = onNextDay
        ) {
            Text("Begin Next Week")
        }
    }
}