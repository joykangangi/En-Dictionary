package com.jkangangi.en_dictionary.game.mode.model

import com.jkangangi.en_dictionary.R
import com.jkangangi.en_dictionary.game.GameRoute
import com.jkangangi.en_dictionary.game.util.GameConstants

enum class GameMode(
    val levelId: Int,
    val wordLength: IntRange,
    val route: GameRoute,
    val passMark: Int,
){

    Easy(
        levelId = R.string.easy,
        wordLength = 2..5,
        route = GameRoute.EasyGameModeRoute,
        passMark = GameConstants.EASY_MODE_PASSMARK,
    ),

    Medium(
        levelId = R.string.medium ,
        wordLength = 6..8,
        route = GameRoute.MediumGameModeRoute,
        passMark = GameConstants.MEDIUM_MODE_PASSMARK
    ),

    Hard(
        levelId = R.string.hard,
        wordLength = 2..Int.MAX_VALUE,
        route = GameRoute.HardGameModeRoute,
        passMark = GameConstants.HARD_MODE_PASSMARK
    ),

}