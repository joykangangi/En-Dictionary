package com.jkangangi.en_dictionary.history

import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jkangangi.en_dictionary.app.data.local.room.DictionaryEntity
import com.jkangangi.en_dictionary.app.data.repository.DictionaryRepository
import com.jkangangi.en_dictionary.app.util.PaginationState
import kotlinx.collections.immutable.persistentMapOf
import kotlinx.collections.immutable.toPersistentMap
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class HistoryViewModel(private val repository: DictionaryRepository) : ViewModel() {

    private val allHistoryItems = repository.getAllHistory().map { historyList ->
        val groupedHistory = historyList.groupBy { entity ->
            "${
                entity.dateInserted.month.name.lowercase().replaceFirstChar { it.uppercase() }
            } - ${entity.dateInserted.year}"
        }

        groupedHistory.toPersistentMap()
    }

    private val _pagingState = MutableStateFlow(PaginationState.LOADING)
    val pagingState = _pagingState.asStateFlow()

    private var page = INITIAL_PAGE
    var canPaginate by mutableStateOf(false)
    private var groupedHistory : Map<String, List<DictionaryEntity>> = persistentMapOf()


    fun getPagingHistory() {
        if (page == 0 || (canPaginate && _pagingState.value == PaginationState.REQUEST_INACTIVE)) {
            _pagingState.update { if (page == 0) PaginationState.LOADING else PaginationState.PAGINATING }
        }

        viewModelScope.launch(Dispatchers.IO) {
            try {
                val historyEntities = repository.getPagingHistory(
                    query = _userQuery.value,
                    limit = ITEMS_PER_PAGE,
                    offset = page * ITEMS_PER_PAGE
                )

                canPaginate = historyEntities.size == 5

                if (page == 0) {
                    if (historyEntities.isEmpty()) {
                        _pagingState.update { PaginationState.EMPTY }
                        return@launch
                    }
                    allHistoryItems.collect { it.clear() }
                    groupedHistory = historyEntities.groupBy { entity ->
                        "${
                            entity.dateInserted.month.name.lowercase()
                                .replaceFirstChar { it.uppercase() }
                        } - ${entity.dateInserted.year}"
                    }

                    groupedHistory.toPersistentMap()
                    allHistoryItems.collect { it.putAll(groupedHistory) }
                } else {
                    allHistoryItems.collect { it.putAll(groupedHistory) }
                }

                _pagingState.update { PaginationState.REQUEST_INACTIVE }

                if (canPaginate){
                    page++
                } else {
                    _pagingState.update { PaginationState.PAGINATION_EXHAUST }
                }

            } catch (e: Exception) {
                _pagingState.update { if (page == 0) PaginationState.ERROR else PaginationState.PAGINATION_EXHAUST }
            }

        }
    }


    fun clearDictionaryItems() {
        viewModelScope.launch {
            allHistoryItems.collect { dictMap ->
                val deletedSentences = dictMap.flatMap { (_, valueList) ->
                    valueList.map { it.sentence }
                }

                repository.deleteDictionaryItems(
                    deletedSentences
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
        .flatMapLatest { query ->
            if (query.isBlank()) {
                allHistoryItems
            } else {
                allHistoryItems.map { dictMap ->
                    dictMap.mapValues { (_, valueList) ->
                        valueList.filter { it.sentence.contains(query, ignoreCase = true) }
                    }
                }
            }
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = persistentMapOf()
        )

    fun onQueryTyped(query: String) {
        _userQuery.value = query
    }

    fun clearPaging() {
        page = 0
        _pagingState.update { PaginationState.LOADING }
        canPaginate = false
    }


    companion object {
        const val ITEMS_PER_PAGE = 5
        const val INITIAL_PAGE = 0
    }

}
