package com.jkangangi.en_dictionary.game

data class GameUIState(
    val scrambledWord: String = "",
    val hint: String = "",
    val wordCount: Int = 0,
    val score: Int = 0,
    val hintClick: Int = 0,
    val nextEnabled: Boolean = false,
    val isGameOver: Boolean = false,
    val isEmpty: Boolean = false,
    val showSubmit: Boolean = false,
)