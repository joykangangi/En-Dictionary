package com.jkangangi.en_dictionary.search


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
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.mapLatest
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.net.UnknownHostException
import java.net.UnknownServiceException

/**
 * Target string MUST have an input, before and after texts are optional
 *
 * states:
 * 1. TextFields-[] -> inputStates
 * 2. TextFieldsErrors-[SearchInputErrorState] -> inputErrorState
 * 3. NetworkResult - [SearchResultUiState] -> resultUiState
 */

class SearchViewModel(private val repository: DictionaryRepository) : ViewModel() {
    private val regex = Regex("^[a-zA-Z' ]+\$")

    var beforeSelection: String by mutableStateOf("")
        private set

    var selection: String by mutableStateOf("")
        private set

    var afterSelection: String by mutableStateOf("")
        private set

    private val _resultUiState = MutableStateFlow<SearchResultUiState>(SearchResultUiState.Idle)
    val resultUiState = _resultUiState.asStateFlow()

    private fun updateBeforeTextInput(input: String) {
        beforeSelection = input
    }

    private fun updateTargetInput(input: String) {
        selection = input
    }

    private fun updateAfterTextInput(input: String) {
        afterSelection = input
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
        flow = snapshotFlow { beforeSelection },
        flow2 = snapshotFlow { selection },
        flow3 = snapshotFlow { afterSelection },
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
        _resultUiState.update { SearchResultUiState.Idle }
    }


    private fun validateInput(input: String): Boolean {
        return if (input.isNotEmpty()) {
            val requiredLength = input.length < 129
            regex.matches(input) && input != "'" && requiredLength
        } else {
            true //optional fields can be empty
        }
    }


//    @OptIn(FlowPreview::class, ExperimentalCoroutinesApi::class)
//    val resultUiState: StateFlow<SearchResultUiState> = combine(
//        flow = snapshotFlow { beforeSelection },
//        flow2 = snapshotFlow { selection },
//        flow3 = snapshotFlow { afterSelection },
//        transform = { beforeSelection, selection, afterSelection ->
//            RequestDTO(
//                textBeforeSelection = beforeSelection,
//                selection = selection,
//                textAfterSelection = afterSelection
//            )
//
//        }
//    ).debounce(1000)
//        .mapLatest { repository.postSearch(request = it) }
//        .mapLatest { result ->
//            when (result) {
//                is NetworkResult.Success -> {
//                    if (result.data != null) {
//                        SearchResultUiState.Success(
//                            wordItem = result.data
//                        )
//                    } else {
//                        SearchResultUiState.Idle
//                    }
//                }
//
//                is NetworkResult.Failure -> {
//                    val serverError = when (result.throwable) {
//                        is RedirectResponseException -> "Redirecting to a different page. Please wait."
//                        is UnknownHostException -> "Unable to connect to the server. Check your internet connection"
//                        is UnknownServiceException -> "An unexpected server error. Please try again later."
//                        else -> "An error occurred. Please try again later."
//                    }
//                    SearchResultUiState.Error(serverError = serverError)
//
//                }
//
//                is NetworkResult.EmptyBody -> {
//                    SearchResultUiState.EmptyBody
//                }
//            }
//        }
//        .stateIn(
//            scope = viewModelScope,
//            started = SharingStarted.WhileSubscribed(5_000L),
//            initialValue = SearchResultUiState.Idle
//        )


    @OptIn(ExperimentalCoroutinesApi::class)
    fun getSearchResults() {
        _resultUiState.update { SearchResultUiState.Loading }
        val queries = RequestDTO(
            textBeforeSelection = beforeSelection,
            selection = selection,
            textAfterSelection = afterSelection
        )

        viewModelScope.launch {
            flowOf(queries)
                .mapLatest { request -> repository.postSearch(request) }
                .collect { result ->
                    _resultUiState.update {
                        when (result) {
                            is NetworkResult.Success -> {
                                if (result.data != null) {
                                    SearchResultUiState.Success(
                                        wordItem = result.data
                                    )
                                } else {
                                    SearchResultUiState.Idle
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
}
