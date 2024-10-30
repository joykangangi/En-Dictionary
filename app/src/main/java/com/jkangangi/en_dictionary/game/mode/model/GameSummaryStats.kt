package com.jkangangi.en_dictionary.game.mode.model

import kotlinx.serialization.Serializable

@Serializable
data class GameSummaryStats(
    val percentageScore: Int = 0,
    val totalGameTime: String = "0",
    val totalPoints: Int = 0,
    val isExcellent: Boolean = false,
)
