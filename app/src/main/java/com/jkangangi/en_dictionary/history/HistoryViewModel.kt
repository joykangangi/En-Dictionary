package com.jkangangi.en_dictionary.history

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.snapshotFlow
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jkangangi.en_dictionary.app.data.repository.DictionaryRepository
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toPersistentList
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class HistoryViewModel(private val repository: DictionaryRepository) : ViewModel() {

    private val allHistoryItems = repository.getAllHistory().map { it.toPersistentList() }

    fun clearDictionaryItems() {
        viewModelScope.launch {
            allHistoryItems.collect { list ->
                val deletedSentence = list.map { it.sentence }

                repository.deleteDictionaryItems(
                    deletedSentence
                )
            }
        }
    }

    fun deleteDictionaryItem(sentences: List<String>) {
        viewModelScope.launch {
            repository.deleteDictionaryItems(sentences)
        }
    }

    private var _userQuery = mutableStateOf("")
    val userQuery: State<String> = _userQuery

    @OptIn(FlowPreview::class, ExperimentalCoroutinesApi::class)
    val filteredItems = snapshotFlow { _userQuery.value }
        .debounce(500)
        .flatMapLatest { sentence ->
            if (sentence.isBlank()) {
                allHistoryItems
            } else {
               allHistoryItems.map { list ->
                   list.filter {
                       it.sentence.contains(sentence, ignoreCase = true)
                   }.toPersistentList()
               }
            }
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = persistentListOf()
        )

    fun onQueryTyped(query: String) {
        _userQuery.value = query
    }

}
