package com.jkangangi.en_dictionary.game

import com.jkangangi.en_dictionary.app.data.local.room.DictionaryEntity
import kotlinx.collections.immutable.persistentListOf

data class GameUIState(
    val wordItems: List<DictionaryEntity>? = persistentListOf(),
    val wordItem: DictionaryEntity? = null,
    val scrambledWord: String = "",
    val hint: String = "",
    val wordCount: Int = 0,
    val score: Int = 0,
    val nextEnabled: Boolean = false,
    val isGameOver: Boolean = false,
    val isGameOn: Boolean = false,
    val showSubmit: Boolean = false,
    val showHint: Boolean = false,
)