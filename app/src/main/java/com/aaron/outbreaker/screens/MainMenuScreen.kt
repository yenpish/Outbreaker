package com.aaron.outbreaker.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun MainMenuScreen(
    onStart: () -> Unit,
    onHowToPlay: () -> Unit,
    onCredits: () -> Unit
) {

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

        Text(
            text = "OUTBREAKER",
            fontSize = 32.sp
        )

        Spacer(modifier = Modifier.height(20.dp))

        Button(onClick = onStart) {
            Text("Start Game")
        }

        Spacer(modifier = Modifier.height(12.dp))

        Button(onClick = onHowToPlay) {
            Text("How To Play")
        }

        Spacer(modifier = Modifier.height(12.dp))

        Button(onClick = onCredits) {
            Text("Credits")
        }
    }
}