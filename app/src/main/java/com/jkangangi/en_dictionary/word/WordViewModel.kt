package com.jkangangi.en_dictionary.word

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
import kotlinx.coroutines.launch
import javax.inject.Inject


private const val DELAY_TIME = 500L
private const val STOP_TIME_VM = 5000L

@HiltViewModel
class WordViewModel @Inject constructor(
    private val repository: DictionaryRepository
) : ViewModel() {

    private var _wordDetailState = MutableStateFlow(WordDetailState())
    val wordDetailState = _wordDetailState.asStateFlow()

    private var searchJob: Job? = null
    suspend fun getWordDetail(query: String) {
        searchJob = viewModelScope.launch {
            delay(DELAY_TIME)
            repository.getWord(word = query).onEach { result ->
                when (result) {
                    is NetworkResult.Error -> {
                        _wordDetailState.value = _wordDetailState.value.copy(
                            wordItems = result.data as ImmutableList<Word>,
                            isLoading = false
                        )
                    }

                    is NetworkResult.Loading -> {
                        _wordDetailState.value = _wordDetailState.value.copy(
                            wordItems = result.data as ImmutableList<Word>,
                            isLoading = true
                        )
                    }

                    is NetworkResult.Success -> {
                        _wordDetailState.value = _wordDetailState.value.copy(
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