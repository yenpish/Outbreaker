package com.aaron.outbreaker

data class GameState(

    val day: Int = 1,

    val infection: Int = 10,
    val trust: Int = 60,
    val budget: Int = 50,
    val preparedness: Int = 0,
    val hospital: Int = 80,
    val investigationPoints: Int = 6,
    val visitedBuildings: Set<String> = emptySet(),

    val eventMessage: String = "Welcome to Outbreaker.",

    val improvedVaccines: Boolean = false,
    val contactTracing: Boolean = false,
    val publicHealthEducation: Boolean = false,
    val hospitalExpansion: Boolean = false,

    val rapidTesting: Boolean = false,
    val sanitationInitiative: Boolean = false,
    val publicAwarenessNetwork: Boolean = false,
    val emergencyPlanning: Boolean = false,

    val outbreakLocation: String = "",
    val outbreakScenario: String = "",
    val scenarioTemplateId: Int = 1,
    val outbreakSeverity: Int = 1,
    val lastEffectiveness: String = "",

    val evidenceByLocation: Map<String, List<String>> = emptyMap(),

    val activeScenarioByLocation: Map<String, String> = emptyMap(),

    val investigatedActionsByLocation: Map<String, Set<String>> =
        emptyMap(),

    val suspectedLocation: String = "",
    val suspectedCause: String = "",

    val selectedInterventions: Set<String> =
        emptySet(),

    val latestOutcomeReport: String = "",

    val currentCrisis: String = "",

    val crisisDescription: String = "",

    val gameOver: Boolean = false,
    val gameWon: Boolean = false
)