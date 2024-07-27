package com.jkangangi.en_dictionary.game

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.Stable
import com.jkangangi.en_dictionary.app.data.local.room.DictionaryEntity

@Stable
@Immutable
data class GameUIState(
    val wordItemsSize: Int = 0,
    val wordItem: DictionaryEntity? = null,
    val scrambledWord: String = "",
    val hint: String = "",
    val wordCount: Int = 0,
    val score: Int = 0,
    val btnEnabled: Boolean = false,
    val showHint: Boolean = false,
    val timeLeft: Long = 60_000L
)