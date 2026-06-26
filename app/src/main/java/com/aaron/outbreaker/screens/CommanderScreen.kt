package com.aaron.outbreaker.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.aaron.outbreaker.GameState
import androidx.compose.ui.Alignment

@Composable
fun CommanderScreen(
    gameState: GameState,
    onUpdate: (GameState) -> Unit,
    onNavigateOutcome: () -> Unit,
    onBack: () -> Unit
) {

    var selectedActions by remember {
        mutableStateOf(
            gameState.selectedInterventions
        )
    }

    val maxOP =
        if (gameState.emergencyPlanning)
            4
        else
            3

    val interventionCosts = mapOf(
        "Hygiene Program" to 1,
        "Mass Testing" to 2,
        "Awareness Campaign" to 1,
        "School Closure" to 3
    )

    val usedOP =
        selectedActions.sumOf {
            interventionCosts[it] ?: 0
        }

    val remainingOP =
        maxOP - usedOP

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp)
    ) {

        Text(
            text = "COMMAND CENTER",
            fontSize = 28.sp
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            "Hypothesis:"
        )

        Text(
            gameState.suspectedCause
        )

        Spacer(modifier = Modifier.height(20.dp))

        Text(
            "Operations Points Remaining: $remainingOP"
        )

        Spacer(modifier = Modifier.height(8.dp))

        Card(
            modifier = Modifier.fillMaxWidth()
        ) {

            Column(
                modifier = Modifier.padding(16.dp)
            ) {

        Spacer(modifier = Modifier.height(16.dp))

        interventionCosts.forEach { (name, cost) ->

            val checked =
                selectedActions.contains(name)

            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {

                Checkbox(
                    checked = checked,
                    onCheckedChange = { isChecked ->

                        val newSelection =
                            if (isChecked) {

                                val newList =
                                    selectedActions + name

                                val totalCost =
                                    newList.sumOf {
                                        interventionCosts[it] ?: 0
                                    }

                                if (totalCost <= maxOP)
                                    newList
                                else
                                    selectedActions

                            } else {

                                selectedActions - name
                            }

                        selectedActions = newSelection
                    }

                )


                Spacer(
                    modifier = Modifier.width(8.dp)
                )

                Text(
                    text = "$name (Cost: $cost)"
                )
            }
        }
            }

            Spacer(
                modifier = Modifier.height(4.dp)
            )
        }

        Spacer(modifier = Modifier.height(20.dp))

        Button(
            onClick = {

                var infectionChange = 0
                var trustChange = 0
                var budgetChange = 0

                val testingBonus =
                    if (gameState.rapidTesting) 1 else 0

                val hygieneBonus =
                    if (gameState.sanitationInitiative) 1 else 0

                val awarenessBonus =
                    if (gameState.publicAwarenessNetwork) 1 else 0

                val actualLocation =
                    gameState.activeScenarioByLocation
                        .entries
                        .firstOrNull {
                            it.value != "Normal"
                        }
                        ?.key ?: "Unknown"

                val actualScenario =
                    gameState.outbreakScenario

                when (actualScenario) {

                    "Poor Hygiene" -> {

                        if (selectedActions.contains("Hygiene Program")) {
                            infectionChange -= (3 + hygieneBonus)
                            trustChange += 1
                            budgetChange -= 1
                        }

                        if (selectedActions.contains("Awareness Campaign")) {
                            infectionChange -= 1
                            trustChange += 1
                            budgetChange -= 1
                        }

                        if (selectedActions.contains("Mass Testing")) {
                            trustChange -= 1
                            budgetChange -= 2
                        }

                        if (selectedActions.contains("School Closure")) {
                            trustChange -= 3
                            budgetChange -= 1
                        }
                    }

                    "Ongoing Transmission" -> {

                        if (selectedActions.contains("Mass Testing")) {
                            infectionChange -= (3 + testingBonus)
                            budgetChange -= 2
                        }

                        if (selectedActions.contains("School Closure")) {
                            infectionChange -= 2
                            trustChange -= 2
                            budgetChange -= 1
                        }

                        if (selectedActions.contains("Awareness Campaign")) {
                            infectionChange -= 1
                            trustChange += 1
                            budgetChange -= 1
                        }

                        if (selectedActions.contains("Hygiene Program")) {
                            trustChange -= 1
                            budgetChange -= 1
                        }
                    }

                    "Crowded Gathering" -> {

                        if (selectedActions.contains("School Closure")) {
                            infectionChange -= 4
                            trustChange -= 2
                            budgetChange -= 1
                        }

                        if (selectedActions.contains("Awareness Campaign")) {
                            infectionChange -= 1
                            trustChange += 1
                            budgetChange -= 1
                        }

                        if (selectedActions.contains("Mass Testing")) {
                            trustChange -= 1
                            budgetChange -= 2
                        }

                        if (selectedActions.contains("Hygiene Program")) {
                            trustChange -= 1
                            budgetChange -= 1
                        }
                    }

                    "Food Contamination" -> {

                        if (selectedActions.contains("Hygiene Program")) {
                            infectionChange -= (3 + hygieneBonus)
                            budgetChange -= 1
                        }

                        if (selectedActions.contains("Mass Testing")) {
                            infectionChange -= 1
                            budgetChange -= 2
                        }

                        if (selectedActions.contains("Awareness Campaign")) {
                            trustChange += 0
                            budgetChange -= 1
                        }

                        if (selectedActions.contains("School Closure")) {
                            infectionChange += 0
                            trustChange -= 2
                            budgetChange -= 1
                        }
                    }
                }

                val causeCorrect =
                    gameState.suspectedCause
                        .replace("🧼 ", "")
                        .replace("👥 ", "")
                        .replace("🍽️ ", "")
                        .replace("😷 ", "") ==
                            actualScenario

                if (!causeCorrect) {

                    infectionChange += 3

                    trustChange -= 2
                }

                val hypothesisAssessment =
                    if (causeCorrect)
                        "Correct"
                    else
                        "Incorrect"

                val explanation =
                    when (actualScenario) {

                        "Poor Hygiene" ->
                            "Evidence pointed toward sanitation problems. Hygiene programs are usually the most effective response."

                        "Ongoing Transmission" ->
                            "Evidence suggested disease transmission through close contact and respiratory symptoms. Testing and restrictions are often effective."

                        "Crowded Gathering" ->
                            "Large gatherings increase transmission opportunities. Limiting crowd exposure can reduce spread."

                        "Food Contamination" ->
                            "Illness reports linked to food sources suggest contamination. Hygiene inspections and food safety measures are important."

                        else ->
                            "No significant outbreak was detected."
                    }

                when (gameState.currentCrisis) {

                    "Festival Week" -> {

                        if (actualScenario == "Crowded Gathering") {

                            infectionChange += 1
                        }
                    }

                    "Hospital Staff Shortage" -> {

                        if (actualScenario == "Ongoing Transmission") {

                            infectionChange += 1
                        }
                    }

                    "Public Panic" -> {

                        trustChange -= 2
                    }

                    "Misinformation Campaign" -> {

                        if (
                            selectedActions.contains(
                                "Awareness Campaign"
                            )
                        ) {

                            infectionChange += 1
                        }
                    }
                }

                val crisisImpact =
                    when (gameState.currentCrisis) {

                        "Festival Week" ->

                            if (actualScenario == "Crowded Gathering")
                                "Festival Week increased transmission risk and made containment more difficult."
                            else
                                "Festival Week had little impact on this week's outbreak."

                        "Hospital Staff Shortage" ->

                            if (actualScenario == "Ongoing Transmission")
                                "Hospital Staff Shortage reduced healthcare response effectiveness."
                            else
                                "Hospital Staff Shortage had little impact on this week's outbreak."

                        "Public Panic" ->

                            "Public Panic reduced public confidence and trust."

                        "Misinformation Campaign" ->

                            if (selectedActions.contains("Awareness Campaign"))
                                "Misinformation reduced the effectiveness of public awareness efforts."
                            else
                                "Misinformation Campaign had little impact on this week's response."

                        else ->
                            "No significant crisis affected this week's response."
                    }

                val effectiveness =
                    when {

                        infectionChange <= -3 ->
                            "Highly Effective"

                        infectionChange <= -2 ->
                            "Effective"

                        infectionChange <= -1 ->
                            "Partially Effective"

                        else ->
                            "Ineffective"
                    }

                val report = buildString {

                    append("WEEK ${gameState.day} CASE ANALYSIS\n\n")

                    append(
                        "Your Theory:\n" +
                                "${gameState.suspectedCause}\n\n"
                    )

                    if (hypothesisAssessment == "Incorrect") {

                        append(
                            "Actual Cause:\n" +
                                    "$actualScenario\n\n"
                        )
                    }

                    append(
                        "Assessment:\n" +
                                "$hypothesisAssessment\n\n"
                    )

                    append(
                        "Intervention Result:\n" +
                                "$effectiveness\n\n"
                    )

                    append(
                        "Impact:\n" +
                                "Infection: $infectionChange\n" +
                                "Trust: $trustChange\n" +
                                "Budget: $budgetChange\n\n"
                    )

                    append(
                        "Crisis Impact:\n" +
                                crisisImpact +
                                "\n\n"
                    )

                    append("Analysis:\n")

                    append(explanation + "\n\n")

                    append("Key Lesson:\n")

                    when (actualScenario) {

                        "Poor Hygiene" -> {

                            append(
                                "Poor Hygiene outbreaks are often identified through recurring facility, maintenance, and sanitation concerns rather than a single obvious symptom.\n\n"
                            )
                        }

                        "Crowded Gathering" -> {

                            append(
                                "Crowded Gathering outbreaks are often linked to congestion, unusually high activity levels, and over-capacity events.\n\n"
                            )
                        }

                        "Food Contamination" -> {

                            append(
                                "Food Contamination outbreaks often show shared exposure patterns, where multiple affected groups can be connected to a common source.\n\n"
                            )
                        }

                        "Ongoing Transmission" -> {

                            append(
                                "Respiratory Spread outbreaks often appear as increasing illness across multiple groups, rising absenteeism, and growing healthcare demand.\n\n"
                            )
                        }
                    }
                }

                onUpdate(
                    gameState.copy(
                        selectedInterventions =
                            selectedActions,

                        lastEffectiveness =
                            effectiveness,

                        outbreakLocation =
                            actualLocation,

                        outbreakScenario =
                            actualScenario,

                        infection =
                            (gameState.infection + infectionChange)
                                .coerceAtLeast(0),

                        trust =
                            (gameState.trust + trustChange)
                                .coerceAtLeast(0),

                        budget =
                            (gameState.budget + budgetChange)
                                .coerceAtLeast(0),

                        latestOutcomeReport =
                            report
                    )
                )

                onNavigateOutcome()
            }
        ) {
            Text("Confirm Response")
        }

        Spacer(modifier = Modifier.height(12.dp))

        Button(
            onClick = onBack
        ) {
            Text("Back")
        }
    }
}