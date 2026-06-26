package com.aaron.outbreaker

import com.aaron.outbreaker.screens.MainMenuScreen
import com.aaron.outbreaker.screens.HowToPlayScreen
import com.aaron.outbreaker.screens.CreditsScreen
import com.aaron.outbreaker.screens.ResearchScreen
import com.aaron.outbreaker.screens.DashboardScreen
import com.aaron.outbreaker.screens.DailyReportScreen
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import com.aaron.outbreaker.ui.theme.OutbreakerTheme
import kotlin.random.Random
import com.aaron.outbreaker.screens.MapScreen
import com.aaron.outbreaker.screens.InvestigationScreen
import com.aaron.outbreaker.screens.BuildingInteriorScreen
import com.aaron.outbreaker.screens.EvidenceBoardScreen
import com.aaron.outbreaker.screens.CommanderScreen
import com.aaron.outbreaker.screens.OutcomeScreen
import com.aaron.outbreaker.screens.RoomScreen


class MainActivity : ComponentActivity() {

    fun createNewGameState(): GameState {

        val locations = listOf(
            "School",
            "Market",
            "Hospital",
            "Festival Grounds"
        )

        val scenarios = listOf(
            "Poor Hygiene",
            "Crowded Gathering",
            "Food Contamination",
            "Ongoing Transmission"
        )

        val startingScenario =
            scenarios.random()

        return GameState(

            outbreakLocation =
                locations.random(),

            outbreakScenario =
                startingScenario,

            activeScenarioByLocation =
                locations.associateWith {
                    startingScenario
                }
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            OutbreakerTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    OutbreakerApp()
                }
            }
        }
    }
}

@Composable
fun OutbreakerApp() {
    var currentScreen by remember { mutableStateOf("Menu") }
    val locations = listOf(
        "School",
        "Market",
        "Hospital",
        "Festival Grounds"
    )

    val scenarios = listOf(
        "Poor Hygiene",
        "Crowded Gathering",
        "Food Contamination",
        "Ongoing Transmission"
    )

    var gameState by remember {

        val startingScenario =
            scenarios.random()

        mutableStateOf(
            GameState(
                outbreakLocation =
                    locations.random(),

                outbreakScenario =
                    startingScenario,

                scenarioTemplateId = Random.nextInt(1, 8),

                activeScenarioByLocation =
                    locations.associateWith {
                        startingScenario
                    }
            )
        )
    }

    var reportText by remember { mutableStateOf("") }

    var selectedLocation by remember {
        mutableStateOf("")
    }

    var selectedRoom by remember {
        mutableStateOf("")
    }

    var selectedObject by remember {
        mutableStateOf("")
    }

    var investigatedObjects by remember {
        mutableStateOf(setOf<String>())
    }

    var showObservationPopup by remember {
        mutableStateOf(false)
    }

    var observationMessage by remember {
        mutableStateOf("")
    }

    when (currentScreen) {

        "Outcome" -> {

            OutcomeScreen(
                gameState = gameState,

                onNextDay = {

                    val locations = listOf(
                        "School",
                        "Market",
                        "Hospital",
                        "Festival Grounds"
                    )

                    val scenarios = listOf(
                        "Poor Hygiene",
                        "Crowded Gathering",
                        "Food Contamination",
                        "Ongoing Transmission"
                    )

                    val outbreakLocation: String
                    val outbreakScenario: String
                    val outbreakSeverity: Int

                    when (gameState.lastEffectiveness) {

                        "Highly Effective" -> {

                            outbreakLocation =
                                locations.random()

                            outbreakScenario =
                                scenarios.random()

                            outbreakSeverity = 1
                        }

                        "Effective" -> {

                            if (Random.nextInt(100) < 70) {

                                outbreakLocation =
                                    locations.random()

                                outbreakScenario =
                                    scenarios.random()

                                outbreakSeverity = 1

                            } else {

                                outbreakLocation =
                                    gameState.outbreakLocation

                                outbreakScenario =
                                    gameState.outbreakScenario

                                outbreakSeverity =
                                    gameState.outbreakSeverity
                            }
                        }

                        "Partially Effective" -> {

                            outbreakLocation =
                                locations.random()

                            outbreakScenario =
                                scenarios.random()

                            outbreakSeverity =
                                maxOf(1, gameState.outbreakSeverity)
                        }

                        "Ineffective" -> {

                            outbreakLocation =
                                locations.random()

                            outbreakScenario =
                                scenarios.random()

                            outbreakSeverity =
                                gameState.outbreakSeverity + 1
                        }

                        else -> {

                            outbreakLocation =
                                locations.random()

                            outbreakScenario =
                                scenarios.random()

                            outbreakSeverity = 1
                        }
                    }

                    val newScenarioMap =
                        locations.associateWith {
                            outbreakScenario
                        }

                    val crisisRoll = Random.nextInt(100)

                    val crisisName: String
                    val crisisDescription: String

                    when {

                        crisisRoll < 50 -> {

                            crisisName = ""
                            crisisDescription = ""
                        }

                        crisisRoll < 65 -> {

                            crisisName = "Festival Week"

                            crisisDescription =
                                "Large public gatherings are occurring. Crowded Gathering outbreaks are more dangerous."
                        }

                        crisisRoll < 80 -> {

                            crisisName = "Hospital Staff Shortage"

                            crisisDescription =
                                "Healthcare resources are strained. Respiratory outbreaks are harder to contain."
                        }

                        crisisRoll < 90 -> {

                            crisisName = "Public Panic"

                            crisisDescription =
                                "Citizens are anxious. Trust is easier to lose."
                        }

                        else -> {

                            crisisName = "Misinformation Campaign"

                            crisisDescription =
                                "False information is spreading. Awareness campaigns are less effective."
                        }
                    }

                    val severityInfectionPenalty =
                        when {

                            outbreakSeverity >= 4 -> 3

                            outbreakSeverity == 3 -> 2

                            outbreakSeverity == 2 -> 1

                            else -> 0
                        }

                    val severityTrustPenalty =
                        when {

                            outbreakSeverity >= 4 -> 2

                            outbreakSeverity == 3 -> 1

                            else -> 0
                        }

                    if (gameState.day >= 6) {

                        currentScreen = "Menu"

                    } else {

                    gameState = gameState.copy(

                        day = gameState.day + 1,

                        budget = gameState.budget + 15,

                        investigationPoints = 6,

                        visitedBuildings = emptySet(),

                        scenarioTemplateId = Random.nextInt(1, 8),

                        infection =
                            gameState.infection +
                                    severityInfectionPenalty,

                        trust =
                            (gameState.trust -
                                    severityTrustPenalty)
                                .coerceAtLeast(0),

                        evidenceByLocation =
                            emptyMap(),

                        investigatedActionsByLocation =
                            emptyMap(),

                        suspectedLocation = "",

                        suspectedCause = "",

                        selectedInterventions =
                            emptySet(),

                        latestOutcomeReport = "",

                        currentCrisis =
                            crisisName,

                        crisisDescription =
                            crisisDescription,

                        outbreakLocation =
                            outbreakLocation,

                        outbreakScenario =
                            outbreakScenario,

                        outbreakSeverity =
                            outbreakSeverity,

                        activeScenarioByLocation =
                            newScenarioMap
                    )

                    currentScreen = "Dashboard"

                    }

                }
            )

        }

        "Commander" -> {

            CommanderScreen(
                gameState = gameState,
                onUpdate = {
                    gameState = it
                },
                onNavigateOutcome = {
                    currentScreen = "Outcome"
                },
                onBack = {
                    currentScreen = "Dashboard"
                }
            )

        }

        "EvidenceBoard" -> {

            EvidenceBoardScreen(
                gameState = gameState,
                onUpdate = {
                    gameState = it
                },
                onCommander = {
                    currentScreen = "Commander"
                },
                onBack = {
                    currentScreen = "Dashboard"
                }
            )

        }

        "Menu" -> {
            MainMenuScreen(
                onStart = { currentScreen = "Dashboard" },
                onHowToPlay = { currentScreen = "HowToPlay" },
                onCredits = { currentScreen = "Credits" }
            )
        }

        "Dashboard" -> {
            DashboardScreen(
                gameState = gameState,
                onUpdate = {
                    gameState = it
                },
                onRestart = {

                    investigatedObjects = emptySet()

                    val startingScenario =
                        scenarios.random()

                    gameState = GameState(

                        outbreakLocation =
                            locations.random(),

                        outbreakScenario =
                            startingScenario,

                        scenarioTemplateId = Random.nextInt(1, 8),

                        activeScenarioByLocation =
                            locations.associateWith {
                                startingScenario
                            }
                    )
                },
                onResearch = {
                    currentScreen = "Research"
                },
                onDailyReport = {
                    reportText = it
                    currentScreen = "DailyReport"
                },
                onMap = {
                    currentScreen = "Map"
                },
                onEvidenceBoard = {
                    currentScreen = "EvidenceBoard"
                }
            )
        }

        "Map" -> {

            MapScreen(

                investigationPoints =
                    gameState.investigationPoints,


                onSchool = {

                    var updatedState = gameState

                    if (!gameState.visitedBuildings.contains("School")) {

                        updatedState =
                            updatedState.copy(

                                investigationPoints =
                                    maxOf(
                                        0,
                                        updatedState.investigationPoints - 1
                                    ),

                                visitedBuildings =
                                    updatedState.visitedBuildings + "School"
                            )
                    }

                    gameState = updatedState

                    selectedLocation = "School"
                    currentScreen = "BuildingInterior"
                },

                onMarket = {

                    var updatedState = gameState

                    if (!gameState.visitedBuildings.contains("Market")) {

                        updatedState =
                            updatedState.copy(

                                investigationPoints =
                                    maxOf(
                                        0,
                                        updatedState.investigationPoints - 1
                                    ),

                                visitedBuildings =
                                    updatedState.visitedBuildings + "Market"
                            )
                    }

                    gameState = updatedState

                    selectedLocation = "Market"
                    currentScreen = "BuildingInterior"
                },

                onHospital = {

                    var updatedState = gameState

                    if (!gameState.visitedBuildings.contains("Hospital")) {

                        updatedState =
                            updatedState.copy(

                                investigationPoints =
                                    maxOf(
                                        0,
                                        updatedState.investigationPoints - 1
                                    ),

                                visitedBuildings =
                                    updatedState.visitedBuildings + "Hospital"
                            )
                    }

                    gameState = updatedState

                    selectedLocation = "Hospital"
                    currentScreen = "BuildingInterior"
                },

                onFestival = {

                    var updatedState = gameState

                    if (!gameState.visitedBuildings.contains("Festival Grounds")) {

                        updatedState =
                            updatedState.copy(

                                investigationPoints =
                                    maxOf(
                                        0,
                                        updatedState.investigationPoints - 1
                                    ),

                                visitedBuildings =
                                    updatedState.visitedBuildings + "Festival Grounds"
                            )
                    }

                    gameState = updatedState

                    selectedLocation = "Festival Grounds"
                    currentScreen = "BuildingInterior"
                },

                onBack = {
                    currentScreen = "Dashboard"
                }
            )

        }

        "BuildingInterior" -> {

            BuildingInteriorScreen(
                buildingName = selectedLocation,
                investigationPoints = gameState.investigationPoints,

                onRoomSelected = {

                    selectedRoom = it

                    currentScreen =
                        "Room"
                },

                onBack = {

                    currentScreen = "Map"
                }
            )
        }

        "Room" -> {

            RoomScreen(

                roomName = selectedRoom,

                gameState = gameState,

                investigatedObjects =
                    investigatedObjects,

                onObjectClick = {

                    selectedObject = it

                    showObservationPopup = true
                },

                onBack = {

                    currentScreen =
                        "BuildingInterior"
                }
            )
        }

        "Investigation" -> {

            InvestigationScreen(
                locationName = selectedObject,
                gameState = gameState,
                onUpdate = {
                    gameState = it
                },
                onBack = {
                    currentScreen = "Map"
                }
            )

        }

        "Research" -> {
            ResearchScreen(
                gameState = gameState,
                onUpdate = { gameState = it },
                onBack = { currentScreen = "Dashboard" }
            )
        }

        "HowToPlay" -> {
            HowToPlayScreen(onBack = { currentScreen = "Menu" })
        }

        "Credits" -> {
            CreditsScreen(onBack = { currentScreen = "Menu" })
        }

        "DailyReport" -> {

            DailyReportScreen(
                reportText = reportText,
                onContinue = {
                    currentScreen = "Dashboard"
                }
            )
        }
    }

    if (showObservationPopup) {

        AlertDialog(

            onDismissRequest = {
                showObservationPopup = false
            },

            title = {
                Text(selectedObject)
            },

            text = {

                Text(

                    when (selectedObject) {

                        "Teacher" ->

                            when (
                                gameState.activeScenarioByLocation[selectedLocation]
                            ) {

                                "Food Contamination" -> {

                                    when (gameState.scenarioTemplateId) {

                                        1 ->
                                            "Observation: A teacher mentioned several students became ill later that evening after returning home."

                                        2 ->
                                            "Observation: Several parents reported their children feeling unwell shortly after lunch."

                                        3 ->
                                            "Observation: Teachers noted illness reports increased following meal breaks."

                                        4 ->
                                            "Observation: Multiple students reported similar stomach discomfort after eating."

                                        5 ->
                                            "Observation: Several students who purchased snacks from the same vendor later reported symptoms."

                                        6 ->
                                            "Observation: Teachers noted illness appeared shortly after a refrigerated meal was served."

                                        7 ->
                                            "Observation: Many affected students reported eating food prepared earlier that morning."

                                        else ->
                                            "Observation: Several students became ill after lunch."
                                    }
                                }

                                "Ongoing Transmission" -> {

                                    when (gameState.scenarioTemplateId) {

                                        1 ->
                                            "Observation: Absences increased gradually throughout the week."

                                        2 ->
                                            "Observation: New cases continued appearing several days after the first illnesses."

                                        3 ->
                                            "Observation: Students became ill at different times rather than all at once."

                                        4 ->
                                            "Observation: Several families reported illnesses spreading between household members."

                                        5 ->
                                            "Observation: New absences continued despite earlier students staying home."

                                        6 ->
                                            "Observation: Cases appeared across multiple classes over several days."

                                        7 ->
                                            "Observation: Teachers noticed a steady increase in illness reports throughout the week."

                                        else ->
                                            "Observation: Absences increased gradually throughout the week."
                                    }
                                }

                                "Poor Hygiene" -> {

                                    when (gameState.scenarioTemplateId) {

                                        1 ->
                                            "Observation: Shared surfaces appeared noticeably dirty."

                                        2 ->
                                            "Observation: Cleaning routines were not always completed."

                                        3 ->
                                            "Observation: Frequently touched objects showed visible grime."

                                        4 ->
                                            "Observation: Students rarely cleaned shared equipment."

                                        5 ->
                                            "Observation: Staff reported increasing sanitation concerns."

                                        6 ->
                                            "Observation: Cleaning supplies required replacement more frequently than expected."

                                        7 ->
                                            "Observation: Hygiene reminders were often ignored."

                                        else ->
                                            "Observation: Shared surfaces appeared noticeably dirty."
                                    }
                                }

                                "Crowded Gathering" -> {

                                    when (gameState.scenarioTemplateId) {

                                        1 ->
                                            "Observation: Attendance was unusually high during a recent event."

                                        2 ->
                                            "Observation: Students from several classes gathered together."

                                        3 ->
                                            "Observation: Shared activities attracted larger groups than expected."

                                        4 ->
                                            "Observation: Several events required additional crowd control."

                                        5 ->
                                            "Observation: Participation numbers exceeded expectations."

                                        6 ->
                                            "Observation: Gathering areas remained busy for extended periods."

                                        7 ->
                                            "Observation: Many students attended the same activity."

                                        else ->
                                            "Observation: Attendance was unusually high during a recent event."
                                    }
                                }

                                else ->
                                    "Observation: Several students have been absent this week."
                            }

                        "Desk" ->

                            when (
                                gameState.activeScenarioByLocation[selectedLocation]
                            ) {

                                "Food Contamination" -> {

                                    when (gameState.scenarioTemplateId) {

                                        1 ->
                                            "Observation: Several students left school earlier than usual."

                                        2 ->
                                            "Observation: Several desks remained empty after meal breaks."

                                        3 ->
                                            "Observation: Absences increased during the second half of the school day."

                                        4 ->
                                            "Observation: Multiple students left school early after lunch."

                                        else ->
                                            "Observation: Several desks appeared unused."
                                    }
                                }

                                "Ongoing Transmission" ->
                                    "Observation: Several desks remained empty throughout the week."

                                "Poor Hygiene" ->
                                    "Observation: Food wrappers and litter were frequently left behind."

                                "Crowded Gathering" ->
                                    "Observation: Classrooms appeared unusually crowded after recent events."

                                else ->
                                    "Observation: Several desks appeared unused."
                            }

                        "Student" ->

                            when (
                                gameState.activeScenarioByLocation[selectedLocation]
                            ) {

                                "Food Contamination" -> {

                                    when (gameState.scenarioTemplateId) {

                                        1 ->
                                            "Observation: Several classmates reported feeling unwell later in the day."

                                        2 ->
                                            "Observation: Many students discussed feeling unwell following lunch."

                                        3 ->
                                            "Observation: Students reported similar symptoms despite attending different classes."

                                        4 ->
                                            "Observation: Multiple students mentioned becoming sick after eating."

                                        else ->
                                            "Observation: Multiple classmates appeared unwell."
                                    }
                                }

                                "Ongoing Transmission" -> {

                                    when (gameState.scenarioTemplateId) {

                                        1 ->
                                            "Observation: Several classmates became ill days apart."

                                        2 ->
                                            "Observation: New absences continued appearing throughout the week."

                                        3 ->
                                            "Observation: Some students became ill after family members were sick."

                                        4 ->
                                            "Observation: Cases appeared gradually rather than all at once."

                                        5 ->
                                            "Observation: New illnesses continued even after earlier students stayed home."

                                        6 ->
                                            "Observation: Students from different classes became ill at different times."

                                        7 ->
                                            "Observation: Illness reports continued increasing over several days."

                                        else ->
                                            "Observation: Several classmates became ill days apart."
                                    }
                                }

                                "Poor Hygiene" -> {

                                    when (gameState.scenarioTemplateId) {

                                        1 ->
                                            "Observation: Shared items were rarely cleaned."

                                        2 ->
                                            "Observation: Frequently touched surfaces appeared dirty."

                                        3 ->
                                            "Observation: Students often shared equipment without cleaning it."

                                        4 ->
                                            "Observation: Cleaning routines were inconsistent."

                                        5 ->
                                            "Observation: Shared materials showed heavy wear and grime."

                                        6 ->
                                            "Observation: Sanitation practices varied significantly between groups."

                                        7 ->
                                            "Observation: Cleanliness concerns were commonly mentioned."

                                        else ->
                                            "Observation: Shared items were rarely cleaned."
                                    }
                                }

                                "Crowded Gathering" -> {

                                    when (gameState.scenarioTemplateId) {

                                        1 ->
                                            "Observation: Many students participated in the same event."

                                        2 ->
                                            "Observation: Several classes gathered together recently."

                                        3 ->
                                            "Observation: Attendance was unusually high during recent activities."

                                        4 ->
                                            "Observation: Shared event spaces remained busy throughout the day."

                                        5 ->
                                            "Observation: Large groups stayed together for extended periods."

                                        6 ->
                                            "Observation: Participation levels exceeded expectations."

                                        7 ->
                                            "Observation: Students spent significant time in shared gathering areas."

                                        else ->
                                            "Observation: Many students participated in the same event."
                                    }
                                }

                                else ->
                                    "Observation: Multiple classmates appeared unwell."
                            }

                        "Sink" ->
                            "The sink appeared heavily used and poorly maintained."

                        "Trash Bin" ->
                            "The trash bin appeared unusually full and had likely not been emptied recently."

                        "Crowd Area" ->

                            when (
                                gameState.activeScenarioByLocation[selectedLocation]
                            ) {

                                "Food Contamination" ->

                                    when (gameState.scenarioTemplateId) {

                                        1 ->
                                            "Observation: Movement became difficult due to crowd density near several gathering points."

                                        2 ->
                                            "Observation: Multiple areas became heavily congested during the event."

                                        3 ->
                                            "Observation: Large groups remained gathered for extended periods."

                                        4 ->
                                            "Observation: Several crowd-control measures were required during peak attendance."

                                        else ->
                                            "Observation: The area appeared unusually crowded."
                                    }

                                else ->
                                    "Observation: Crowd levels appeared normal."
                            }

                        "Serving Counter" ->

                            when (
                                gameState.activeScenarioByLocation[selectedLocation]
                            ) {

                                "Food Contamination" ->

                                    when (gameState.scenarioTemplateId) {

                                        1 ->
                                            "Observation: The serving area remained crowded throughout most of the event."

                                        2 ->
                                            "Observation: Visitor traffic was consistently high around food booths."

                                        3 ->
                                            "Observation: Crowd movement slowed significantly near serving areas."

                                        4 ->
                                            "Observation: Several food counters attracted unusually large gatherings."

                                        else ->
                                            "Observation: Visitor activity was unusually high."
                                    }

                                else ->
                                    "Observation: Activity levels appeared normal."
                            }

                        "Food Vendor" ->

                            when (
                                gameState.activeScenarioByLocation[selectedLocation]
                            ) {

                                "Food Contamination" ->

                                    when (gameState.scenarioTemplateId) {

                                        1 ->
                                            "Observation: Vendors reported unusually high food sales during the event."

                                        2 ->
                                            "Observation: Food stalls experienced heavier demand than expected."

                                        3 ->
                                            "Observation: Long queues formed around several food booths."

                                        4 ->
                                            "Observation: Food supplies were depleted faster than anticipated."

                                        else ->
                                            "Observation: Food sales were unusually high."
                                    }

                                "Ongoing Transmission" -> {

                                    when (gameState.scenarioTemplateId) {

                                        1 -> "Observation: Several customers returned on different days while feeling unwell."
                                        2 -> "Observation: Illness reports continued appearing throughout the week."
                                        3 -> "Observation: New customers reported symptoms over multiple days."
                                        4 -> "Observation: Reports of illness appeared gradually rather than all at once."
                                        5 -> "Observation: Vendors heard repeated complaints over an extended period."
                                        6 -> "Observation: New illness reports continued after earlier cases."
                                        7 -> "Observation: Customers described symptoms appearing at different times."
                                        else -> "Observation: Illness reports continued appearing throughout the week."
                                    }
                                }

                                "Poor Hygiene" -> {

                                    when (gameState.scenarioTemplateId) {

                                        1 -> "Observation: Food preparation areas appeared inconsistently cleaned."
                                        2 -> "Observation: Shared preparation equipment showed signs of heavy use."
                                        3 -> "Observation: Cleaning practices varied between workers."
                                        4 -> "Observation: Hygiene standards appeared inconsistent."
                                        5 -> "Observation: Work surfaces appeared overdue for cleaning."
                                        6 -> "Observation: Sanitization routines were not always followed."
                                        7 -> "Observation: Several cleanliness concerns were noted."
                                        else -> "Observation: Food preparation areas appeared inconsistently cleaned."
                                    }
                                }

                                "Crowded Gathering" -> {

                                    when (gameState.scenarioTemplateId) {

                                        1 -> "Observation: Customer traffic was unusually heavy."
                                        2 -> "Observation: Long queues formed throughout the day."
                                        3 -> "Observation: Large groups remained near vendor stalls."
                                        4 -> "Observation: Attendance exceeded expectations."
                                        5 -> "Observation: Gathering areas remained busy for extended periods."
                                        6 -> "Observation: Customer density was difficult to manage."
                                        7 -> "Observation: Vendors reported unusually large crowds."
                                        else -> "Observation: Customer traffic was unusually heavy."
                                    }
                                }

                                else ->
                                    "Observation: Vendor activity appeared normal."
                            }

                        "Security Guard" ->

                            when (
                                gameState.activeScenarioByLocation[selectedLocation]
                            ) {

                                "Food Contamination" ->

                                    when (gameState.scenarioTemplateId) {

                                        1 ->
                                            "Observation: Attendance during the recent event was significantly higher than expected."

                                        2 ->
                                            "Observation: Event organizers reported unusually large crowds."

                                        3 ->
                                            "Observation: Several gathering areas reached capacity."

                                        4 ->
                                            "Observation: Crowd density became difficult to manage."

                                        else ->
                                            "Observation: Attendance appeared unusually high."
                                    }

                                "Ongoing Transmission" -> {

                                    when (gameState.scenarioTemplateId) {

                                        1 -> "Observation: Visitor absences increased gradually over time."
                                        2 -> "Observation: New illness reports continued throughout the week."
                                        3 -> "Observation: Cases appeared on multiple separate days."
                                        4 -> "Observation: Reports continued after the first cases."
                                        5 -> "Observation: Attendance declined steadily."
                                        6 -> "Observation: New visitors reported illness over time."
                                        7 -> "Observation: Illness reports appeared in waves rather than all at once."
                                        else -> "Observation: Visitor absences increased gradually over time."
                                    }
                                }

                                "Poor Hygiene" -> {

                                    when (gameState.scenarioTemplateId) {

                                        1 -> "Observation: Shared facilities appeared poorly maintained."
                                        2 -> "Observation: Cleaning staff were frequently requested."
                                        3 -> "Observation: Several sanitation concerns were reported."
                                        4 -> "Observation: Frequently used areas appeared dirty."
                                        5 -> "Observation: Maintenance requests increased."
                                        6 -> "Observation: Cleanliness complaints became more common."
                                        7 -> "Observation: Shared facilities required additional cleaning."
                                        else -> "Observation: Shared facilities appeared poorly maintained."
                                    }
                                }

                                "Crowded Gathering" -> {

                                    when (gameState.scenarioTemplateId) {

                                        1 -> "Observation: Crowd sizes exceeded expectations."
                                        2 -> "Observation: Several gathering points became congested."
                                        3 -> "Observation: Additional crowd control was required."
                                        4 -> "Observation: Attendance was unusually high."
                                        5 -> "Observation: Gathering areas reached capacity."
                                        6 -> "Observation: Large groups remained together for long periods."
                                        7 -> "Observation: Visitor numbers were significantly above normal."
                                        else -> "Observation: Crowd sizes exceeded expectations."
                                    }
                                }

                                else ->
                                    "Observation: Attendance appeared normal."
                            }

                        "Food Crates" ->

                            when (
                                gameState.activeScenarioByLocation[selectedLocation]
                            ) {

                                "Food Contamination" ->

                                    when (gameState.scenarioTemplateId) {

                                        1 ->
                                            "Observation: Several food crates showed signs of recent spoilage."

                                        2 ->
                                            "Observation: Some products appeared to have been stored longer than expected."

                                        3 ->
                                            "Observation: Packaging dates varied significantly across stored food items."

                                        4 ->
                                            "Observation: Several food products required disposal due to quality concerns."

                                        else ->
                                            "Observation: Some food products appeared questionable."
                                    }

                                "Ongoing Transmission" -> {

                                    when (gameState.scenarioTemplateId) {

                                        1 -> "Observation: Storage operations appeared normal."
                                        2 -> "Observation: Inventory records showed no unusual concerns."
                                        3 -> "Observation: Handling procedures appeared routine."
                                        4 -> "Observation: No significant issues were identified."
                                        5 -> "Observation: Storage conditions appeared stable."
                                        6 -> "Observation: Staff reported no recent problems."
                                        7 -> "Observation: Equipment appeared operational."
                                        else -> "Observation: Storage operations appeared normal."
                                    }
                                }

                                "Poor Hygiene" -> {

                                    when (gameState.scenarioTemplateId) {

                                        1 -> "Observation: Storage shelves appeared dusty."
                                        2 -> "Observation: Cleaning logs contained gaps."
                                        3 -> "Observation: Some areas appeared overdue for cleaning."
                                        4 -> "Observation: General sanitation standards appeared inconsistent."
                                        5 -> "Observation: Several surfaces appeared dirty."
                                        6 -> "Observation: Maintenance concerns were noted."
                                        7 -> "Observation: Cleaning schedules appeared irregular."
                                        else -> "Observation: Storage shelves appeared dusty."
                                    }
                                }

                                "Crowded Gathering" -> {

                                    when (gameState.scenarioTemplateId) {

                                        1 -> "Observation: Supply demand increased recently."
                                        2 -> "Observation: Inventory turnover was unusually high."
                                        3 -> "Observation: Product movement increased during recent events."
                                        4 -> "Observation: Stock levels fluctuated significantly."
                                        5 -> "Observation: Several products required rapid restocking."
                                        6 -> "Observation: Demand exceeded expectations."
                                        7 -> "Observation: Inventory usage increased noticeably."
                                        else -> "Observation: Supply demand increased recently."
                                    }
                                }

                                else ->
                                    "Observation: Food storage conditions appeared acceptable."
                            }

                        "Storage Manager" ->

                            when (
                                gameState.activeScenarioByLocation[selectedLocation]
                            ) {

                                "Food Contamination" ->

                                    when (gameState.scenarioTemplateId) {

                                        1 ->
                                            "Observation: Staff reported several food items were moved after refrigeration issues occurred."

                                        2 ->
                                            "Observation: Inventory records showed concerns about food storage conditions."

                                        3 ->
                                            "Observation: Staff discussed difficulties maintaining safe storage temperatures."

                                        4 ->
                                            "Observation: Recent complaints involved improperly stored food products."

                                        else ->
                                            "Observation: Staff discussed food storage concerns."
                                    }

                                else ->
                                    "Observation: Storage operations appeared normal."
                            }

                        "Cooling Equipment" ->

                            when (
                                gameState.activeScenarioByLocation[selectedLocation]
                            ) {

                                "Food Contamination" ->

                                    when (gameState.scenarioTemplateId) {

                                        1 ->
                                            "Observation: Cooling equipment appears to have malfunctioned recently."

                                        2 ->
                                            "Observation: Temperature logs show irregular storage conditions."

                                        3 ->
                                            "Observation: Several refrigerated products appear improperly stored."

                                        4 ->
                                            "Observation: Staff reported recent refrigeration issues."

                                        else ->
                                            "Observation: Cooling equipment appears unreliable."
                                    }

                                else ->
                                    "Observation: Cooling equipment appears operational."
                            }

                        "Doctor" ->

                            when (
                                gameState.activeScenarioByLocation[selectedLocation]
                            ) {

                                "Food Contamination" ->

                                    when (gameState.scenarioTemplateId) {

                                        1 ->
                                            "Observation: Most patients reported stomach discomfort rather than respiratory symptoms."

                                        2 ->
                                            "Observation: Doctors noted digestive symptoms were more common than coughing."

                                        3 ->
                                            "Observation: Several patients reported nausea shortly after eating."

                                        4 ->
                                            "Observation: Similar gastrointestinal symptoms appeared across multiple cases."

                                        else ->
                                            "Observation: Several patients reported stomach-related symptoms."
                                    }

                                "Ongoing Transmission" -> {

                                    when (gameState.scenarioTemplateId) {

                                        1 ->
                                            "Observation: New cases continued appearing over multiple days."

                                        2 ->
                                            "Observation: Patients became ill at different times."

                                        3 ->
                                            "Observation: Several cases reported recent contact with ill individuals."

                                        4 ->
                                            "Observation: Case numbers increased steadily throughout the week."

                                        5 ->
                                            "Observation: New patients continued arriving despite earlier isolation efforts."

                                        6 ->
                                            "Observation: Cases appeared across multiple households."

                                        7 ->
                                            "Observation: Doctors noted a continuing chain of infections."

                                        else ->
                                            "Observation: New cases continued appearing over multiple days."
                                    }
                                }

                                "Poor Hygiene" -> {

                                    when (gameState.scenarioTemplateId) {

                                        1 ->
                                            "Observation: Several cases appeared linked to shared surfaces."

                                        2 ->
                                            "Observation: Patients reported frequent use of common facilities."

                                        3 ->
                                            "Observation: Doctors noted concerns regarding sanitation practices."

                                        4 ->
                                            "Observation: Multiple cases involved individuals sharing equipment."

                                        5 ->
                                            "Observation: Several patients reported poor cleanliness conditions."

                                        6 ->
                                            "Observation: Cases appeared concentrated among people sharing facilities."

                                        7 ->
                                            "Observation: Doctors suspected environmental cleanliness may be a factor."

                                        else ->
                                            "Observation: Several cases appeared linked to shared surfaces."
                                    }
                                }

                                "Crowded Gathering" -> {

                                    when (gameState.scenarioTemplateId) {

                                        1 ->
                                            "Observation: Many patients attended the same recent event."

                                        2 ->
                                            "Observation: Several cases shared a common gathering location."

                                        3 ->
                                            "Observation: Patients reported spending time in crowded areas."

                                        4 ->
                                            "Observation: A recent public event appeared in multiple case histories."

                                        5 ->
                                            "Observation: Doctors noted a large overlap in recent social activities."

                                        6 ->
                                            "Observation: Several patients reported attending the same gathering."

                                        7 ->
                                            "Observation: Cases appeared connected to a recent high-attendance activity."

                                        else ->
                                            "Observation: Many patients attended the same recent event."
                                    }
                                }

                                else ->
                                    "Observation: Doctors continue monitoring new cases."
                            }

                        "Patient" ->

                            when (
                                gameState.activeScenarioByLocation[selectedLocation]
                            ) {

                                "Food Contamination" ->

                                    when (gameState.scenarioTemplateId) {

                                        1 ->
                                            "Observation: Several patients reported feeling ill after eating earlier in the day."

                                        2 ->
                                            "Observation: Multiple patients mentioned symptoms beginning after meals."

                                        3 ->
                                            "Observation: Patients described similar stomach-related symptoms."

                                        4 ->
                                            "Observation: Several patients reported nausea following food consumption."

                                        else ->
                                            "Observation: Several patients reported similar digestive symptoms."
                                    }

                                "Ongoing Transmission" -> {

                                    when (gameState.scenarioTemplateId) {

                                        1 ->
                                            "Observation: Several patients reported becoming ill after contact with someone already sick."

                                        2 ->
                                            "Observation: Symptoms appeared days after exposure rather than immediately."

                                        3 ->
                                            "Observation: Illness spread through families over time."

                                        4 ->
                                            "Observation: Patients became ill on different days."

                                        5 ->
                                            "Observation: New cases continued appearing throughout the week."

                                        6 ->
                                            "Observation: Some patients knew others who had recently become ill."

                                        7 ->
                                            "Observation: Symptoms appeared gradually across multiple households."

                                        else ->
                                            "Observation: Several patients reported becoming ill after contact with someone already sick."
                                    }
                                }

                                "Poor Hygiene" -> {

                                    when (gameState.scenarioTemplateId) {

                                        1 ->
                                            "Observation: Patients frequently mentioned dirty shared facilities."

                                        2 ->
                                            "Observation: Several individuals reported concerns about cleanliness."

                                        3 ->
                                            "Observation: Shared equipment was commonly used without cleaning."

                                        4 ->
                                            "Observation: Patients recalled poor sanitation conditions."

                                        5 ->
                                            "Observation: Frequently touched areas appeared poorly maintained."

                                        6 ->
                                            "Observation: Several patients reported similar hygiene concerns."

                                        7 ->
                                            "Observation: Common-use areas were often described as unclean."

                                        else ->
                                            "Observation: Patients frequently mentioned dirty shared facilities."
                                    }
                                }

                                "Crowded Gathering" -> {

                                    when (gameState.scenarioTemplateId) {

                                        1 ->
                                            "Observation: Many patients attended the same recent event."

                                        2 ->
                                            "Observation: Several patients remembered being part of a large crowd."

                                        3 ->
                                            "Observation: Multiple cases involved attendance at the same activity."

                                        4 ->
                                            "Observation: Patients reported spending time in busy public spaces."

                                        5 ->
                                            "Observation: Several individuals attended the same gathering."

                                        6 ->
                                            "Observation: Large public events appeared repeatedly in patient histories."

                                        7 ->
                                            "Observation: Many patients visited the same crowded location."

                                        else ->
                                            "Observation: Many patients attended the same recent event."
                                    }
                                }

                                else ->
                                    "Observation: Patients are waiting for treatment."
                            }

                        "Hospital Bed" ->
                            "Most beds appeared occupied during the visit."

                        else ->
                            "The investigator noted something worth recording."
                    }
                )
            },

            confirmButton = {

                Button(

                    enabled =
                        gameState.investigationPoints > 0,

                    onClick = {

                        val currentEvidence =
                            gameState.evidenceByLocation[selectedLocation]
                                ?: emptyList()

                        val observation =
                            when (selectedObject) {

                                "Teacher" ->

                                    when (
                                        gameState.activeScenarioByLocation[selectedLocation]
                                    ) {

                                        "Food Contamination" -> {

                                            when (gameState.scenarioTemplateId) {

                                                1 ->
                                                    "Observation: A teacher mentioned several students became ill later that evening after returning home."

                                                2 ->
                                                    "Observation: Several parents reported their children feeling unwell shortly after lunch."

                                                3 ->
                                                    "Observation: Teachers noted illness reports increased following meal breaks."

                                                4 ->
                                                    "Observation: Multiple students reported similar stomach discomfort after eating."

                                                else ->
                                                    "Observation: Several students became ill after lunch."
                                            }
                                        }

                                        "Ongoing Transmission" -> {

                                            when (gameState.scenarioTemplateId) {

                                                1 ->
                                                    "Observation: Absences increased gradually throughout the week."

                                                2 ->
                                                    "Observation: New cases continued appearing several days after the first illnesses."

                                                3 ->
                                                    "Observation: Students became ill at different times rather than all at once."

                                                4 ->
                                                    "Observation: Several families reported illnesses spreading between household members."

                                                5 ->
                                                    "Observation: New absences continued despite earlier students staying home."

                                                6 ->
                                                    "Observation: Cases appeared across multiple classes over several days."

                                                7 ->
                                                    "Observation: Teachers noticed a steady increase in illness reports throughout the week."

                                                else ->
                                                    "Observation: Absences increased gradually throughout the week."
                                            }
                                        }

                                        "Poor Hygiene" -> {

                                            when (gameState.scenarioTemplateId) {

                                                1 ->
                                                    "Observation: Shared surfaces appeared noticeably dirty."

                                                2 ->
                                                    "Observation: Cleaning routines were not always completed."

                                                3 ->
                                                    "Observation: Frequently touched objects showed visible grime."

                                                4 ->
                                                    "Observation: Students rarely cleaned shared equipment."

                                                5 ->
                                                    "Observation: Staff reported increasing sanitation concerns."

                                                6 ->
                                                    "Observation: Cleaning supplies required replacement more frequently than expected."

                                                7 ->
                                                    "Observation: Hygiene reminders were often ignored."

                                                else ->
                                                    "Observation: Shared surfaces appeared noticeably dirty."
                                            }
                                        }

                                        "Crowded Gathering" -> {

                                            when (gameState.scenarioTemplateId) {

                                                1 ->
                                                    "Observation: Attendance was unusually high during a recent event."

                                                2 ->
                                                    "Observation: Students from several classes gathered together."

                                                3 ->
                                                    "Observation: Shared activities attracted larger groups than expected."

                                                4 ->
                                                    "Observation: Several events required additional crowd control."

                                                5 ->
                                                    "Observation: Participation numbers exceeded expectations."

                                                6 ->
                                                    "Observation: Gathering areas remained busy for extended periods."

                                                7 ->
                                                    "Observation: Many students attended the same activity."

                                                else ->
                                                    "Observation: Attendance was unusually high during a recent event."
                                            }
                                        }

                                        else ->
                                            "Observation: Several students have been absent this week."
                                    }

                                "Desk" ->

                                    when (
                                        gameState.activeScenarioByLocation[selectedLocation]
                                    ) {

                                        "Food Contamination" -> {

                                            when (gameState.scenarioTemplateId) {

                                                1 ->
                                                    "Observation: Several students left school earlier than usual."

                                                2 ->
                                                    "Observation: Several desks remained empty after meal breaks."

                                                3 ->
                                                    "Observation: Absences increased during the second half of the school day."

                                                4 ->
                                                    "Observation: Multiple students left school early after lunch."

                                                else ->
                                                    "Observation: Several desks appeared unused."
                                            }
                                        }

                                        "Ongoing Transmission" ->
                                            "Observation: Several desks remained empty throughout the week."

                                        "Poor Hygiene" ->
                                            "Observation: Food wrappers and litter were frequently left behind."

                                        "Crowded Gathering" ->
                                            "Observation: Classrooms appeared unusually crowded after recent events."

                                        else ->
                                            "Observation: Several desks appeared unused."
                                    }

                                "Student" ->

                                    when (
                                        gameState.activeScenarioByLocation[selectedLocation]
                                    ) {

                                        "Food Contamination" -> {

                                            when (gameState.scenarioTemplateId) {

                                                1 ->
                                                    "Observation: Several classmates reported feeling unwell later in the day."

                                                2 ->
                                                    "Observation: Many students discussed feeling unwell following lunch."

                                                3 ->
                                                    "Observation: Students reported similar symptoms despite attending different classes."

                                                4 ->
                                                    "Observation: Multiple students mentioned becoming sick after eating."

                                                else ->
                                                    "Observation: Multiple classmates appeared unwell."
                                            }
                                        }

                                        "Ongoing Transmission" -> {

                                            when (gameState.scenarioTemplateId) {

                                                1 ->
                                                    "Observation: Several classmates became ill days apart."

                                                2 ->
                                                    "Observation: New absences continued appearing throughout the week."

                                                3 ->
                                                    "Observation: Some students became ill after family members were sick."

                                                4 ->
                                                    "Observation: Cases appeared gradually rather than all at once."

                                                5 ->
                                                    "Observation: New illnesses continued even after earlier students stayed home."

                                                6 ->
                                                    "Observation: Students from different classes became ill at different times."

                                                7 ->
                                                    "Observation: Illness reports continued increasing over several days."

                                                else ->
                                                    "Observation: Several classmates became ill days apart."
                                            }
                                        }

                                        "Poor Hygiene" -> {

                                            when (gameState.scenarioTemplateId) {

                                                1 ->
                                                    "Observation: Shared items were rarely cleaned."

                                                2 ->
                                                    "Observation: Frequently touched surfaces appeared dirty."

                                                3 ->
                                                    "Observation: Students often shared equipment without cleaning it."

                                                4 ->
                                                    "Observation: Cleaning routines were inconsistent."

                                                5 ->
                                                    "Observation: Shared materials showed heavy wear and grime."

                                                6 ->
                                                    "Observation: Sanitation practices varied significantly between groups."

                                                7 ->
                                                    "Observation: Cleanliness concerns were commonly mentioned."

                                                else ->
                                                    "Observation: Shared items were rarely cleaned."
                                            }
                                        }

                                        "Crowded Gathering" -> {

                                            when (gameState.scenarioTemplateId) {

                                                1 ->
                                                    "Observation: Many students participated in the same event."

                                                2 ->
                                                    "Observation: Several classes gathered together recently."

                                                3 ->
                                                    "Observation: Attendance was unusually high during recent activities."

                                                4 ->
                                                    "Observation: Shared event spaces remained busy throughout the day."

                                                5 ->
                                                    "Observation: Large groups stayed together for extended periods."

                                                6 ->
                                                    "Observation: Participation levels exceeded expectations."

                                                7 ->
                                                    "Observation: Students spent significant time in shared gathering areas."

                                                else ->
                                                    "Observation: Many students participated in the same event."
                                            }
                                        }

                                        else ->
                                            "Observation: Multiple classmates appeared unwell."
                                    }

                                "Sink" ->
                                    "The sink appeared heavily used and poorly maintained."

                                "Trash Bin" ->
                                    "The trash bin appeared unusually full and had likely not been emptied recently."

                                "Crowd Area" ->

                                    when (
                                        gameState.activeScenarioByLocation[selectedLocation]
                                    ) {

                                        "Food Contamination" ->

                                            when (gameState.scenarioTemplateId) {

                                                1 ->
                                                    "Observation: Movement became difficult due to crowd density near several gathering points."

                                                2 ->
                                                    "Observation: Multiple areas became heavily congested during the event."

                                                3 ->
                                                    "Observation: Large groups remained gathered for extended periods."

                                                4 ->
                                                    "Observation: Several crowd-control measures were required during peak attendance."

                                                else ->
                                                    "Observation: The area appeared unusually crowded."
                                            }

                                        else ->
                                            "Observation: Crowd levels appeared normal."
                                    }

                                "Serving Counter" ->

                                    when (
                                        gameState.activeScenarioByLocation[selectedLocation]
                                    ) {

                                        "Food Contamination" ->

                                            when (gameState.scenarioTemplateId) {

                                                1 ->
                                                    "Observation: The serving area remained crowded throughout most of the event."

                                                2 ->
                                                    "Observation: Visitor traffic was consistently high around food booths."

                                                3 ->
                                                    "Observation: Crowd movement slowed significantly near serving areas."

                                                4 ->
                                                    "Observation: Several food counters attracted unusually large gatherings."

                                                else ->
                                                    "Observation: Visitor activity was unusually high."
                                            }

                                        else ->
                                            "Observation: Activity levels appeared normal."
                                    }

                                "Food Vendor" ->

                                    when (
                                        gameState.activeScenarioByLocation[selectedLocation]
                                    ) {

                                        "Food Contamination" ->

                                            when (gameState.scenarioTemplateId) {

                                                1 ->
                                                    "Observation: Vendors reported unusually high food sales during the event."

                                                2 ->
                                                    "Observation: Food stalls experienced heavier demand than expected."

                                                3 ->
                                                    "Observation: Long queues formed around several food booths."

                                                4 ->
                                                    "Observation: Food supplies were depleted faster than anticipated."

                                                else ->
                                                    "Observation: Food sales were unusually high."
                                            }

                                        "Ongoing Transmission" -> {

                                            when (gameState.scenarioTemplateId) {

                                                1 -> "Observation: Several customers returned on different days while feeling unwell."
                                                2 -> "Observation: Illness reports continued appearing throughout the week."
                                                3 -> "Observation: New customers reported symptoms over multiple days."
                                                4 -> "Observation: Reports of illness appeared gradually rather than all at once."
                                                5 -> "Observation: Vendors heard repeated complaints over an extended period."
                                                6 -> "Observation: New illness reports continued after earlier cases."
                                                7 -> "Observation: Customers described symptoms appearing at different times."
                                                else -> "Observation: Illness reports continued appearing throughout the week."
                                            }
                                        }

                                        "Poor Hygiene" -> {

                                            when (gameState.scenarioTemplateId) {

                                                1 -> "Observation: Food preparation areas appeared inconsistently cleaned."
                                                2 -> "Observation: Shared preparation equipment showed signs of heavy use."
                                                3 -> "Observation: Cleaning practices varied between workers."
                                                4 -> "Observation: Hygiene standards appeared inconsistent."
                                                5 -> "Observation: Work surfaces appeared overdue for cleaning."
                                                6 -> "Observation: Sanitization routines were not always followed."
                                                7 -> "Observation: Several cleanliness concerns were noted."
                                                else -> "Observation: Food preparation areas appeared inconsistently cleaned."
                                            }
                                        }

                                        "Crowded Gathering" -> {

                                            when (gameState.scenarioTemplateId) {

                                                1 -> "Observation: Customer traffic was unusually heavy."
                                                2 -> "Observation: Long queues formed throughout the day."
                                                3 -> "Observation: Large groups remained near vendor stalls."
                                                4 -> "Observation: Attendance exceeded expectations."
                                                5 -> "Observation: Gathering areas remained busy for extended periods."
                                                6 -> "Observation: Customer density was difficult to manage."
                                                7 -> "Observation: Vendors reported unusually large crowds."
                                                else -> "Observation: Customer traffic was unusually heavy."
                                            }
                                        }

                                        else ->
                                            "Observation: Vendor activity appeared normal."
                                    }

                                "Security Guard" ->

                                    when (
                                        gameState.activeScenarioByLocation[selectedLocation]
                                    ) {

                                        "Food Contamination" ->

                                            when (gameState.scenarioTemplateId) {

                                                1 ->
                                                    "Observation: Attendance during the recent event was significantly higher than expected."

                                                2 ->
                                                    "Observation: Event organizers reported unusually large crowds."

                                                3 ->
                                                    "Observation: Several gathering areas reached capacity."

                                                4 ->
                                                    "Observation: Crowd density became difficult to manage."

                                                else ->
                                                    "Observation: Attendance appeared unusually high."
                                            }

                                        "Ongoing Transmission" -> {

                                            when (gameState.scenarioTemplateId) {

                                                1 -> "Observation: Visitor absences increased gradually over time."
                                                2 -> "Observation: New illness reports continued throughout the week."
                                                3 -> "Observation: Cases appeared on multiple separate days."
                                                4 -> "Observation: Reports continued after the first cases."
                                                5 -> "Observation: Attendance declined steadily."
                                                6 -> "Observation: New visitors reported illness over time."
                                                7 -> "Observation: Illness reports appeared in waves rather than all at once."
                                                else -> "Observation: Visitor absences increased gradually over time."
                                            }
                                        }

                                        "Poor Hygiene" -> {

                                            when (gameState.scenarioTemplateId) {

                                                1 -> "Observation: Shared facilities appeared poorly maintained."
                                                2 -> "Observation: Cleaning staff were frequently requested."
                                                3 -> "Observation: Several sanitation concerns were reported."
                                                4 -> "Observation: Frequently used areas appeared dirty."
                                                5 -> "Observation: Maintenance requests increased."
                                                6 -> "Observation: Cleanliness complaints became more common."
                                                7 -> "Observation: Shared facilities required additional cleaning."
                                                else -> "Observation: Shared facilities appeared poorly maintained."
                                            }
                                        }

                                        "Crowded Gathering" -> {

                                            when (gameState.scenarioTemplateId) {

                                                1 -> "Observation: Crowd sizes exceeded expectations."
                                                2 -> "Observation: Several gathering points became congested."
                                                3 -> "Observation: Additional crowd control was required."
                                                4 -> "Observation: Attendance was unusually high."
                                                5 -> "Observation: Gathering areas reached capacity."
                                                6 -> "Observation: Large groups remained together for long periods."
                                                7 -> "Observation: Visitor numbers were significantly above normal."
                                                else -> "Observation: Crowd sizes exceeded expectations."
                                            }
                                        }

                                        else ->
                                            "Observation: Attendance appeared normal."
                                    }

                                "Food Crates" ->

                                    when (
                                        gameState.activeScenarioByLocation[selectedLocation]
                                    ) {

                                        "Food Contamination" ->

                                            when (gameState.scenarioTemplateId) {

                                                1 ->
                                                    "Observation: Several food crates showed signs of recent spoilage."

                                                2 ->
                                                    "Observation: Some products appeared to have been stored longer than expected."

                                                3 ->
                                                    "Observation: Packaging dates varied significantly across stored food items."

                                                4 ->
                                                    "Observation: Several food products required disposal due to quality concerns."

                                                else ->
                                                    "Observation: Some food products appeared questionable."
                                            }

                                        "Ongoing Transmission" -> {

                                            when (gameState.scenarioTemplateId) {

                                                1 -> "Observation: Storage operations appeared normal."
                                                2 -> "Observation: Inventory records showed no unusual concerns."
                                                3 -> "Observation: Handling procedures appeared routine."
                                                4 -> "Observation: No significant issues were identified."
                                                5 -> "Observation: Storage conditions appeared stable."
                                                6 -> "Observation: Staff reported no recent problems."
                                                7 -> "Observation: Equipment appeared operational."
                                                else -> "Observation: Storage operations appeared normal."
                                            }
                                        }

                                        "Poor Hygiene" -> {

                                            when (gameState.scenarioTemplateId) {

                                                1 -> "Observation: Storage shelves appeared dusty."
                                                2 -> "Observation: Cleaning logs contained gaps."
                                                3 -> "Observation: Some areas appeared overdue for cleaning."
                                                4 -> "Observation: General sanitation standards appeared inconsistent."
                                                5 -> "Observation: Several surfaces appeared dirty."
                                                6 -> "Observation: Maintenance concerns were noted."
                                                7 -> "Observation: Cleaning schedules appeared irregular."
                                                else -> "Observation: Storage shelves appeared dusty."
                                            }
                                        }

                                        "Crowded Gathering" -> {

                                            when (gameState.scenarioTemplateId) {

                                                1 -> "Observation: Supply demand increased recently."
                                                2 -> "Observation: Inventory turnover was unusually high."
                                                3 -> "Observation: Product movement increased during recent events."
                                                4 -> "Observation: Stock levels fluctuated significantly."
                                                5 -> "Observation: Several products required rapid restocking."
                                                6 -> "Observation: Demand exceeded expectations."
                                                7 -> "Observation: Inventory usage increased noticeably."
                                                else -> "Observation: Supply demand increased recently."
                                            }
                                        }

                                        else ->
                                            "Observation: Food storage conditions appeared acceptable."
                                    }

                                "Storage Manager" ->

                                    when (
                                        gameState.activeScenarioByLocation[selectedLocation]
                                    ) {

                                        "Food Contamination" ->

                                            when (gameState.scenarioTemplateId) {

                                                1 ->
                                                    "Observation: Staff reported several food items were moved after refrigeration issues occurred."

                                                2 ->
                                                    "Observation: Inventory records showed concerns about food storage conditions."

                                                3 ->
                                                    "Observation: Staff discussed difficulties maintaining safe storage temperatures."

                                                4 ->
                                                    "Observation: Recent complaints involved improperly stored food products."

                                                else ->
                                                    "Observation: Staff discussed food storage concerns."
                                            }

                                        else ->
                                            "Observation: Storage operations appeared normal."
                                    }

                                "Cooling Equipment" ->

                                    when (
                                        gameState.activeScenarioByLocation[selectedLocation]
                                    ) {

                                        "Food Contamination" ->

                                            when (gameState.scenarioTemplateId) {

                                                1 ->
                                                    "Observation: Cooling equipment appears to have malfunctioned recently."

                                                2 ->
                                                    "Observation: Temperature logs show irregular storage conditions."

                                                3 ->
                                                    "Observation: Several refrigerated products appear improperly stored."

                                                4 ->
                                                    "Observation: Staff reported recent refrigeration issues."

                                                else ->
                                                    "Observation: Cooling equipment appears unreliable."
                                            }

                                        else ->
                                            "Observation: Cooling equipment appears operational."
                                    }

                                "Doctor" ->

                                    when (
                                        gameState.activeScenarioByLocation[selectedLocation]
                                    ) {

                                        "Food Contamination" ->

                                            when (gameState.scenarioTemplateId) {

                                                1 ->
                                                    "Observation: Most patients reported stomach discomfort rather than respiratory symptoms."

                                                2 ->
                                                    "Observation: Doctors noted digestive symptoms were more common than coughing."

                                                3 ->
                                                    "Observation: Several patients reported nausea shortly after eating."

                                                4 ->
                                                    "Observation: Similar gastrointestinal symptoms appeared across multiple cases."

                                                else ->
                                                    "Observation: Several patients reported stomach-related symptoms."
                                            }

                                        "Ongoing Transmission" -> {

                                            when (gameState.scenarioTemplateId) {

                                                1 ->
                                                    "Observation: New cases continued appearing over multiple days."

                                                2 ->
                                                    "Observation: Patients became ill at different times."

                                                3 ->
                                                    "Observation: Several cases reported recent contact with ill individuals."

                                                4 ->
                                                    "Observation: Case numbers increased steadily throughout the week."

                                                5 ->
                                                    "Observation: New patients continued arriving despite earlier isolation efforts."

                                                6 ->
                                                    "Observation: Cases appeared across multiple households."

                                                7 ->
                                                    "Observation: Doctors noted a continuing chain of infections."

                                                else ->
                                                    "Observation: New cases continued appearing over multiple days."
                                            }
                                        }

                                        "Poor Hygiene" -> {

                                            when (gameState.scenarioTemplateId) {

                                                1 ->
                                                    "Observation: Several cases appeared linked to shared surfaces."

                                                2 ->
                                                    "Observation: Patients reported frequent use of common facilities."

                                                3 ->
                                                    "Observation: Doctors noted concerns regarding sanitation practices."

                                                4 ->
                                                    "Observation: Multiple cases involved individuals sharing equipment."

                                                5 ->
                                                    "Observation: Several patients reported poor cleanliness conditions."

                                                6 ->
                                                    "Observation: Cases appeared concentrated among people sharing facilities."

                                                7 ->
                                                    "Observation: Doctors suspected environmental cleanliness may be a factor."

                                                else ->
                                                    "Observation: Several cases appeared linked to shared surfaces."
                                            }
                                        }

                                        "Crowded Gathering" -> {

                                            when (gameState.scenarioTemplateId) {

                                                1 ->
                                                    "Observation: Many patients attended the same recent event."

                                                2 ->
                                                    "Observation: Several cases shared a common gathering location."

                                                3 ->
                                                    "Observation: Patients reported spending time in crowded areas."

                                                4 ->
                                                    "Observation: A recent public event appeared in multiple case histories."

                                                5 ->
                                                    "Observation: Doctors noted a large overlap in recent social activities."

                                                6 ->
                                                    "Observation: Several patients reported attending the same gathering."

                                                7 ->
                                                    "Observation: Cases appeared connected to a recent high-attendance activity."

                                                else ->
                                                    "Observation: Many patients attended the same recent event."
                                            }
                                        }

                                        else ->
                                            "Observation: Doctors continue monitoring new cases."
                                    }

                                "Patient" ->

                                    when (
                                        gameState.activeScenarioByLocation[selectedLocation]
                                    ) {

                                        "Food Contamination" ->

                                            when (gameState.scenarioTemplateId) {

                                                1 ->
                                                    "Observation: Several patients reported feeling ill after eating earlier in the day."

                                                2 ->
                                                    "Observation: Multiple patients mentioned symptoms beginning after meals."

                                                3 ->
                                                    "Observation: Patients described similar stomach-related symptoms."

                                                4 ->
                                                    "Observation: Several patients reported nausea following food consumption."

                                                else ->
                                                    "Observation: Several patients reported similar digestive symptoms."
                                            }

                                        "Ongoing Transmission" -> {

                                            when (gameState.scenarioTemplateId) {

                                                1 ->
                                                    "Observation: Several patients reported becoming ill after contact with someone already sick."

                                                2 ->
                                                    "Observation: Symptoms appeared days after exposure rather than immediately."

                                                3 ->
                                                    "Observation: Illness spread through families over time."

                                                4 ->
                                                    "Observation: Patients became ill on different days."

                                                5 ->
                                                    "Observation: New cases continued appearing throughout the week."

                                                6 ->
                                                    "Observation: Some patients knew others who had recently become ill."

                                                7 ->
                                                    "Observation: Symptoms appeared gradually across multiple households."

                                                else ->
                                                    "Observation: Several patients reported becoming ill after contact with someone already sick."
                                            }
                                        }

                                        "Poor Hygiene" -> {

                                            when (gameState.scenarioTemplateId) {

                                                1 ->
                                                    "Observation: Patients frequently mentioned dirty shared facilities."

                                                2 ->
                                                    "Observation: Several individuals reported concerns about cleanliness."

                                                3 ->
                                                    "Observation: Shared equipment was commonly used without cleaning."

                                                4 ->
                                                    "Observation: Patients recalled poor sanitation conditions."

                                                5 ->
                                                    "Observation: Frequently touched areas appeared poorly maintained."

                                                6 ->
                                                    "Observation: Several patients reported similar hygiene concerns."

                                                7 ->
                                                    "Observation: Common-use areas were often described as unclean."

                                                else ->
                                                    "Observation: Patients frequently mentioned dirty shared facilities."
                                            }
                                        }

                                        "Crowded Gathering" -> {

                                            when (gameState.scenarioTemplateId) {

                                                1 ->
                                                    "Observation: Many patients attended the same recent event."

                                                2 ->
                                                    "Observation: Several patients remembered being part of a large crowd."

                                                3 ->
                                                    "Observation: Multiple cases involved attendance at the same activity."

                                                4 ->
                                                    "Observation: Patients reported spending time in busy public spaces."

                                                5 ->
                                                    "Observation: Several individuals attended the same gathering."

                                                6 ->
                                                    "Observation: Large public events appeared repeatedly in patient histories."

                                                7 ->
                                                    "Observation: Many patients visited the same crowded location."

                                                else ->
                                                    "Observation: Many patients attended the same recent event."
                                            }
                                        }

                                        else ->
                                            "Observation: Patients are waiting for treatment."
                                    }

                                "Hospital Bed" ->
                                    "Most beds appeared occupied during the visit."

                                else ->
                                    "The investigator noted something worth recording."
                            }

                        if (currentEvidence.contains(observation)) {

                            showObservationPopup = false
                            return@Button
                        }

                        investigatedObjects =
                            investigatedObjects + selectedObject

                        gameState =
                            gameState.copy(

                                investigationPoints =
                                    gameState.investigationPoints - 1,

                                evidenceByLocation =
                                    gameState.evidenceByLocation +
                                            (
                                                    selectedLocation to
                                                            (currentEvidence + observation)
                                                    )
                            )

                        observationMessage =
                            "Observation Recorded"

                        showObservationPopup = false
                    }
                ) {

                    Text(

                        if (gameState.investigationPoints > 0)
                            "Investigate"
                        else
                            "No AP Remaining"
                    )
                }
            },

            dismissButton = {

                Button(

                    onClick = {
                        showObservationPopup = false
                    }

                ) {

                    Text("Close")
                }
            }
        )
    }
}