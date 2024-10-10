package com.jkangangi.en_dictionary.game.mode

sealed interface GameUIState {

    data object WordLoading: GameUIState

    data object SmallWordCount: GameUIState // < 5

    data object WordsFound: GameUIState

    data object WordsNotFound: GameUIState
 }