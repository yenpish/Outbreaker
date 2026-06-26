package com.aaron.outbreaker.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun DailyReportScreen(
    reportText: String,
    onContinue: () -> Unit
) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),

        horizontalAlignment = Alignment.Start
    ) {

        Text(
            text = "Daily Report",
            fontSize = 28.sp
        )

        Spacer(
            modifier = Modifier.height(20.dp)
        )

        Text(reportText)

        Spacer(
            modifier = Modifier.height(24.dp)
        )

        Button(
            onClick = onContinue
        ) {
            Text("Continue")
        }
    }
}