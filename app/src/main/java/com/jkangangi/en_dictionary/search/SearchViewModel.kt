package com.jkangangi.en_dictionary.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jkangangi.en_dictionary.app.data.model.Word
import com.jkangangi.en_dictionary.app.data.remote.network.DictionaryRepository
import com.jkangangi.en_dictionary.app.util.NetworkResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.collections.immutable.ImmutableList
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject


private const val DELAY_TIME = 500L
private const val STOP_TIME_VM = 5000L


@HiltViewModel
class SearchViewModel @Inject constructor(
    private val repository: DictionaryRepository
) : ViewModel() {

    private val _searchState = MutableStateFlow(SearchScreenState())
    val searchState = _searchState.asStateFlow()

    private val _searchQuery = MutableStateFlow(_searchState.value.searchQuery)
    val searchQuery = _searchQuery.asStateFlow()

    private var searchJob: Job? = null
    fun onSearch(query: String) {
        _searchQuery.update { query }

        searchJob = viewModelScope.launch {
            delay(DELAY_TIME)
            repository.getWord(word = query).onEach { result ->
                when (result) {
                    is NetworkResult.Error -> {
                        _searchState.value = _searchState.value.copy(
                            wordItems = result.data as ImmutableList<Word>,
                            isLoading = false
                        )
                    }

                    is NetworkResult.Loading -> {
                        _searchState.value = _searchState.value.copy(
                            wordItems = result.data as ImmutableList<Word>,
                            isLoading = true
                        )
                    }

                    is NetworkResult.Success -> {
                        _searchState.value = _searchState.value.copy(
                            wordItems = result.data as ImmutableList<Word>,
                            isLoading = false
                        )
                    }
                }
            }.flowOn(Dispatchers.Default)
                .stateIn(
                    viewModelScope,
                    SharingStarted.WhileSubscribed(STOP_TIME_VM),
                    initialValue = NetworkResult.Loading()
                )
        }
    }
}