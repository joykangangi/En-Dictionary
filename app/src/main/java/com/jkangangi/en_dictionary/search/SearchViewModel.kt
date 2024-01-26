package com.jkangangi.en_dictionary.search

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jkangangi.en_dictionary.app.data.remote.dto.RequestDTO
import com.jkangangi.en_dictionary.app.data.repository.DictionaryRepository
import com.jkangangi.en_dictionary.app.util.NetworkResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

//private const val DELAY_TIME = 500L
/**
 * Target string MUST have an input, before and after texts are optional
 */

@HiltViewModel
class SearchViewModel @Inject constructor(private val repository: DictionaryRepository) :
    ViewModel() {

    private val _queries = MutableStateFlow(RequestDTO())
    private val _searchState: MutableStateFlow<SearchScreenState> = MutableStateFlow(SearchScreenState())
    val searchState = _searchState.asStateFlow()

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


    private fun validateInput(input: String): Boolean {
        val isValid =
            if (input.isNotEmpty()) {
                val requiredLength = input.length < 129
                val regex = Regex("^[a-zA-Z' ]+\$") //Input only has letters/spaces/apostrophes
                regex.matches(input) && input != "'" && requiredLength
            } else {
                true //optional fields can be empty
            }
        return isValid
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
                                serverError = result.message ?: "Unexpected error occurred, try again.",
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


    fun closeClient() {
        Log.d("SearchVM", "calling close from the viewModel...")
        repository.closeClient()
    }

    override fun onCleared() {
        super.onCleared()
        Log.d("SearchVM", "Search view model onCleared called...")
        closeClient()
    }
}
