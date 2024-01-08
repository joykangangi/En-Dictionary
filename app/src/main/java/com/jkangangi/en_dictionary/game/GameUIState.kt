package com.jkangangi.en_dictionary.game

import com.jkangangi.en_dictionary.app.data.local.DictionaryEntity
import kotlinx.collections.immutable.ImmutableSet
import kotlinx.collections.immutable.persistentSetOf

data class GameUIState(
    val isEmpty: Boolean = false,
    val scrambledWord: String = "",
    val hint: String = "",
    val wordCount: Int = 0,
    val score: Int = 0,
    val btnEnabled: Boolean = false,
    val isGameOver: Boolean = false,
    val hintClick: Int = 0,
)