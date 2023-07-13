package com.jkangangi.en_dictionary.search

import com.jkangangi.en_dictionary.app.data.model.Dictionary

data class SearchScreenState(
   // val wordItems: ImmutableList<Word> = persistentListOf(),
    val wordItems: List<Dictionary?> = emptyList(),
    val isLoading: Boolean = false,
    val error: String = "",
)