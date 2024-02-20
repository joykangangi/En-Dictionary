package com.jkangangi.en_dictionary.game

import androidx.compose.runtime.Stable
import com.jkangangi.en_dictionary.app.data.local.room.DictionaryEntity
import kotlinx.collections.immutable.ImmutableSet
import kotlinx.collections.immutable.persistentSetOf

@Stable
data class GameUIState(
    val wordItems: ImmutableSet<DictionaryEntity>? = persistentSetOf(),
    val wordItem: DictionaryEntity? = null,
    val scrambledWord: String = "",
    val hint: String = "",
    val wordCount: Int = 0,
    val score: Int = 0,
    val nextEnabled: Boolean = false,
    val isGameOver: Boolean = false,
    val showSubmit: Boolean = false,
    val showHint: Boolean = false,
)