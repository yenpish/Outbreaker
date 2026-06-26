package com.aaron.outbreaker.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.aaron.outbreaker.GameState
import kotlin.random.Random
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll

@Composable
fun DashboardScreen(
    gameState: GameState,
    onUpdate: (GameState) -> Unit,
    onRestart: () -> Unit,
    onResearch: () -> Unit,
    onDailyReport: (String) -> Unit,
    onMap: () -> Unit,
    onEvidenceBoard: () -> Unit
) {
    val outbreakStatus = when {
        gameState.infection >= 70 -> "🔴 Emergency"
        gameState.infection >= 40 -> "🟡 Concern"
        else -> "🟢 Stable"
    }
    val districtStatus = when {
        gameState.infection >= 70 -> "🔴"
        gameState.infection >= 40 -> "🟡"
        else -> "🟢"
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(24.dp),

        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Text("OUTBREAKER", fontSize = 28.sp)

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            "WEEK ${gameState.day} / 6",
            fontSize = 20.sp
        )

        if (gameState.currentCrisis.isNotBlank()) {

            Spacer(
                modifier = Modifier.height(12.dp)
            )

            Card(
                modifier = Modifier.fillMaxWidth()
            ) {

                Column(
                    modifier = Modifier.padding(16.dp)
                ) {

                    Text(
                        "CURRENT CRISIS"
                    )

                    Spacer(
                        modifier = Modifier.height(8.dp)
                    )

                    Text(
                        gameState.currentCrisis
                    )

                    Spacer(
                        modifier = Modifier.height(4.dp)
                    )

                    Text(
                        gameState.crisisDescription
                    )
                }
            }
        }

        if (gameState.outbreakSeverity > 1) {

            Spacer(
                modifier = Modifier.height(12.dp)
            )

            Card(
                modifier = Modifier.fillMaxWidth()
            ) {

                Column(
                    modifier = Modifier.padding(16.dp)
                ) {

                    Text(
                        "OUTBREAK STATUS"
                    )

                    Spacer(
                        modifier = Modifier.height(8.dp)
                    )

                    val severityText =
                        when {

                            gameState.outbreakSeverity >= 3 ->
                                "Critical"

                            gameState.outbreakSeverity == 2 ->
                                "Escalating"

                            else ->
                                "Stable"
                        }

                    Text(severityText)

                    Spacer(
                        modifier = Modifier.height(4.dp)
                    )

                    Text(
                        "Severity ${gameState.outbreakSeverity}"
                    )
                }
            }
        }

        Text("Control the outbreak for 6 weeks.", modifier = Modifier.padding(top = 8.dp))
        Spacer(modifier = Modifier.height(16.dp))
        Card(
            modifier = Modifier.fillMaxWidth()
        ) {

            Column(
                modifier = Modifier.padding(16.dp)
            ) {

                Text(
                    "COMMAND CENTER",
                    fontSize = 20.sp
                )

                Spacer(
                    modifier = Modifier.height(8.dp)
                )

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text("🦠 Infection: ${gameState.infection}%")
                    Text("🤝 Trust: ${gameState.trust}%")
                }

                Spacer(modifier = Modifier.height(4.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text("💰 Budget: ${gameState.budget}")
                }

                Spacer(modifier = Modifier.height(4.dp))

                Text("🏥 Hospital Capacity: ${gameState.hospital}%")

                Text(
                    "Investigation Points: ${gameState.investigationPoints}"
                )

                Spacer(
                    modifier = Modifier.height(8.dp)
                )

                Text(outbreakStatus)
            }
        }

        Spacer(modifier = Modifier.height(12.dp))

        Spacer(modifier = Modifier.height(16.dp))
        Text(gameState.eventMessage)

        if (gameState.gameOver) {
            Spacer(modifier = Modifier.height(12.dp))
            Text("GAME OVER")
            if (gameState.infection >= 100) Text("The outbreak became uncontrollable.")
            if (gameState.hospital <= 0) Text("The healthcare system collapsed.")
            Text("You survived ${gameState.day} weeks.")
        }

        if (gameState.gameWon) {
            Spacer(modifier = Modifier.height(12.dp))
            Text("OUTBREAK CONTROLLED")
            Text("You successfully protected the city for 6 weeks.")
            val rating = when {
                gameState.hospital >= 70 -> "Excellent Response"
                gameState.hospital >= 40 -> "Good Response"
                else -> "Poor Response"
            }
            Text("Final Rating: $rating")
        }

        if (gameState.infection >= 50) Text("⚠ Hospital under pressure!")
        if (gameState.hospital <= 25) Text("🚨 Hospital system nearing collapse!")

        Spacer(modifier = Modifier.height(12.dp))

        Button(onClick = onResearch) {
            Text("Research Center")
        }

        Spacer(modifier = Modifier.height(6.dp))

        Button(onClick = onEvidenceBoard) {
            Text("Evidence Board")
        }

        Spacer(modifier = Modifier.height(6.dp))

        Button(onClick = onMap) {
            Text("Investigation Map")
        }

        Spacer(modifier = Modifier.height(12.dp))

        Button(onClick = onRestart) {
            Text("Restart")
        }

        Spacer(modifier = Modifier.height(20.dp))

        Text(
            text = "By AARON HAFIDZ BIN MAZRAIN (22111606)",
            style = MaterialTheme.typography.bodySmall
        )
    }

}