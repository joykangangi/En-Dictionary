package com.jkangangi.en_dictionary.search


import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
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

data class SearchScreenState(
    val inputState: SearchInputState = SearchInputState(),
    val errorState: SearchInputErrorState = SearchInputErrorState(),
    val networkState: SearchResultUiState = SearchResultUiState.Idle
)

data class SearchInputState(
    val beforeSelection: MutableState<String> = mutableStateOf(""),
    val selection: MutableState<String> = mutableStateOf(""),
    val afterSelection: MutableState<String> = mutableStateOf(""),
)


class SearchViewModel(private val repository: DictionaryRepository) : ViewModel() {
    private val regex = Regex("^[a-zA-Z' ]+\$")

    private val _resultUiState = MutableStateFlow<SearchResultUiState>(SearchResultUiState.Idle)
    private val _searchInputState = SearchInputState()
    //errorStates
    private val _inputErrorState: StateFlow<SearchInputErrorState> = combine(
        flow = snapshotFlow { _searchInputState.beforeSelection.value },
        flow2 = snapshotFlow { _searchInputState.selection.value },
        flow3 = snapshotFlow { _searchInputState.afterSelection.value },
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

    val searchScreenState = combine(
        flow = snapshotFlow { _searchInputState },
        flow2 = _inputErrorState,
        flow3 = _resultUiState,
        transform = { inputState, inputErrorState, networkState ->
            SearchScreenState(
                inputState = inputState,
                errorState = inputErrorState,
                networkState = networkState
            )
        }
    ).stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = SearchScreenState()
    )

    private fun updateBeforeTextInput(input: String) {
        _searchInputState.beforeSelection.value = input
    }

    private fun updateTargetInput(input: String) {
        _searchInputState.selection.value = input
    }

    private fun updateAfterTextInput(input: String) {
        _searchInputState.afterSelection.value = input
    }

    private fun updateInputEvents(events: SearchInputEvents) {
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
    fun performEvent(event: SearchScreenEvent) {
        when(event) {
            is SearchScreenEvent.UpdateQueries -> {
                updateInputEvents(events = event.updateQueries)
            }

            SearchScreenEvent.DoSearch -> {
                getSearchResults()
            }
        }
    }



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
   private fun getSearchResults() {
        _resultUiState.update { SearchResultUiState.Loading }
        val queries = RequestDTO(
            textBeforeSelection = _searchInputState.beforeSelection.value,
            selection = _searchInputState.selection.value,
            textAfterSelection = _searchInputState.afterSelection.value
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
