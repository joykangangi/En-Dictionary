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
import kotlinx.collections.immutable.toPersistentMap
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
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

                canPaginate = historyEntities.first().size == ITEMS_PER_PAGE

                if (historyEntities.first().isEmpty() && page == 0) {
                    _pagingState.update { PaginationState.EMPTY }
                    return@launch
                }


                val newGroupedHistory = historyEntities.map { entities ->
                    entities.groupBy { entity ->
                        "${
                            entity.dateInserted.month.name.lowercase()
                                .replaceFirstChar { it.uppercase() }
                        } - ${entity.dateInserted.year}"
                    }
                }

                _historyItems.update { items ->
                    if (page == 0) {
                        newGroupedHistory.first()
                    } else {
                        items.toPersistentMap().clear()
                        // Merge newGroupedHistory with the existing groupedHistory
                        items.toMutableMap().apply {
                            newGroupedHistory.first().forEach { (key, newItems) ->
                                val existingItems = this[key] ?: emptyList()
                                this[key] = existingItems + newItems
                            }
                        }
                    }
                }

                Log.i(
                    "History route",
                    "history size vm = ${_historyItems.first().values.sumOf { it.size }}"
                )
                Log.i("History route", "history size vm2 = ${historyEntities.first().size}")
                Log.i("History route", "page = $page")


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
            val currentItems = _historyItems.value
            val deletedSentences = currentItems.flatMap { (_, valueList) ->
                valueList.map { it.sentence }
            }

            repository.deleteDictionaryItems(deletedSentences)

            _historyItems.update { emptyMap() }
        }
    }

    fun deleteDictionaryItem(sentences: List<String>) {
        viewModelScope.launch {
            repository.deleteDictionaryItems(sentences)
        }

        _historyItems.update { currentItems ->
            currentItems.toMutableMap().apply {
                forEach { (key, items) ->
                    val updatedItems = items.filterNot { it.sentence in sentences }
                    if (updatedItems.isEmpty()) {
                        remove(key) // Remove the group if it's empty
                    } else {
                        this[key] = updatedItems
                    }
                }
            }
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
