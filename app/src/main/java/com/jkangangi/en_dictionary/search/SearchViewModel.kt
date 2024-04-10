package com.jkangangi.en_dictionary.search

import android.util.Log
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jkangangi.en_dictionary.app.data.remote.dto.RequestDTO
import com.jkangangi.en_dictionary.app.data.repository.DictionaryRepository
import com.jkangangi.en_dictionary.app.util.NetworkResult
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

//private const val DELAY_TIME = 500L
/**
 * Target string MUST have an input, before and after texts are optional
 */

@Stable
interface SearchTextState {
    val beforeSelection: String
    val selection: String
    val afterSelection: String
}

private class MutableSearchTextState: SearchTextState {
    override var beforeSelection: String by mutableStateOf("")
    override var selection: String by mutableStateOf("")
    override var afterSelection: String by mutableStateOf("")
}

class SearchViewModel(private val repository: DictionaryRepository) : ViewModel() {
    private var regex: Regex? = null

    init {
        viewModelScope.launch {
            regex = Regex("^[a-zA-Z' ]+\$")
            Log.i("SearchVM", "Init block called")
        }
    }

    //uiEvent
    private val _searchState: MutableStateFlow<SearchScreenState> =
        MutableStateFlow(SearchScreenState())

    private val _inputState = MutableSearchTextState()
    val inputState: SearchTextState = _inputState

    private val _uiState =
        MutableStateFlow<SearchResultUiState>(SearchResultUiState.Empty)//MutableSharedFlow
    val uiState = _uiState.asStateFlow()


    private fun updateBeforeTextInput(input: String) {
        _inputState.beforeSelection = input
    }

    private fun updateTargetInput(input: String) {
        _inputState.selection = input
    }

    private fun updateAfterTextInput(input: String) {
        _inputState.afterSelection = input
    }


    fun updateInputEvents(events: SearchInputEvents) {
        when (events) {
            is SearchInputEvents.UpdateBeforeSelection -> {
                updateBeforeTextInput(events.beforeInput)
            }

            is SearchInputEvents.UpdateTarget -> {
                updateTargetInput(events.targetInput)
            }

            is SearchInputEvents.UpdateAfterSelection -> {
                updateAfterTextInput(events.afterInput)
            }
        }
    }

    //errorStates
    val searchState: StateFlow<SearchScreenState> = combine(
        flow = snapshotFlow {  _inputState.beforeSelection },
        flow2 = snapshotFlow {  _inputState.selection },
        flow3 = snapshotFlow { _inputState.afterSelection },
        flow4 = _uiState,
        flow5 = _searchState,
        transform = { beforeSelection, selection, afterSelection, uiState, searchState ->
            SearchScreenState(
                wordItem = searchState.wordItem,
                isLoading = searchState.isLoading,
                serverError = searchState.serverError,
                beforeError = validateInput(beforeSelection),
                targetError = validateInput(selection) && (selection.isNotBlank()),
                afterError = validateInput(afterSelection)
            )
        }
    ).stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000L),
        initialValue = SearchScreenState()
    )


    fun clearState() {
        _searchState.update { SearchScreenState() }
        updateBeforeTextInput("")
        updateTargetInput("")
        updateAfterTextInput("")
    }


    private fun validateInput(input: String): Boolean {
        return if (input.isNotEmpty()) {
            val requiredLength = input.length < 129
            regex?.matches(input) == true && input != "'" && requiredLength
        } else {
            true //optional fields can be empty
        }

    }

    fun findWord() {
        viewModelScope.launch {
            repository.postSearch(
                RequestDTO(
                    textBeforeSelection = _inputState.beforeSelection,
                    selection = _inputState.selection,
                    textAfterSelection = _inputState.afterSelection
                )
            ).collect { result ->
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
    }

    fun doWordSearch() {
        viewModelScope.launch {
            repository.postSearch(
                RequestDTO(
                    textBeforeSelection = _inputState.beforeSelection,
                    selection = _inputState.selection,
                    textAfterSelection = _inputState.afterSelection
                )
            ).collect { result ->
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

    override fun onCleared() {
        super.onCleared()
        regex = null
    }
}
