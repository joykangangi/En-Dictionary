package com.jkangangi.en_dictionary.search

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
import io.ktor.client.plugins.RedirectResponseException
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.net.UnknownHostException
import java.net.UnknownServiceException

//private const val DELAY_TIME = 500L
/**
 * Target string MUST have an input, before and after texts are optional
 *
 * states:
 * 1. TextFields-[SearchTextState] -> inputStates
 * 2. TextFieldsErrors-[SearchInputErrorState] -> inputErrorState
 * 2. NetworkResult - [SearchResultUiState] -> resultUiState
 * 3.
 */

@Stable
interface SearchTextState {
    val beforeSelection: String
    val selection: String
    val afterSelection: String
}

private class MutableSearchTextState : SearchTextState {
    override var beforeSelection: String by mutableStateOf("")
    override var selection: String by mutableStateOf("")
    override var afterSelection: String by mutableStateOf("")
}

class SearchViewModel(private val repository: DictionaryRepository) : ViewModel() {
    private var regex: Regex? = null

    init {
        viewModelScope.launch {
            regex = Regex("^[a-zA-Z' ]+\$")
        }
    }

    private val _inputState = MutableSearchTextState()
    val inputState: SearchTextState = _inputState

    private val _resultUiState = MutableStateFlow<SearchResultUiState>(SearchResultUiState.Empty)
    val resultUiState = _resultUiState.asStateFlow()


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
    val inputErrorState: StateFlow<SearchInputErrorState> = combine(
        flow = snapshotFlow { _inputState.beforeSelection },
        flow2 = snapshotFlow { _inputState.selection },
        flow3 = snapshotFlow { _inputState.afterSelection },
        transform = { beforeSelection, selection, afterSelection ->
            SearchInputErrorState(
                beforeError = validateInput(beforeSelection),
                targetError = validateInput(selection) && (selection.isNotBlank()),
                afterError = validateInput(afterSelection)
            )
        }
    ).stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000L),
        initialValue = SearchInputErrorState()
    )

    //clear txt fields
    fun clearState() {
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


    fun getSearchResults() {
        _resultUiState.update { SearchResultUiState.Loading }
        val queries = RequestDTO(
            textBeforeSelection = _inputState.beforeSelection,
            selection = _inputState.selection,
            textAfterSelection = _inputState.afterSelection
        )

        viewModelScope.launch {
            flowOf(queries)
                .map { request -> repository.postSearch(request) }
                .collect { result ->
                    _resultUiState.update {
                        when (result) {
                            is NetworkResult.Success -> {
                                if (result.data != null) {
                                    SearchResultUiState.Success(
                                        wordItem = result.data
                                    )
                                } else {
                                    SearchResultUiState.Empty
                                }
                            }

                            is NetworkResult.Failure -> {
                                val serverError = when (result.throwable) {
                                    is RedirectResponseException -> "Redirecting to a different page. Please wait."
                                    is UnknownHostException -> "Unable to connect to the server. Check your internet connection"
                                    is UnknownServiceException -> "An unexpected server error. Please try again later."
                                    else -> "An error occurred. Please try again later."
                                }
                                SearchResultUiState.Error(serverError = serverError)

                            }

                            is NetworkResult.EmptyBody -> {
                                SearchResultUiState.EmptyBody
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
