package com.jkangangi.en_dictionary.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jkangangi.en_dictionary.app.data.local.room.DictionaryEntity
import com.jkangangi.en_dictionary.app.data.remote.dto.RequestDTO
import com.jkangangi.en_dictionary.app.data.repository.DictionaryRepository
import com.jkangangi.en_dictionary.app.util.NetworkResult
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

//private const val DELAY_TIME = 500L
/**
 * Target string MUST have an input, before and after texts are optional
 */

class SearchViewModel(private val repository: DictionaryRepository) : ViewModel() {

    private val _queries = MutableStateFlow(RequestDTO())
    private val _searchState: MutableStateFlow<SearchScreenState> =
        MutableStateFlow(SearchScreenState())
    val searchState = _searchState.asStateFlow()

    private val _uiState = MutableStateFlow<SearchResultUiState>(SearchResultUiState.Empty)
    val uiState = _uiState.asStateFlow()


    fun updateQuery(queries: RequestDTO) {
        _queries.update { queries }
        _searchState.update {
            it.copy(
                requests = queries,
                beforeError = validateInput(_queries.value.textBeforeSelection),
                targetError = validateInput(_queries.value.selection) && _queries.value.selection.isNotBlank(),
                afterError = validateInput(_queries.value.textAfterSelection)
            )
        }
    }

    fun clearState() {
        _searchState.update { SearchScreenState() }
    }


    private fun validateInput(input: String): Boolean {
        return if (input.isNotEmpty()) {
            val requiredLength = input.length < 129
            val regex = Regex("^[a-zA-Z' ]+\$") //Input only has letters/spaces/apostrophes
            regex.matches(input) && input != "'" && requiredLength
        } else {
            true //optional fields can be empty
        }
    }

    fun findWord() {
        viewModelScope.launch {
            repository.postSearch(_queries.value).collect { result ->
                _uiState.update { state ->
                    when (result) {
                        is NetworkResult.Error -> {
                            SearchResultUiState.Error(serverError = result.message ?: "")
                        }

                        is NetworkResult.Loading -> {
                            SearchResultUiState.Loading
                        }

                        is NetworkResult.Success -> {
                            if (result.data != null) {
                                SearchResultUiState.Success(wordItem = result.data)
                            } else SearchResultUiState.Empty
                        }
                    }
                }
            }
        }

        fun doWordSearch() {
            viewModelScope.launch {
                repository.postSearch(request = _queries.value).collect { result ->
                    //pipe Flow emissions into StateFlow
                    _searchState.update { state ->
                        when (result) {
                            is NetworkResult.Success -> {
                                state.copy(
                                    wordItem = result.data,
                                    isLoading = false,
                                )
                            }

                            is NetworkResult.Error -> {
                                state.copy(
                                    serverError = result.message
                                        ?: "Unexpected error occurred, try again.",
                                    isLoading = false
                                )
                            }

                            is NetworkResult.Loading -> {
                                state.copy(
                                    isLoading = true,
                                )
                            }


                        }
                    }
                }
            }
        }
    }
