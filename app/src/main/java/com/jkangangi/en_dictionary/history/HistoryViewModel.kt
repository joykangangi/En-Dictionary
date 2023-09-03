package com.jkangangi.en_dictionary.history

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jkangangi.en_dictionary.app.data.local.DictionaryEntity
import com.jkangangi.en_dictionary.app.data.repository.DictionaryRepositoryImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toPersistentList
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HistoryViewModel @Inject constructor(private val repositoryImpl: DictionaryRepositoryImpl) :
    ViewModel() {

    private val _historyState = MutableStateFlow(HistoryState())
    val historyState = _historyState.asStateFlow()

    init {
        getAllItems()
    }

   private fun getAllItems() = viewModelScope.launch {
        repositoryImpl.getAllHistory().collect { items->
            _historyState.update { historyState ->
                historyState.copy(historyItems = items.toPersistentList())
            }
        }
    }
    /*val allHistoryItems = repositoryImpl.getAllHistory().map {
        it.toPersistentList()
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = persistentListOf()
    )*/

    fun deleteDictionaryItems(sentences: List<String>) {
        viewModelScope.launch {
            repositoryImpl.deleteDictionaryItems(sentences)
        }
    }

}

data class HistoryState(val historyItems: ImmutableList<DictionaryEntity> = persistentListOf())
