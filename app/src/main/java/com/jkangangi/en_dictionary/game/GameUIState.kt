package com.jkangangi.en_dictionary.game

import com.jkangangi.en_dictionary.app.data.local.DictionaryEntity

data class GameUIState(
    val dictionary: DictionaryEntity = DictionaryEntity(),
    val wordCount: Int = 0,
    val score: Int = 0,
    val btnEnabled: Boolean = false,
    val isGameOver: Boolean = false,
    val hintClick: Int = 0,
)