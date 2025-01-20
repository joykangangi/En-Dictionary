package com.jkangangi.en_dictionary.game.mode.model

import com.jkangangi.en_dictionary.game.util.GameConstants

enum class GameModeDetails(
    val wordLength: IntRange,
    val passMark: Int,
) {
    Easy(
        wordLength = 2..5,
        passMark = GameConstants.EASY_MODE_PASSMARK,
    ),

    Medium(
        wordLength = 6..8,
        passMark = GameConstants.MEDIUM_MODE_PASSMARK
    ),

    Hard(
        wordLength = 2..Int.MAX_VALUE,
        passMark = GameConstants.HARD_MODE_PASSMARK
    )
}
