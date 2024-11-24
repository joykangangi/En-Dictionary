package com.jkangangi.en_dictionary.history

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jkangangi.en_dictionary.app.data.local.room.DictionaryEntity
import com.jkangangi.en_dictionary.app.data.repository.DictionaryRepository
import com.jkangangi.en_dictionary.app.util.PaginationState
import kotlinx.collections.immutable.persistentMapOf
import kotlinx.collections.immutable.toPersistentMap
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class HistoryViewModel(private val repository: DictionaryRepository) : ViewModel() {
    private val _historyItems = MutableStateFlow<Map<String, List<DictionaryEntity>>>(mapOf())
    val historyItems = _historyItems.asStateFlow()

    private var _userQuery = mutableStateOf("")
    val userQuery: State<String> = _userQuery

    private val _pagingState = MutableStateFlow(PaginationState.LOADING)
    val pagingState = _pagingState.asStateFlow()

    private var page = PAGE
    var canPaginate by mutableStateOf(false)
        private set
    private var groupedHistory: Map<String, List<DictionaryEntity>> = persistentMapOf()

    init {
        getPagingHistory()
    }

    /**
     * Pagination is designed to fetch more data only when the user is near the end of the list
     */
    fun getPagingHistory() {
        if (page == 0 || (canPaginate && _pagingState.value == PaginationState.REQUEST_INACTIVE)) {
            _pagingState.update { if (page == 0) PaginationState.LOADING else PaginationState.PAGINATING }
        }

        viewModelScope.launch(Dispatchers.IO) {
            try {
                val historyEntities = repository.getPagingHistory(
                    query = _userQuery.value,
                    pageSize = ITEMS_PER_PAGE,
                    offset = page * ITEMS_PER_PAGE
                )

                canPaginate = historyEntities.size == ITEMS_PER_PAGE

                if (historyEntities.isEmpty() && page == 0) {
                    _pagingState.update { PaginationState.EMPTY }
                    return@launch
                }

                // Group and update history items
                val newGroupedHistory = historyEntities.groupBy { entity ->
                    "${
                        entity.dateInserted.month.name.lowercase()
                            .replaceFirstChar { it.uppercase() }
                    } - ${entity.dateInserted.year}"
                }

                groupedHistory = if (page == 0) {
                    newGroupedHistory
                } else {
                    // Merge newGroupedHistory with the existing groupedHistory
                    groupedHistory.toMutableMap().apply {
                        newGroupedHistory.forEach { (key, newItems) ->
                            // Merge items into the group if it already exists
                            val existingItems = this[key] ?: emptyList()
                            this[key] = existingItems + newItems
                        }
                    }
                }
                _historyItems.update { it.toPersistentMap().clear().putAll(groupedHistory) }

                Log.i("History route","history size vm = ${groupedHistory.values.sumOf { it.size }}")
                Log.i("History route","history size vm2 = ${historyEntities.size}")
                Log.i("History route","page = $page")


                _pagingState.update {
                    if (canPaginate) {
                        page++
                        PaginationState.REQUEST_INACTIVE
                    } else {
                        PaginationState.PAGINATION_EXHAUST
                    }
                }
            } catch (e: Exception) {
                _pagingState.update {
                    if (page == 0) PaginationState.ERROR else PaginationState.PAGINATION_EXHAUST
                }
            }
        }
    }


    fun clearDictionaryItems() {
        viewModelScope.launch {
            _historyItems.collect { dictMap ->
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

    @OptIn(FlowPreview::class)
    fun onQueryTyped(newQuery: String) {
        _userQuery.value = newQuery

        viewModelScope.launch {
            flowOf(newQuery)
                .debounce(500)
                .distinctUntilChanged()
                .collect { _ ->
                    page = 0
                    canPaginate = false
                    groupedHistory = persistentMapOf()
                    _historyItems.update { it.toPersistentMap().clear() }
                    getPagingHistory()
                }
        }
    }

    fun clearPaging() {
        page = 0
        _pagingState.update { PaginationState.LOADING }
        canPaginate = false
    }


    companion object {
        const val ITEMS_PER_PAGE = 10
        const val PAGE = 0
    }

}
