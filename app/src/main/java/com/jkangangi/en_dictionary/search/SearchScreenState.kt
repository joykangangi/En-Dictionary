package com.jkangangi.en_dictionary.search

import com.jkangangi.en_dictionary.app.data.local.room.DictionaryEntity

data class SearchScreenState(
    val wordItem: DictionaryEntity? = null,
    val isLoading: Boolean = false,
    val serverError: String? = null,
    val beforeError: Boolean = false,
    val targetError: Boolean = false,
    val afterError: Boolean = false
)