package com.aaron.outbreaker.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.aaron.outbreaker.GameState
import kotlin.random.Random

@Composable
fun InvestigationScreen(
    locationName: String,
    gameState: GameState,
    onUpdate: (GameState) -> Unit,
    onBack: () -> Unit
) {

    var clueText by remember {
        mutableStateOf(
            "Choose an investigation action."
        )
    }

    val scenario =
        gameState.activeScenarioByLocation[locationName]
            ?: "False Alarm"

    val completedActions =
        gameState.investigatedActionsByLocation[locationName]
            ?: emptySet()

    val totalActionsUsed =
        gameState.investigatedActionsByLocation
            .values
            .sumOf { it.size }

    val remainingAP =
        gameState.investigationPoints -
                totalActionsUsed

    fun saveEvidence(
        action: String,
        clue: String
    ) {

        val currentEvidence =
            gameState.evidenceByLocation[locationName]
                ?: emptyList()

        val updatedEvidence =
            currentEvidence + clue

        val updatedActions =
            completedActions + action

        onUpdate(
            gameState.copy(
                evidenceByLocation =
                    gameState.evidenceByLocation +
                            (
                                    locationName to
                                            updatedEvidence
                                    ),

                investigatedActionsByLocation =
                    gameState.investigatedActionsByLocation +
                            (
                                    locationName to
                                            updatedActions
                                    )
            )
        )

        clueText = clue
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),

        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Text(
            text = "INVESTIGATION",
            fontSize = 28.sp
        )

        Spacer(modifier = Modifier.height(20.dp))

        Text(
            text = locationName,
            fontSize = 22.sp
        )

        Spacer(
            modifier = Modifier.height(20.dp)
        )

        val interactables = when (locationName) {

            "Classroom" -> listOf(
                "Teacher",
                "Student",
                "Desk"
            )

            "Kitchen" -> listOf(
                "Trash Bin",
                "Sink",
                "Cook"
            )

            "Washroom" -> listOf(
                "Sink",
                "Mirror",
                "Janitor"
            )

            "Reception" -> listOf(
                "Receptionist",
                "Visitor Log",
                "Waiting Area"
            )

            "Ward" -> listOf(
                "Doctor",
                "Patient",
                "Hospital Bed"
            )

            "Records Office" -> listOf(
                "Records Clerk",
                "Medical Reports",
                "Computer Terminal"
            )

            "Food Stall" -> listOf(
                "Vendor",
                "Food Counter",
                "Ingredients"
            )

            "Storage" -> listOf(
                "Storage Crates",
                "Supplies",
                "Delivery Log"
            )

            "Entrance" -> listOf(
                "Security Guard",
                "Notice Board",
                "Visitors"
            )

            "Stage" -> listOf(
                "Performer",
                "Sound Equipment",
                "Stage Area"
            )

            "Food Booth" -> listOf(
                "Food Vendor",
                "Serving Area",
                "Ingredients"
            )

            else -> listOf(
                "Bench",
                "Crowd",
                "Cleaner"
            )
        }

        Spacer(
            modifier = Modifier.height(8.dp)
        )

        Text(
            text = "Investigation Points: $remainingAP"
        )

        Spacer(
            modifier = Modifier.height(20.dp)
        )

        Text(
            text = "Interactables",
            fontSize = 20.sp
        )

        Spacer(
            modifier = Modifier.height(12.dp)
        )

        interactables.forEach {

            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 8.dp)
            ) {

                Text(
                    text = it,
                    modifier = Modifier.padding(16.dp)
                )
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        Card(
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = clueText,
                modifier = Modifier.padding(16.dp)
            )
        }

        Spacer(modifier = Modifier.height(20.dp))

        Button(
            enabled =
                !completedActions.contains("Interview") &&
                        remainingAP > 0,
            onClick = {

                val reliability =
                    when (Random.nextInt(100)) {

                        in 0..49 -> "High"

                        in 50..79 -> "Medium"

                        else -> "Low"
                    }

                val evidencePrefix =
                    "Source: Witness Statements\nReliability: $reliability"

                val clue = when (scenario) {

                    "Poor Hygiene" -> {

                        when (reliability) {

                            "High" -> {

                                listOf(

                                    "Several visitors reported recurring concerns about facility conditions.",

                                    "Multiple visitors mentioned ongoing maintenance-related issues."

                                ).random()
                            }

                            "Medium" -> {

                                listOf(

                                    "Some visitors expressed concerns about the condition of the area.",

                                    "Several visitors noted that conditions seemed poorly maintained."

                                ).random()
                            }

                            else -> {

                                listOf(

                                    "A few visitors appeared dissatisfied with local conditions.",

                                    "Some visitors felt the area was not being maintained properly."

                                ).random()
                            }
                        }
                    }

                    "Crowded Gathering" -> {

                        when (reliability) {

                            "High" -> {

                                listOf(

                                    "Several visitors reported difficulty moving through the area.",

                                    "Many visitors described unusually large crowds.",

                                    "Visitors reported long delays during peak periods."

                                ).random()
                            }

                            "Medium" -> {

                                listOf(

                                    "Activity levels seemed higher than usual.",

                                    "Some visitors mentioned congestion in busy areas."

                                ).random()
                            }

                            else -> {

                                listOf(

                                    "The area appeared busier than expected.",

                                    "Several visitors noted increased activity."

                                ).random()
                            }
                        }
                    }

                    "Food Contamination" -> {

                        when (reliability) {

                            "High" -> {

                                listOf(

                                    "Several visitors reported becoming unwell after the same event.",

                                    "Multiple health complaints appeared linked to a similar timeframe.",

                                    "Many affected visitors reported similar experiences."

                                ).random()
                            }

                            "Medium" -> {

                                listOf(

                                    "A number of visitors reported feeling unwell.",

                                    "Several visitors described similar health concerns."

                                ).random()
                            }

                            else -> {

                                listOf(

                                    "A few visitors reported mild health complaints.",

                                    "Some visitors mentioned feeling unwell recently."

                                ).random()
                            }
                        }
                    }

                    "Ongoing Transmission" -> {

                        when (reliability) {

                            "High" -> {

                                listOf(

                                    "Many visitors reported similar minor health concerns.",

                                    "Several visitors noted increasing illness among people around them.",

                                    "Multiple visitors reported that more people seemed unwell than usual."

                                ).random()
                            }

                            "Medium" -> {

                                listOf(

                                    "Some visitors reported increased illness in the community.",

                                    "Several people mentioned seeing more unwell individuals recently."

                                ).random()
                            }

                            else -> {

                                listOf(

                                    "A few visitors reported general health concerns.",

                                    "Some visitors felt more people appeared unwell than usual."

                                ).random()
                            }
                        }
                    }

                    "Normal" -> {

                        when (reliability) {

                            "High" ->
                                "No unusual activity was observed."

                            "Medium" ->
                                "Nothing significant was reported."

                            else ->
                                "Reports were inconsistent, but no clear issue was identified."
                        }
                    }

                    else ->
                        "No useful information obtained."
                }

                saveEvidence(
                    "Interview",
                    "$evidencePrefix\n\n$clue"
                )
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Interview Witness")
        }

        Spacer(modifier = Modifier.height(12.dp))

        Button(
            enabled =
                !completedActions.contains("Environment") &&
                        remainingAP > 0,

            onClick = {

                val clue = when (scenario) {

                    "Poor Hygiene" ->

                        listOf(

                            "Several maintenance-related concerns were observed.",

                            "Facility conditions showed signs of neglect.",

                            "A number of preventable operational issues were identified.",

                            "Inspection teams noted recurring upkeep concerns."

                        ).random()

                    "Crowded Gathering" ->

                        listOf(

                            "Common areas experienced unusually high activity.",

                            "Movement through the area became difficult during peak hours.",

                            "Services experienced pressure following increased visitor activity.",

                            "Large numbers of visitors occupied shared spaces."

                        ).random()

                    "Food Contamination" ->

                        listOf(

                            "Several reported concerns appeared linked to a similar timeframe.",

                            "Inspection findings suggested issues affecting a limited number of vendors.",

                            "Many reported cases shared similar characteristics.",

                            "Several affected groups appeared connected through a common activity."

                        ).random()

                    "Ongoing Transmission" ->

                        listOf(

                            "Absenteeism increased during the reporting period.",

                            "Community activity remained largely unchanged despite increasing concerns.",

                            "Healthcare services experienced higher demand than usual.",

                            "Large public events continued as scheduled."

                        ).random()

                    "Normal" ->
                        "Facility appeared well maintained."

                    else ->
                        "No environmental concerns detected."
                }

                saveEvidence(
                    "Environment",
                    "Source: Environmental Inspection\nReliability: High\n\n$clue"
                )
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Inspect Environment")
        }

        Spacer(modifier = Modifier.height(12.dp))

        Button(
            enabled =
                !completedActions.contains("Records") &&
                        remainingAP > 0,

            onClick = {

                val clue = when (scenario) {

                    "Poor Hygiene" ->

                        listOf(

                            "Maintenance complaints increased during the reporting period.",

                            "Facility-related concerns became more frequent this week.",

                            "Reports of preventable issues increased during the reporting period.",

                            "Operational reports highlighted recurring upkeep concerns."

                        ).random()

                    "Crowded Gathering" ->

                        listOf(

                            "Participation records showed unusually high activity levels.",

                            "Peak-hour traffic exceeded normal expectations.",

                            "Community activity increased significantly this week.",

                            "Attendance consistently exceeded planned capacity."

                        ).random()

                    "Food Contamination" ->

                        listOf(

                            "Health complaints appeared concentrated within a similar timeframe.",

                            "Several complaints appeared linked to a common source.",

                            "Many reported cases shared similar characteristics.",

                            "Several affected groups appeared connected through a common activity."

                        ).random()

                    "Ongoing Transmission" ->

                        listOf(

                            "Attendance records showed a sharp increase in absences.",

                            "Healthcare services experienced higher demand than usual.",

                            "More health-related concerns were reported than expected.",

                            "Reports suggested illness was affecting multiple groups."

                        ).random()

                    "Normal" ->
                        "Operational records showed normal activity."

                    else ->
                        "Records showed normal activity."
                }

                saveEvidence(
                    "Records",
                    "Source: Public Health Records\nReliability: High\n\n$clue"
                )
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Check Records")
        }

        Spacer(modifier = Modifier.height(24.dp))

        Text(
            "Evidence Collected: ${
                gameState.evidenceByLocation[locationName]
                    ?.size ?: 0
            }"
        )

        Spacer(modifier = Modifier.height(24.dp))

        Button(
            onClick = onBack
        ) {
            Text("Return To Map")
        }
    }
}