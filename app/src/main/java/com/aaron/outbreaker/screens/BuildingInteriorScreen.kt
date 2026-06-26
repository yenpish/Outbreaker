package com.aaron.outbreaker.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.border
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun BuildingInteriorScreen(
    buildingName: String,
    investigationPoints: Int,
    onRoomSelected: (String) -> Unit,
    onBack: () -> Unit
) {

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {

        Column(
            modifier = Modifier.fillMaxSize()
        ) {

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {

                Text(
                    text = buildingName.uppercase()
                )

                Text(
                    text = "AP: $investigationPoints"
                )
            }

            Spacer(
                modifier = Modifier.height(16.dp)
            )

            Text(
                text = "Explore Building"
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

                when (buildingName) {

                    "School" -> {

                        RoomBox(
                            roomName = "Classroom",
                            modifier = Modifier
                                .align(Alignment.TopStart)
                                .padding(16.dp),
                            onClick = onRoomSelected
                        )

                        RoomBox(
                            roomName = "Kitchen",
                            modifier = Modifier
                                .align(Alignment.CenterEnd)
                                .padding(16.dp),
                            onClick = onRoomSelected
                        )

                        RoomBox(
                            roomName = "Washroom",
                            modifier = Modifier
                                .align(Alignment.BottomStart)
                                .padding(16.dp),
                            onClick = onRoomSelected
                        )
                    }

                    "Hospital" -> {

                        RoomBox(
                            roomName = "Reception",
                            modifier = Modifier
                                .align(Alignment.TopStart)
                                .padding(16.dp),
                            onClick = onRoomSelected
                        )

                        RoomBox(
                            roomName = "Ward",
                            modifier = Modifier
                                .align(Alignment.Center)
                                .padding(16.dp),
                            onClick = onRoomSelected
                        )

                        RoomBox(
                            roomName = "Records Office",
                            modifier = Modifier
                                .align(Alignment.BottomEnd)
                                .padding(16.dp),
                            onClick = onRoomSelected
                        )
                    }

                    "Market" -> {

                        RoomBox(
                            roomName = "Food Stall",
                            modifier = Modifier
                                .align(Alignment.TopCenter)
                                .padding(16.dp),
                            onClick = onRoomSelected
                        )

                        RoomBox(
                            roomName = "Storage",
                            modifier = Modifier
                                .align(Alignment.CenterStart)
                                .padding(16.dp),
                            onClick = onRoomSelected
                        )

                        RoomBox(
                            roomName = "Entrance",
                            modifier = Modifier
                                .align(Alignment.BottomCenter)
                                .padding(16.dp),
                            onClick = onRoomSelected
                        )
                    }



                    else -> {

                        RoomBox(
                            roomName = "Stage Area",
                            modifier = Modifier
                                .align(Alignment.TopCenter)
                                .padding(16.dp),
                            onClick = onRoomSelected
                        )

                        RoomBox(
                            roomName = "Food Booth",
                            modifier = Modifier
                                .align(Alignment.CenterStart)
                                .padding(16.dp),
                            onClick = onRoomSelected
                        )

                        RoomBox(
                            roomName = "Seating Area",
                            modifier = Modifier
                                .align(Alignment.BottomEnd)
                                .padding(16.dp),
                            onClick = onRoomSelected
                        )
                    }
                }
            }

            Spacer(
                modifier = Modifier.height(16.dp)
            )

            Button(
                onClick = onBack
            ) {
                Text("Back")
            }
        }
    }
}

@Composable
private fun RoomBox(
    roomName: String,
    modifier: Modifier,
    onClick: (String) -> Unit
) {

    Card(
        modifier = modifier
            .size(
                width = 130.dp,
                height = 90.dp
            )
            .clickable {
                onClick(roomName)
            },
        border = BorderStroke(
            2.dp,
            MaterialTheme.colorScheme.onBackground
        )
    ) {

        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {

            Text(
                text = roomName
            )
        }
    }
}