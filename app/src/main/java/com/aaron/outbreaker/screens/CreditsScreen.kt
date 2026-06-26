package com.aaron.outbreaker.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun CreditsScreen(
    onBack: () -> Unit
) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),

        horizontalAlignment = Alignment.Start
    ) {

        Text(
            text = "Credits",
            fontSize = 28.sp
        )

        Spacer(
            modifier = Modifier.height(16.dp)
        )

        Text(
            "Created by Aaron Hafidz"
        )

        Text(
            "Matric No: 22111606"
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