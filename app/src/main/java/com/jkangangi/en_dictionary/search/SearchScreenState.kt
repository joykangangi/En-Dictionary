package com.jkangangi.en_dictionary.search

import com.jkangangi.en_dictionary.app.data.model.Dictionary
import com.jkangangi.en_dictionary.app.data.remote.dto.RequestDTO

data class SearchScreenState(
   // val wordItems: ImmutableList<Word> = persistentListOf(),
    val wordItem: Dictionary? = null,
    val isLoading: Boolean = false,
    val serverError: String = "",
    val requests: RequestDTO = RequestDTO(),
    val beforeError: Boolean = false,
    val targetError: Boolean = false,
    val afterError: Boolean = false
)