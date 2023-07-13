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
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

private const val DELAY_TIME = 500L

@HiltViewModel
class SearchViewModel @Inject constructor(private val repository: DictionaryRepositoryImpl) :
    ViewModel() {

    private val _targetQuery = MutableStateFlow("")
    val targetQuery = _targetQuery.asStateFlow()

    private val _queryAfterTarget = MutableStateFlow("")
    val queryAfterTarget = _queryAfterTarget.asStateFlow()

    private val _queryBeforeTarget = MutableStateFlow("")
    val queryBeforeTarget = _queryBeforeTarget.asStateFlow()

    private val _searchState = MutableStateFlow(SearchScreenState())
    val searchState = _searchState.asStateFlow()

    private val _detailState = MutableStateFlow(WordDetailState())
    val detailState = _detailState.asStateFlow()

    private var searchJob: Job? = null

    fun onSearchTarget(query: String) {
        _targetQuery.update { query }
    }

    fun onSearchAfTarget(query: String) {
        _queryAfterTarget.update { query }
    }

    fun onSearchBfTarget(query: String) {
        _queryBeforeTarget.update { query }
    }


    fun doWordSearch(target: String //, textAfterTarget: String, textBeforeTarget: String
    ) {
        searchJob?.cancel() //if a job already exits we cancel the job
        searchJob = viewModelScope.launch {

            delay(DELAY_TIME)
            repository.postSearch(request = RequestDTO(selection = target)).collect { result -> //:NetworkResult<Dictionary> ->
                when (result) {
                    is NetworkResult.Error -> {
                        _searchState.value = _searchState.value.copy(
                            error = result.message ?: "Unexpected Error Occurred"
                        )
                    }

                    is NetworkResult.Loading -> {
                        _searchState.value = _searchState.value.copy(
                            isLoading = true,
                        )
                    }

                    is NetworkResult.Success -> {
                        _searchState.value = _searchState.value.copy(
                            wordItems = mutableListOf(result.data),
                            isLoading = false
                        )
                    }
                }
            }

        }
        /*    repository.getWord(word = param.lowercase()).collect { result ->


                when (result) {
                    is NetworkResult.Error -> {
                        _searchState.value = _searchState.value.copy(
                            wordItems = result.data ?: emptyList(),
                            error = result.message ?: "Unexpected Error Occurred"
                        )
                    }

                    is NetworkResult.Loading -> {
                        _searchState.value = _searchState.value.copy(
                            wordItems = result.data ?: emptyList(),
                            isLoading = true,
                        )
                    }

                    is NetworkResult.Success -> {
                        _searchState.value = _searchState.value.copy(
                            wordItems = result.data ?: emptyList(),
                            isLoading = false
                        )
                    }
                }
            }*/
    }


    fun clearInputT() {
        _targetQuery.value = ""
    }
    fun clearInputA() {
        _queryAfterTarget.value = ""
    }
    fun clearInputB() {
        _queryBeforeTarget.value = ""
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
