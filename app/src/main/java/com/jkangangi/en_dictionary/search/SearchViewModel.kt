package com.jkangangi.en_dictionary.search

import androidx.lifecycle.ViewModel
import com.jkangangi.en_dictionary.app.data.remote.network.DictionaryRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject


@HiltViewModel
class SearchViewModel @Inject constructor(private val repository: DictionaryRepository) : ViewModel() {
    private val _searchQuery = MutableStateFlow("")
    val searchQuery = _searchQuery.asStateFlow()

    fun onSearch(query: String) {
        _searchQuery.update { query }
    }
}