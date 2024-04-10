package com.jkangangi.en_dictionary.search

sealed interface SearchInputEvents {
    data class UpdateBeforeSelection(val beforeInput: String) : SearchInputEvents
    data class UpdateTarget(val targetInput: String) : SearchInputEvents
    data class UpdateAfterSelection(val afterInput: String) : SearchInputEvents
}