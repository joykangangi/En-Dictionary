package com.jkangangi.en_dictionary.search

import com.jkangangi.en_dictionary.app.data.model.Word
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf


data class SearchScreenState(
    val wordItems: ImmutableList<Word> = persistentListOf(),
    val isLoading: Boolean = false
)
