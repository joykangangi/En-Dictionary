package com.jkangangi.en_dictionary.game.mode.model

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.GppGood
import androidx.compose.material.icons.filled.Hardware
import androidx.compose.material.icons.filled.TagFaces
import androidx.compose.ui.graphics.vector.ImageVector
import com.jkangangi.en_dictionary.R
import com.jkangangi.en_dictionary.game.GameRoute
import com.jkangangi.en_dictionary.game.util.GameConstants

enum class GameMode(
    val levelId: Int,
    val levelIcon: ImageVector,
    val wordLength: IntRange,
    val route: GameRoute,
    val passMark: Int,
){
    Hard(
        levelId = R.string.hard,
        levelIcon = Icons.Default.Hardware,
        wordLength = 2..Int.MAX_VALUE,
        route = GameRoute.HardGameModeRoute,
        passMark = GameConstants.HARD_MODE_PASSMARK
    ),

    Medium(
        levelId = R.string.medium ,
        levelIcon = Icons.Default.TagFaces,
        wordLength = 6..8,
        route = GameRoute.MediumGameModeRoute,
        passMark = GameConstants.MEDIUM_MODE_PASSMARK
    ),

    Easy(
        levelId = R.string.easy,
        levelIcon = Icons.Default.GppGood,
        wordLength = 2..5,
        route = GameRoute.EasyGameModeRoute,
        passMark = GameConstants.EASY_MODE_PASSMARK
    )
}