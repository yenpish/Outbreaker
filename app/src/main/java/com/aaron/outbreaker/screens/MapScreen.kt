package com.aaron.outbreaker.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.Button
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.runtime.*
import androidx.compose.foundation.Image
import androidx.compose.ui.res.painterResource
import com.aaron.outbreaker.R

@Composable
fun MapScreen(
    investigationPoints: Int,
    onSchool: () -> Unit,
    onMarket: () -> Unit,
    onHospital: () -> Unit,
    onFestival: () -> Unit,
    onBack: () -> Unit
) {

    var selectedLocation by remember {
        mutableStateOf<String?>(null)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {

            Text(
                text = "CITY MAP"
            )

            Text(
                text = "AP: $investigationPoints"
            )
        }

        Spacer(
            modifier = Modifier.height(12.dp)
        )

        Text(
            text = "Select Investigation Location"
        )

        Spacer(
            modifier = Modifier.height(16.dp)
        )

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .border(
                    width = 2.dp,
                    color = MaterialTheme.colorScheme.onBackground
                )
        ) {

            LocationNode(
                name = "SCHOOL",
                modifier = Modifier
                    .align(Alignment.TopCenter)
                    .padding(top = 24.dp),
                onClick = {
                    selectedLocation = "SCHOOL"
                }
            )

            LocationNode(
                name = "MARKET",
                modifier = Modifier
                    .align(Alignment.CenterStart)
                    .padding(start = 16.dp),
                onClick = {
                    selectedLocation = "MARKET"
                }
            )

            LocationNode(
                name = "HOSPITAL",
                modifier = Modifier
                    .align(Alignment.CenterEnd)
                    .padding(end = 16.dp),
                onClick = {
                    selectedLocation = "HOSPITAL"
                }
            )

            LocationNode(
                name = "FESTIVAL",
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .padding(bottom = 24.dp),
                onClick = {
                    selectedLocation = "FESTIVAL"
                }
            )

            if (selectedLocation == null) {

                Image(
                    painter = painterResource(R.drawable.north),
                    contentDescription = "Detective",
                    modifier = Modifier
                        .align(Alignment.BottomStart)
                        .padding(start = 24.dp, bottom = 24.dp)
                        .size(72.dp)
                )
            }

            if (selectedLocation == "SCHOOL") {

                Image(
                    painter = painterResource(R.drawable.north),
                    contentDescription = "Detective",
                    modifier = Modifier
                        .align(Alignment.TopCenter)
                        .padding(top = 90.dp)
                        .size(72.dp)
                )
            }

            if (selectedLocation == "MARKET") {

                Image(
                    painter = painterResource(R.drawable.west),
                    contentDescription = "Detective",
                    modifier = Modifier
                        .align(Alignment.CenterStart)
                        .padding(start = 110.dp)
                        .size(72.dp)
                )
            }

            if (selectedLocation == "HOSPITAL") {

                Image(
                    painter = painterResource(R.drawable.east),
                    contentDescription = "Detective",
                    modifier = Modifier
                        .align(Alignment.CenterEnd)
                        .padding(end = 110.dp)
                        .size(72.dp)
                )
            }

            if (selectedLocation == "FESTIVAL") {

                Image(
                    painter = painterResource(R.drawable.south),
                    contentDescription = "Detective",
                    modifier = Modifier
                        .align(Alignment.BottomCenter)
                        .padding(bottom = 90.dp)
                        .size(72.dp)
                )
            }
        }

        Spacer(
            modifier = Modifier.height(16.dp)
        )

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {

            Button(
                onClick = onBack
            ) {
                Text("Back")
            }

            Text(
                text = selectedLocation ?: ""
            )

            Button(
                enabled = selectedLocation != null,
                onClick = {

                    when (selectedLocation) {

                        "SCHOOL" -> onSchool()

                        "MARKET" -> onMarket()

                        "HOSPITAL" -> onHospital()

                        "FESTIVAL" -> onFestival()
                    }
                }
            ) {
                Text("Enter >")
            }
        }
    }
}

@Composable
private fun LocationNode(
    name: String,
    modifier: Modifier,
    onClick: () -> Unit
) {

    Card(
        modifier = modifier
            .size(
                width = 110.dp,
                height = 70.dp
            )
            .clickable {
                onClick()
            },
        border = BorderStroke(
            3.dp,
            MaterialTheme.colorScheme.onBackground
        )
    ) {

        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {

            Text(name)
        }
    }
}