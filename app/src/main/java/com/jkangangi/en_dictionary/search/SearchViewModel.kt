package com.jkangangi.en_dictionary.search

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jkangangi.en_dictionary.app.data.model.Dictionary
import com.jkangangi.en_dictionary.app.data.remote.dto.RequestDTO
import com.jkangangi.en_dictionary.app.data.repository.DictionaryRepositoryImpl
import com.jkangangi.en_dictionary.app.util.NetworkResult
import com.jkangangi.en_dictionary.word.WordDetailState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
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


    private val _detailState = MutableStateFlow(WordDetailState())
    val detailState = _detailState.asStateFlow()

    fun updateQuery(queries: RequestDTO) {
        _queries.update { queries }
        _searchState.update { it.copy(requests = queries) }
    }

    fun doWordSearch() {
        viewModelScope.launch {
            repository.postSearch(request = _queries.value).collect { result ->
                //pipe Flow emissions into StateFlow
                _searchState.update { state ->
                    when (result) {
                        is NetworkResult.Error -> {
                            state.copy(
                                error = result.message ?: "Unexpected Error Occurred",
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
                                error = ""
                            )
                        }
                    }
                }
            }
        }
    }

    fun setBook(word: Dictionary): Dictionary {
        _detailState.value = WordDetailState(word = word)
        return word
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
