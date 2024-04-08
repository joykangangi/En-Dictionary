package com.jkangangi.en_dictionary.history

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jkangangi.en_dictionary.app.data.repository.DictionaryRepository
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toPersistentList
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class HistoryViewModel (private val repository: DictionaryRepository) : ViewModel() {

    val allHistoryItems = repository.getAllHistory()
        .map { entities -> entities.toPersistentList() }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = persistentListOf()
        )

    fun clearDictionaryItems() {
        viewModelScope.launch {
            repository.deleteDictionaryItems(allHistoryItems.value.map { it.sentence })
        }
    }

    fun deleteDictionaryItem(sentences: List<String>) {
        viewModelScope.launch {
            repository.deleteDictionaryItems(sentences)
        }
    }

}
