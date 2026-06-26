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
import androidx.compose.runtime.*
import androidx.compose.foundation.Image
import androidx.compose.ui.res.painterResource
import com.aaron.outbreaker.GameState

import com.aaron.outbreaker.R

data class RoomObject(
    val name: String,
    val investigated: Boolean = false
)

@Composable
fun RoomScreen(
    roomName: String,
    gameState: GameState,
    investigatedObjects: Set<String>,
    onObjectClick: (String) -> Unit,
    onBack: () -> Unit
) {

    var selectedObject by remember(roomName) {
        mutableStateOf<String?>(null)
    }

    var detectivePosition by remember(roomName) {
        mutableStateOf("DEFAULT")
    }

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
                    text = roomName.uppercase()
                )

                Text(
                    text = "AP: ${gameState.investigationPoints}"
                )
            }

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

                if (detectivePosition == "DEFAULT") {

                    Image(
                        painter = painterResource(R.drawable.north),
                        contentDescription = "Detective",
                        modifier = Modifier
                            .align(Alignment.BottomStart)
                            .padding(start = 24.dp, bottom = 24.dp)
                            .size(72.dp)
                    )
                }

                when (roomName) {

                    "Classroom" -> {

                        ObjectBox(
                            name = "Teacher",
                            investigated =
                                investigatedObjects.contains("Teacher"),
                            selected =
                                selectedObject == "Teacher",
                            modifier = Modifier
                                .align(Alignment.TopStart)
                                .padding(20.dp),
                            onClick = {
                                selectedObject = it
                                detectivePosition = it
                            }
                        )

                        ObjectBox(
                            name = "Desk",
                            investigated =
                                investigatedObjects.contains("Desk"),
                            selected =
                                selectedObject == "Desk",
                            modifier = Modifier
                                .align(Alignment.TopEnd)
                                .padding(20.dp),
                            onClick = {
                                selectedObject = it
                                detectivePosition = it
                            }
                        )

                        ObjectBox(
                            name = "Student",
                            investigated =
                                investigatedObjects.contains("Student"),
                            selected =
                                selectedObject == "Student",
                            modifier = Modifier
                                .align(Alignment.BottomEnd)
                                .padding(20.dp),
                            onClick = {
                                selectedObject = it
                                detectivePosition = it
                            }
                        )

                        if (detectivePosition == "Teacher") {

                            Image(
                                painter = painterResource(R.drawable.west),
                                contentDescription = "Detective",
                                modifier = Modifier
                                    .align(Alignment.TopStart)
                                    .padding(start = 100.dp, top = 10.dp)
                                    .size(72.dp)
                            )
                        }

                        if (detectivePosition == "Desk") {

                            Image(
                                painter = painterResource(R.drawable.east),
                                contentDescription = "Detective",
                                modifier = Modifier
                                    .align(Alignment.TopEnd)
                                    .padding(end = 100.dp, top = 10.dp)
                                    .size(72.dp)
                            )
                        }

                        if (detectivePosition == "Student") {

                            Image(
                                painter = painterResource(R.drawable.east),
                                contentDescription = "Detective",
                                modifier = Modifier
                                    .align(Alignment.BottomEnd)
                                    .padding(end = 110.dp, bottom = 20.dp)
                                    .size(72.dp)
                            )
                        }
                    }

                    "Kitchen" -> {

                        ObjectBox(
                            name = "Sink",
                            investigated =
                                investigatedObjects.contains("Sink"),

                            selected =
                                selectedObject == "Sink",
                            modifier = Modifier
                                .align(Alignment.TopStart)
                                .padding(16.dp),
                            onClick = {
                                selectedObject = it
                                detectivePosition = it
                            }
                        )

                        ObjectBox(
                            name = "Trash Bin",
                            investigated =
                                investigatedObjects.contains("Trash Bin"),

                            selected =
                                selectedObject == "Trash Bin",
                            modifier = Modifier
                                .align(Alignment.TopEnd)
                                .padding(20.dp),
                            onClick = {
                                selectedObject = it
                                detectivePosition = it
                            }
                        )

                        ObjectBox(
                            name = "Food Counter",
                            investigated =
                                investigatedObjects.contains("Food Counter"),

                            selected =
                                selectedObject == "Food Counter",
                            modifier = Modifier
                                .align(Alignment.BottomEnd)
                                .padding(20.dp),
                            onClick = {
                                selectedObject = it
                                detectivePosition = it
                            }
                        )

                        if (detectivePosition == "Sink") {

                            Image(
                                painter = painterResource(R.drawable.west),
                                contentDescription = "Detective",
                                modifier = Modifier
                                    .align(Alignment.TopStart)
                                    .padding(start = 100.dp, top = 10.dp)
                                    .size(72.dp)
                            )
                        }

                        if (detectivePosition == "Trash Bin") {

                            Image(
                                painter = painterResource(R.drawable.east),
                                contentDescription = "Detective",
                                modifier = Modifier
                                    .align(Alignment.TopEnd)
                                    .padding(end = 100.dp, top = 10.dp)
                                    .size(72.dp)
                            )
                        }

                        if (detectivePosition == "Food Counter") {

                            Image(
                                painter = painterResource(R.drawable.east),
                                contentDescription = "Detective",
                                modifier = Modifier
                                    .align(Alignment.BottomEnd)
                                    .padding(end = 120.dp, bottom = 20.dp)
                                    .size(72.dp)
                            )
                        }
                    }

                    "Washroom" -> {

                        ObjectBox(
                            name = "Janitor",
                            investigated =
                                investigatedObjects.contains("Janitor"),

                            selected =
                                selectedObject == "Janitor",
                            modifier = Modifier
                                .align(Alignment.TopStart)
                                .padding(16.dp),
                            onClick = {
                                selectedObject = it
                                detectivePosition = it
                            }
                        )

                        ObjectBox(
                            name = "Sink",
                            investigated =
                                investigatedObjects.contains("Sink"),

                            selected =
                                selectedObject == "Sink",
                            modifier = Modifier
                                .align(Alignment.TopEnd)
                                .padding(20.dp),
                            onClick = {
                                selectedObject = it
                                detectivePosition = it
                            }
                        )

                        ObjectBox(
                            name = "Toilet Stall",
                            investigated =
                                investigatedObjects.contains("Toilet Stall"),

                            selected =
                                selectedObject == "Toilet Stall",
                            modifier = Modifier
                                .align(Alignment.BottomEnd)
                                .padding(16.dp),
                            onClick = {
                                selectedObject = it
                                detectivePosition = it
                            }
                        )

                        if (detectivePosition == "Janitor") {

                            Image(
                                painter = painterResource(R.drawable.west),
                                contentDescription = "Detective",
                                modifier = Modifier
                                    .align(Alignment.TopStart)
                                    .padding(start = 100.dp, top = 10.dp)
                                    .size(72.dp)
                            )
                        }

                        if (detectivePosition == "Sink") {

                            Image(
                                painter = painterResource(R.drawable.east),
                                contentDescription = "Detective",
                                modifier = Modifier
                                    .align(Alignment.TopEnd)
                                    .padding(end = 100.dp, top = 10.dp)
                                    .size(72.dp)
                            )
                        }

                        if (detectivePosition == "Toilet Stall") {

                            Image(
                                painter = painterResource(R.drawable.east),
                                contentDescription = "Detective",
                                modifier = Modifier
                                    .align(Alignment.BottomEnd)
                                    .padding(end = 120.dp, bottom = 20.dp)
                                    .size(72.dp)
                            )
                        }
                    }

                    "Reception" -> {

                        ObjectBox(
                            name = "Receptionist",
                            investigated =
                                investigatedObjects.contains("Receptionist"),

                            selected =
                                selectedObject == "Receptionist",
                            modifier = Modifier
                                .align(Alignment.TopStart)
                                .padding(16.dp),
                            onClick = {
                                selectedObject = it
                                detectivePosition = it
                            }
                        )

                        ObjectBox(
                            name = "Patient",
                            investigated =
                                investigatedObjects.contains("Patient"),

                            selected =
                                selectedObject == "Patient",
                            modifier = Modifier
                                .align(Alignment.TopEnd)
                                .padding(20.dp),
                            onClick = {
                                selectedObject = it
                                detectivePosition = it
                            }
                        )

                        ObjectBox(
                            name = "Notice Board",
                            investigated =
                                investigatedObjects.contains("Notice Board"),

                            selected =
                                selectedObject == "Notice Board",
                            modifier = Modifier
                                .align(Alignment.BottomEnd)
                                .padding(16.dp),
                            onClick = {
                                selectedObject = it
                                detectivePosition = it
                            }
                        )

                        if (detectivePosition == "Receptionist") {

                            Image(
                                painter = painterResource(R.drawable.west),
                                contentDescription = "Detective",
                                modifier = Modifier
                                    .align(Alignment.TopStart)
                                    .padding(start = 100.dp, top = 10.dp)
                                    .size(72.dp)
                            )
                        }

                        if (detectivePosition == "Patient") {

                            Image(
                                painter = painterResource(R.drawable.east),
                                contentDescription = "Detective",
                                modifier = Modifier
                                    .align(Alignment.TopEnd)
                                    .padding(end = 100.dp, top = 10.dp)
                                    .size(72.dp)
                            )
                        }

                        if (detectivePosition == "Notice Board") {

                            Image(
                                painter = painterResource(R.drawable.east),
                                contentDescription = "Detective",
                                modifier = Modifier
                                    .align(Alignment.BottomEnd)
                                    .padding(end = 110.dp, bottom = 20.dp)
                                    .size(72.dp)
                            )
                        }
                    }

                    "Ward" -> {

                        ObjectBox(
                            name = "Doctor",
                            investigated =
                                investigatedObjects.contains("Doctor"),
                            selected =
                                selectedObject == "Doctor",
                            modifier = Modifier
                                .align(Alignment.TopStart)
                                .padding(16.dp),
                            onClick = {
                                selectedObject = it
                                detectivePosition = it
                            }
                        )

                        ObjectBox(
                            name = "Patient",
                            investigated =
                                investigatedObjects.contains("Patient"),
                            selected =
                                selectedObject == "Patient",
                            modifier = Modifier
                                .align(Alignment.TopEnd)
                                .padding(20.dp),
                            onClick = {
                                selectedObject = it
                                detectivePosition = it
                            }
                        )

                        ObjectBox(
                            name = "Hospital Bed",
                            investigated =
                                investigatedObjects.contains("Hospital Bed"),
                            selected =
                                selectedObject == "Hospital Bed",
                            modifier = Modifier
                                .align(Alignment.BottomEnd)
                                .padding(16.dp),
                            onClick = {
                                selectedObject = it
                                detectivePosition = it
                            }
                        )

                        if (detectivePosition == "Doctor") {

                            Image(
                                painter = painterResource(R.drawable.west),
                                contentDescription = "Detective",
                                modifier = Modifier
                                    .align(Alignment.TopStart)
                                    .padding(start = 100.dp, top = 10.dp)
                                    .size(72.dp)
                            )
                        }

                        if (detectivePosition == "Patient") {

                            Image(
                                painter = painterResource(R.drawable.east),
                                contentDescription = "Detective",
                                modifier = Modifier
                                    .align(Alignment.TopEnd)
                                    .padding(end = 100.dp, top = 10.dp)
                                    .size(72.dp)
                            )
                        }

                        if (detectivePosition == "Hospital Bed") {

                            Image(
                                painter = painterResource(R.drawable.east),
                                contentDescription = "Detective",
                                modifier = Modifier
                                    .align(Alignment.BottomEnd)
                                    .padding(end = 120.dp, bottom = 20.dp)
                                    .size(72.dp)
                            )
                        }
                    }

                    "Records Office" -> {

                        ObjectBox(
                            name = "Records Board",
                            investigated =
                                investigatedObjects.contains("Records Board"),

                            selected =
                                selectedObject == "Records Board",
                            modifier = Modifier
                                .align(Alignment.TopStart)
                                .padding(16.dp),
                            onClick = {
                                selectedObject = it
                                detectivePosition = it
                            }
                        )

                        ObjectBox(
                            name = "Staff Member",
                            investigated =
                                investigatedObjects.contains("Staff Member"),

                            selected =
                                selectedObject == "Staff Member",
                            modifier = Modifier
                                .align(Alignment.TopEnd)
                                .padding(20.dp),
                            onClick = {
                                selectedObject = it
                                detectivePosition = it
                            }
                        )

                        ObjectBox(
                            name = "Case Files",
                            investigated =
                                investigatedObjects.contains("Case Files"),

                            selected =
                                selectedObject == "Case Files",
                            modifier = Modifier
                                .align(Alignment.BottomEnd)
                                .padding(16.dp),
                            onClick = {
                                selectedObject = it
                                detectivePosition = it
                            }
                        )

                        if (detectivePosition == "Records Board") {

                            Image(
                                painter = painterResource(R.drawable.west),
                                contentDescription = "Detective",
                                modifier = Modifier
                                    .align(Alignment.TopStart)
                                    .padding(start = 100.dp, top = 10.dp)
                                    .size(72.dp)
                            )
                        }

                        if (detectivePosition == "Staff Member") {

                            Image(
                                painter = painterResource(R.drawable.east),
                                contentDescription = "Detective",
                                modifier = Modifier
                                    .align(Alignment.TopEnd)
                                    .padding(end = 100.dp, top = 10.dp)
                                    .size(72.dp)
                            )
                        }

                        if (detectivePosition == "Case Files") {

                            Image(
                                painter = painterResource(R.drawable.east),
                                contentDescription = "Detective",
                                modifier = Modifier
                                    .align(Alignment.BottomEnd)
                                    .padding(end = 110.dp, bottom = 20.dp)
                                    .size(72.dp)
                            )
                        }
                    }

                    "Storage" -> {

                        ObjectBox(
                            name = "Storage Manager",
                            investigated =
                                investigatedObjects.contains("Storage Manager"),

                            selected =
                                selectedObject == "Storage Manager",
                            modifier = Modifier
                                .align(Alignment.TopStart)
                                .padding(16.dp),
                            onClick = {
                                selectedObject = it
                                detectivePosition = it
                            }
                        )

                        ObjectBox(
                            name = "Cooling Equipment",
                            investigated =
                                investigatedObjects.contains("Cooling Equipment"),

                            selected =
                                selectedObject == "Cooling Equipment",
                            modifier = Modifier
                                .align(Alignment.TopEnd)
                                .padding(20.dp),
                            onClick = {
                                selectedObject = it
                                detectivePosition = it
                            }
                        )

                        ObjectBox(
                            name = "Food Crates",
                            investigated =
                                investigatedObjects.contains("Food Crates"),

                            selected =
                                selectedObject == "Food Crates",
                            modifier = Modifier
                                .align(Alignment.BottomEnd)
                                .padding(16.dp),
                            onClick = {
                                selectedObject = it
                                detectivePosition = it
                            }
                        )

                        if (detectivePosition == "Storage Manager") {

                            Image(
                                painter = painterResource(R.drawable.west),
                                contentDescription = "Detective",
                                modifier = Modifier
                                    .align(Alignment.TopStart)
                                    .padding(start = 100.dp, top = 10.dp)
                                    .size(72.dp)
                            )
                        }

                        if (detectivePosition == "Cooling Equipment") {

                            Image(
                                painter = painterResource(R.drawable.east),
                                contentDescription = "Detective",
                                modifier = Modifier
                                    .align(Alignment.TopEnd)
                                    .padding(end = 100.dp, top = 10.dp)
                                    .size(72.dp)
                            )
                        }

                        if (detectivePosition == "Food Crates") {

                            Image(
                                painter = painterResource(R.drawable.east),
                                contentDescription = "Detective",
                                modifier = Modifier
                                    .align(Alignment.BottomEnd)
                                    .padding(end = 110.dp, bottom = 20.dp)
                                    .size(72.dp)
                            )
                        }
                    }

                    "Food Stall" -> {

                        ObjectBox(
                            name = "Vendor",
                            investigated =
                                investigatedObjects.contains("Vendor"),

                            selected =
                                selectedObject == "Vendor",
                            modifier = Modifier
                                .align(Alignment.TopStart)
                                .padding(16.dp),
                            onClick = {
                                selectedObject = it
                                detectivePosition = it
                            }
                        )

                        ObjectBox(
                            name = "Food Display",
                            investigated =
                                investigatedObjects.contains("Food Display"),

                            selected =
                                selectedObject == "Food Display",
                            modifier = Modifier
                                .align(Alignment.TopEnd)
                                .padding(20.dp),
                            onClick = {
                                selectedObject = it
                                detectivePosition = it
                            }
                        )

                        ObjectBox(
                            name = "Cash Counter",
                            investigated =
                                investigatedObjects.contains("Cash Counter"),

                            selected =
                                selectedObject == "Cash Counter",
                            modifier = Modifier
                                .align(Alignment.BottomEnd)
                                .padding(16.dp),
                            onClick = {
                                selectedObject = it
                                detectivePosition = it
                            }
                        )

                        if (detectivePosition == "Vendor") {

                            Image(
                                painter = painterResource(R.drawable.west),
                                contentDescription = "Detective",
                                modifier = Modifier
                                    .align(Alignment.TopStart)
                                    .padding(start = 100.dp, top = 10.dp)
                                    .size(72.dp)
                            )
                        }

                        if (detectivePosition == "Food Display") {

                            Image(
                                painter = painterResource(R.drawable.east),
                                contentDescription = "Detective",
                                modifier = Modifier
                                    .align(Alignment.TopEnd)
                                    .padding(end = 100.dp, top = 10.dp)
                                    .size(72.dp)
                            )
                        }

                        if (detectivePosition == "Cash Counter") {

                            Image(
                                painter = painterResource(R.drawable.east),
                                contentDescription = "Detective",
                                modifier = Modifier
                                    .align(Alignment.BottomEnd)
                                    .padding(end = 110.dp, bottom = 20.dp)
                                    .size(72.dp)
                            )
                        }
                    }

                    "Stage Area" -> {

                        ObjectBox(
                            name = "Security Guard",
                            investigated =
                                investigatedObjects.contains("Security Guard"),

                            selected =
                                selectedObject == "Security Guard",
                            modifier = Modifier
                                .align(Alignment.TopStart)
                                .padding(16.dp),
                            onClick = {
                                selectedObject = it
                                detectivePosition = it
                            }
                        )

                        ObjectBox(
                            name = "Crowd Area",
                            investigated =
                                investigatedObjects.contains("Crowd Area"),

                            selected =
                                selectedObject == "Crowd Area",
                            modifier = Modifier
                                .align(Alignment.TopEnd)
                                .padding(20.dp),
                            onClick = {
                                selectedObject = it
                                detectivePosition = it
                            }
                        )

                        ObjectBox(
                            name = "Event Schedule",
                            investigated =
                                investigatedObjects.contains("Event Schedule"),

                            selected =
                                selectedObject == "Event Schedule",
                            modifier = Modifier
                                .align(Alignment.BottomEnd)
                                .padding(16.dp),
                            onClick = {
                                selectedObject = it
                                detectivePosition = it
                            }
                        )

                        if (detectivePosition == "Security Guard") {

                            Image(
                                painter = painterResource(R.drawable.west),
                                contentDescription = "Detective",
                                modifier = Modifier
                                    .align(Alignment.TopStart)
                                    .padding(start = 100.dp, top = 10.dp)
                                    .size(72.dp)
                            )
                        }

                        if (detectivePosition == "Crowd Area") {

                            Image(
                                painter = painterResource(R.drawable.east),
                                contentDescription = "Detective",
                                modifier = Modifier
                                    .align(Alignment.TopEnd)
                                    .padding(end = 100.dp, top = 10.dp)
                                    .size(72.dp)
                            )
                        }

                        if (detectivePosition == "Event Schedule") {

                            Image(
                                painter = painterResource(R.drawable.east),
                                contentDescription = "Detective",
                                modifier = Modifier
                                    .align(Alignment.BottomEnd)
                                    .padding(end = 110.dp, bottom = 20.dp)
                                    .size(72.dp)
                            )
                        }
                    }

                    "Food Booth" -> {

                        ObjectBox(
                            name = "Food Vendor",
                            investigated =
                                investigatedObjects.contains("Food Vendor"),

                            selected =
                                selectedObject == "Food Vendor",
                            modifier = Modifier
                                .align(Alignment.TopStart)
                                .padding(16.dp),
                            onClick = {
                                selectedObject = it
                                detectivePosition = it
                            }
                        )

                        ObjectBox(
                            name = "Serving Counter",
                            investigated =
                                investigatedObjects.contains("Serving Counter"),

                            selected =
                                selectedObject == "Serving Counter",
                            modifier = Modifier
                                .align(Alignment.TopEnd)
                                .padding(20.dp),
                            onClick = {
                                selectedObject = it
                                detectivePosition = it
                            }
                        )

                        ObjectBox(
                            name = "Ingredient Box",
                            investigated =
                                investigatedObjects.contains("Ingredient Box"),

                            selected =
                                selectedObject == "Ingredient Box",
                            modifier = Modifier
                                .align(Alignment.BottomEnd)
                                .padding(16.dp),
                            onClick = {
                                selectedObject = it
                                detectivePosition = it
                            }
                        )

                        if (detectivePosition == "Food Vendor") {

                            Image(
                                painter = painterResource(R.drawable.west),
                                contentDescription = "Detective",
                                modifier = Modifier
                                    .align(Alignment.TopStart)
                                    .padding(start = 100.dp, top = 10.dp)
                                    .size(72.dp)
                            )
                        }

                        if (detectivePosition == "Serving Counter") {

                            Image(
                                painter = painterResource(R.drawable.east),
                                contentDescription = "Detective",
                                modifier = Modifier
                                    .align(Alignment.TopEnd)
                                    .padding(end = 100.dp, top = 10.dp)
                                    .size(72.dp)
                            )
                        }

                        if (detectivePosition == "Ingredient Box") {

                            Image(
                                painter = painterResource(R.drawable.east),
                                contentDescription = "Detective",
                                modifier = Modifier
                                    .align(Alignment.BottomEnd)
                                    .padding(end = 110.dp, bottom = 20.dp)
                                    .size(72.dp)
                            )
                        }
                    }

                    else -> {

                        ObjectBox(
                            name = "Object A",
                            investigated =
                                investigatedObjects.contains("Object A"),

                            selected =
                                selectedObject == "Object A",
                            modifier = Modifier
                                .align(Alignment.TopStart)
                                .padding(16.dp),
                            onClick = {
                                selectedObject = it
                                detectivePosition = it
                            }
                        )

                        ObjectBox(
                            name = "Object B",
                            investigated =
                                investigatedObjects.contains("Object B"),

                            selected =
                                selectedObject == "Object B",
                            modifier = Modifier
                                .align(Alignment.Center),
                            onClick = {
                                selectedObject = it
                                detectivePosition = it
                            }
                        )

                        ObjectBox(
                            name = "Object C",
                            investigated =
                                investigatedObjects.contains("Object C"),

                            selected =
                                selectedObject == "Object C",
                            modifier = Modifier
                                .align(Alignment.BottomEnd)
                                .padding(16.dp),
                            onClick = {
                                selectedObject = it
                                detectivePosition = it
                            }
                        )
                    }
                }
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {

                Button(
                    onClick = onBack
                ) {
                    Text("Back")
                }

                Text(
                    text = selectedObject ?: "",
                    style = MaterialTheme.typography.bodyLarge
                )

                Button(
                    enabled = selectedObject != null,
                    onClick = {
                        selectedObject?.let {
                            onObjectClick(it)
                        }
                    }
                ) {
                    Text("Interact >")
                }
            }

        }

    }
}

@Composable
private fun ObjectBox(
    name: String,
    investigated: Boolean = false,
    selected: Boolean = false,
    modifier: Modifier,
    onClick: (String) -> Unit
) {

    Card(
        modifier = modifier
            .size(
                width = 96.dp,
                height = 96.dp
            )
            .clickable(
                enabled = !investigated
            ) {
                onClick(name)
            },
        border = BorderStroke(
            if (selected) 6.dp else 3.dp,
            MaterialTheme.colorScheme.onBackground
        )
    ) {

        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        )

        {
            // (if (selected) "🕵\n" else "") +
            when {

                name.contains("Teacher") ->
                    Image(
                        painter = painterResource(id = R.drawable.teacher),
                        contentDescription = name,
                        modifier = Modifier.size(52.dp)
                    )

                name.contains("Student") ->
                    Image(
                        painter = painterResource(id = R.drawable.student),
                        contentDescription = name,
                        modifier = Modifier.size(52.dp)
                    )

                name.contains("Desk") ->
                    Image(
                        painter = painterResource(id = R.drawable.desk),
                        contentDescription = name,
                        modifier = Modifier.size(52.dp)
                    )

                name.contains("Doctor") ->
                    Image(
                        painter = painterResource(id = R.drawable.doctor),
                        contentDescription = name,
                        modifier = Modifier.size(52.dp)
                    )

                name.contains("Bed") ->
                    Image(
                        painter = painterResource(id = R.drawable.bed),
                        contentDescription = name,
                        modifier = Modifier.size(52.dp)
                    )

                name.contains("Sink") ->
                    Image(
                        painter = painterResource(id = R.drawable.sink),
                        contentDescription = name,
                        modifier = Modifier.size(52.dp)
                    )

                name.contains("Trash") ->
                    Image(
                        painter = painterResource(id = R.drawable.trash_bin),
                        contentDescription = name,
                        modifier = Modifier.size(52.dp)
                    )

                name.contains("Janitor") ->
                    Image(
                        painter = painterResource(id = R.drawable.janitor),
                        contentDescription = name,
                        modifier = Modifier.size(52.dp)
                    )

                name.contains("Cash") ->
                    Image(
                        painter = painterResource(id = R.drawable.cash_register),
                        contentDescription = name,
                        modifier = Modifier.size(52.dp)
                    )

                name.contains("Reception") ->
                    Image(
                        painter = painterResource(id = R.drawable.receptionist),
                        contentDescription = name,
                        modifier = Modifier.size(52.dp)
                    )

                name.contains("Notice") ->
                    Image(
                        painter = painterResource(id = R.drawable.notice_board),
                        contentDescription = name,
                        modifier = Modifier.size(52.dp)
                    )

                name.contains("Serving") ->
                    Image(
                        painter = painterResource(id = R.drawable.serving_counter),
                        contentDescription = name,
                        modifier = Modifier.size(52.dp)
                    )

                name.contains("Food") ->
                    Image(
                        painter = painterResource(id = R.drawable.food_crate),
                        contentDescription = name,
                        modifier = Modifier.size(52.dp)
                    )

                else ->
                    Text(
                        text =
                            when {

                                investigated ->
                                    "✓"

                                name.contains("Patient") ->
                                    "P"

                                name.contains("Cooling") ->
                                    "C"

                                name.contains("Guard") ->
                                    "G"

                                name.contains("Toilet") ->
                                    "To"

                                name.contains("Vendor") ->
                                    "V"

                                name.contains("Storage") ->
                                    "Sm"

                                name.contains("Records") ->
                                    "Rb"

                                name.contains("Staff") ->
                                    "St"

                                name.contains("Case") ->
                                    "Cf"

                                name.contains("Crowd") ->
                                    "Cr"

                                name.contains("Event") ->
                                    "E"

                                name.contains("Ingredient") ->
                                    "I"

                                else ->
                                    "X"
                            }
                    )
            }
        }
    }
}