package com.jkangangi.en_dictionary.search

data class SearchInputErrorState(
    val beforeError: Boolean = false,
    val targetError: Boolean = false,
    val afterError: Boolean = false
)