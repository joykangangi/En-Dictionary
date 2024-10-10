package com.jkangangi.en_dictionary.game.util

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
    const val SHOW_WORD_ANIM_MILLIS = 500
    const val MAX_WORD_LENGTH = 8 //to calculate text size and box size

}

enum class GameMode(
    val levelId: Int,
    val levelIcon: ImageVector,
    val wordLength: IntRange,
){
    Hard(
        levelId = R.string.hard,
        levelIcon = Icons.Default.Hardware,
        wordLength = 2..Int.MAX_VALUE
    ),

    Medium(
        levelId = R.string.medium ,
        levelIcon = Icons.Default.TagFaces,
        wordLength = 6..8
    ),

    Easy(
        levelId = R.string.easy,
        levelIcon = Icons.Default.GppGood,
        wordLength = 2..5
    )
}