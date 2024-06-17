package com.jkangangi.en_dictionary.search

sealed interface SearchScreenEvent {

    data class UpdateQueries(val updateQueries: SearchInputEvents): SearchScreenEvent

    data object DoSearch: SearchScreenEvent

}