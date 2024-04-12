package com.jkangangi.en_dictionary.search

import com.jkangangi.en_dictionary.app.data.local.room.DictionaryEntity

sealed interface SearchResultUiState {
    data class Success(val wordItem: DictionaryEntity): SearchResultUiState
    data object EmptyBody: SearchResultUiState
    data object Loading: SearchResultUiState
    data class Error(val serverError: String): SearchResultUiState
    data object Empty: SearchResultUiState
}