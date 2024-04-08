

package com.jkangangi.en_dictionary.app.util

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.jkangangi.en_dictionary.DictionaryApplication
import com.jkangangi.en_dictionary.definitions.DefinitionsViewModel
import com.jkangangi.en_dictionary.game.GameViewModel
import com.jkangangi.en_dictionary.history.HistoryViewModel
import com.jkangangi.en_dictionary.search.SearchViewModel

/**
 * Factory for ViewModels accessing the repository.
 */
@Suppress("UNCHECKED_CAST")
val DictionaryViewModelFactory = object : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T =
        with(modelClass) {
            val dictionaryRepository = DictionaryApplication.appModule.dictionaryRepository
            when {
                isAssignableFrom(DefinitionsViewModel::class.java) ->
                    DefinitionsViewModel(repository = dictionaryRepository)
                isAssignableFrom(GameViewModel::class.java) ->
                    GameViewModel(repository = dictionaryRepository)
                isAssignableFrom(HistoryViewModel::class.java) ->
                    HistoryViewModel(repository = dictionaryRepository)
                isAssignableFrom(SearchViewModel::class.java) -> {
                    SearchViewModel(repository = dictionaryRepository)
                }
                else ->
                    throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
            }
        } as T
}