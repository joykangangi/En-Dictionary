package com.jkangangi.en_dictionary.game.mode.model

import kotlinx.serialization.Serializable

@Serializable
data class GameSummaryStats(
    val percentageScore: Int,
    val totalGameTime: Int,
    val totalPoints: Int,
    val isExcellent: Boolean,
)
