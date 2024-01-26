package com.jkangangi.en_dictionary.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jkangangi.en_dictionary.app.data.local.datastore.DictionaryDataStore
import com.jkangangi.en_dictionary.settings.PreferenceKeys.FONT_KEY
import com.jkangangi.en_dictionary.settings.PreferenceKeys.THEME_KEY
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 *
 * DataStoreImpl (repositoryImpl) -> SettingsVM
 * //        .stateIn( //X not be cached
 * //            scope = viewModelScope,
 * //            started = SharingStarted.WhileSubscribed(2500L),
 * //            initialValue = LIGHT_THEME
 * //        )
 *
 */

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val dataStore: DictionaryDataStore
) : ViewModel() {

    val currentTheme = dataStore.getData(key = THEME_KEY).flowOn(Dispatchers.Default)
    val currentFont = dataStore.getData(key = FONT_KEY).flowOn(Dispatchers.Default)


    fun updateTheme(newTheme: String) {
        viewModelScope.launch {
            dataStore.setData(key = THEME_KEY, value = newTheme)
        }
    }

    fun updateFont(newFont: String) {
        viewModelScope.launch {
            dataStore.setData(key = FONT_KEY, value = newFont)
        }
    }


}