package com.jkangangi.en_dictionary.game.mode

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.Stable
import com.jkangangi.en_dictionary.app.data.local.room.DictionaryEntity
import com.jkangangi.en_dictionary.game.util.GameConstants.TOTAL_GAME_TIME

@Stable
@Immutable
data class GameInputState(
    val wordItemsSize: Int = 0,
    val wordItem: DictionaryEntity? = null,
    val scrambledWord: String = "",
    val hint: String = "",
    val wordCount: Int = 0,
    val score: Int = 0,
    val btnEnabled: Boolean = false,
    val showHint: Boolean = false,
    val timeLeft: Int = TOTAL_GAME_TIME
)