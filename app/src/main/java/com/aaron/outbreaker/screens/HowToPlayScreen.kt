package com.aaron.outbreaker.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun HowToPlayScreen(
    onBack: () -> Unit
) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),

        horizontalAlignment = Alignment.Start
    ) {

        Text(
            "How to Play",
            fontSize = 28.sp
        )

        Spacer(
            modifier = Modifier.height(16.dp)
        )

        Text(
            "Goal: Control the outbreak over 6 weeks by investigating clues, identifying the correct cause, and choosing effective public health interventions."
        )

        Spacer(
            modifier = Modifier.height(12.dp)
        )

        Text(
            "1. Visit locations using the Investigation Map."
        )

        Text(
            "2. Enter buildings and investigate clues using Investigation Points (AP)."
        )

        Text(
            "3. Review collected evidence in the Evidence Board."
        )

        Text(
            "4. Determine the most likely outbreak cause:"
        )

        Text(
            "- Food Contamination"
        )

        Text(
            "- Poor Hygiene"
        )

        Text(
            "- Crowded Gathering"
        )

        Text(
            "- Ongoing Transmission"
        )

        Spacer(
            modifier = Modifier.height(12.dp)
        )

        Text(
            "5. Spend budget on research and public health interventions."
        )

        Text(
            "6. Monitor Infection, Trust, Hospital Capacity, and Preparedness."
        )

        Text(
            "7. Survive all 6 weeks while keeping the outbreak under control."
        )

        Spacer(
            modifier = Modifier.height(20.dp)
        )

        Button(
            onClick = onBack
        ) {
            Text("Back")
        }

    }
}