package com.jkangangi.en_dictionary.search

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jkangangi.en_dictionary.app.data.remote.dto.RequestDTO
import com.jkangangi.en_dictionary.app.data.repository.DictionaryRepositoryImpl
import com.jkangangi.en_dictionary.app.util.NetworkResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

//private const val DELAY_TIME = 500L

@HiltViewModel
class SearchViewModel @Inject constructor(private val repository: DictionaryRepositoryImpl) :
    ViewModel() {

    private val _queries = MutableStateFlow(RequestDTO())
    private val _searchState = MutableStateFlow(SearchScreenState())
    val searchState = _searchState.asStateFlow()

    fun updateQuery(queries: RequestDTO) {
        _queries.update { queries }
        _searchState.update { it.copy(requests = queries) }
        _searchState.update { it.copy(beforeError = validateInput(_queries.value.textBeforeSelection)) }
        _searchState.update { it.copy(targetError = validateInput(_queries.value.selection) && _queries.value.selection.isNotEmpty()) }
        _searchState.update {  it.copy(afterError = validateInput(_queries.value.textAfterSelection)) }
    }


    private fun validateInput(input: String): Boolean {
        val isValid = if (input.isNotEmpty()) {
            val requiredLength = input.length < 129
            val regex = Regex("^[a-zA-Z' ]+\$")
            Log.i("SEARCHVM", "VALIDINP AND AND${regex.matches(input) && input != "'" && requiredLength}")
            regex.matches(input) && input != "'" && requiredLength
        } else {
            true
        }
        return isValid
    }

    fun doWordSearch() {
        viewModelScope.launch {
            repository.postSearch(request = _queries.value).collect { result ->
                //pipe Flow emissions into StateFlow
                _searchState.update { state ->
                    when (result) {
                        is NetworkResult.Error -> {
                            state.copy(
                                serverError = result.message
                                    ?: "Unexpected error occurred, try again.",
                                isLoading = false,
                                wordItem = null
                            )
                        }

                        is NetworkResult.Loading -> {
                            state.copy(isLoading = true, wordItem = null)
                        }

                        is NetworkResult.Success -> {
                            state.copy(
                                wordItem = result.data,
                                isLoading = false,
                                serverError = ""
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
        Log.d("SearchVM", "viewmodel onCleared called...")
        closeClient()
    }
}
