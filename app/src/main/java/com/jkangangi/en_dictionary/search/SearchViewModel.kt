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
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

private const val DELAY_TIME = 1000L

@HiltViewModel
class SearchViewModel @Inject constructor(private val repository: DictionaryRepositoryImpl) :
    ViewModel() {

    private val _queries = MutableStateFlow(RequestDTO())

    private val _searchState = MutableStateFlow(SearchScreenState())
   // val searchState = _searchState.asStateFlow()

    private val _detailState = MutableStateFlow(WordDetailState())
    val detailState = _detailState.asStateFlow()

    val searchState = _queries.map {
        _searchState.value.copy(requests = it)

    }.flowOn(Dispatchers.Default).stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5_000),
        initialValue = SearchScreenState(),
    )

    private var searchJob: Job? = null

    fun updateQuery(queries: RequestDTO) {
        _queries.update { queries }
    }



     fun doWordSearch() {
        searchJob?.cancel() //if a job already exits we cancel the job
        searchJob = viewModelScope.launch {
            Log.d("SearchViewModel","Target = ${_queries.value}")

            delay(DELAY_TIME)
            repository.postSearch(request = _queries.value).collect { result ->
                when (result) {
                    is NetworkResult.Error -> {
                        _searchState.value = _searchState.value.copy(
                            error = result.message ?: "Unexpected Error Occurred",
                            isLoading = false
                        )
                    }

                    is NetworkResult.Loading -> {
                        _searchState.value = _searchState.value.copy(
                            isLoading = true,
                        )
                    }

                    is NetworkResult.Success -> {
                        _searchState.value = _searchState.value.copy(
                            wordItem = result.data,
                            isLoading = false
                        )
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
