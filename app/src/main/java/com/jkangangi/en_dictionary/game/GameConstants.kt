package com.jkangangi.en_dictionary.game

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.GppGood
import androidx.compose.material.icons.filled.Hardware
import androidx.compose.material.icons.filled.TagFaces
import androidx.compose.ui.graphics.vector.ImageVector
import com.jkangangi.en_dictionary.R

object GameConstants {
    const val SCORE_INCREASE = 5
    const val SKIP_DECREASE = 4
    const val HINT_DECREASE = 3
    const val MAX_WORDS = 6
    const val MAX_SCORE = 30
    const val TOTAL_GAME_TIME = 60 //60 seconds
    const val THREE_QUARTER = (TOTAL_GAME_TIME / 3) //20
}

enum class GameMode(
    val levelId: Int,
    val levelIcon: ImageVector
){
    Hard(levelId = R.string.hard, levelIcon = Icons.Default.Hardware),
    Medium(levelId = R.string.medium , levelIcon = Icons.Default.TagFaces),
    Easy(levelId = R.string.easy, levelIcon = Icons.Default.GppGood)
}