package com.jkangangi.en_dictionary.search

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jkangangi.en_dictionary.app.data.model.Word
import com.jkangangi.en_dictionary.app.data.remote.network.DictionaryRepositoryImpl
import com.jkangangi.en_dictionary.app.util.NetworkResult
import com.jkangangi.en_dictionary.word.WordDetailState
import dagger.hilt.android.lifecycle.HiltViewModel
import io.github.aakira.napier.Napier
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

private const val DELAY_TIME = 500L
private const val STOP_TIME_VM = 5000L

@HiltViewModel
class SearchViewModel @Inject constructor(private val repository: DictionaryRepositoryImpl) :
    ViewModel() {

    private val _searchQuery = MutableStateFlow("")
    val searchQuery = _searchQuery.asStateFlow()

    private val _searchState = MutableStateFlow(SearchScreenState())
    val searchState = _searchState.asStateFlow()

    private val _wordState = MutableStateFlow(WordDetailState())
    val wordState = _wordState.asStateFlow()


    private var searchJob: Job? = null
    fun onSearch(query: String) {
        _searchQuery.update { query }
        searchJob?.cancel() //if a job already exits we cancel the job
        searchJob = viewModelScope.launch {

            delay(DELAY_TIME)
            repository.getWord(word = query.lowercase()).collect { result ->

                when (result) {
                    is NetworkResult.Error -> {
                        _searchState.value = _searchState.value.copy(
                            wordItems = result.data ?: emptyList(),
                            isLoading = false,
                            error = result.message ?: "Unexpected Error Occurred"
                        )
                    }

                    is NetworkResult.Loading -> {
                        _searchState.value = _searchState.value.copy(
                            wordItems = result.data ?: emptyList(),
                            isLoading = true,
                            error = result.message ?: "Unexpected Error Occurred"
                        )
                    }

                    is NetworkResult.Success -> {
                        _searchState.value = _searchState.value.copy(
                            wordItems = result.data ?: emptyList(),
                            isLoading = false,
                            error = result.message ?: "Unexpected Error Occurred"
                        )
                    }
                }
            }
        }
    }

    fun setBook(word: Word): Word {
        _wordState.value = WordDetailState(word = word)
        return word
    }

    fun closeClient() {
        Napier.d("calling close from the viewModel...")
        repository.closeClient()
    }

    override fun onCleared() {
        super.onCleared()
        Log.d("httpClient", "viewmodel onCleared called...")
        closeClient()
    }
}

/*
.flowOn(Dispatchers.Default)
                .stateIn(
                    viewModelScope,
                    SharingStarted.WhileSubscribed(STOP_TIME_VM),
                    initialValue = NetworkResult.Loading()
                )
 */