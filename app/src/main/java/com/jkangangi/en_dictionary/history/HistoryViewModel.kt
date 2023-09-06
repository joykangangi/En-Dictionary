package com.jkangangi.en_dictionary.history

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jkangangi.en_dictionary.app.data.repository.DictionaryRepositoryImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toPersistentList
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HistoryViewModel @Inject constructor(private val repositoryImpl: DictionaryRepositoryImpl) :
    ViewModel() {

    val allHistoryItems = repositoryImpl.getAllHistory()
        .map { entities -> entities.toPersistentList() }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = persistentListOf()
        )

    fun clearDictionaryItems() {
        viewModelScope.launch {
            repositoryImpl.deleteDictionaryItems(allHistoryItems.value.map { it.sentence })
        }
    }

    fun deleteDictionaryItem(sentences: List<String>) {
        viewModelScope.launch {
            repositoryImpl.deleteDictionaryItems(sentences)
        }
    }

}
