package com.jkangangi.en_dictionary.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.jkangangi.en_dictionary.DictionaryApplication
import com.jkangangi.en_dictionary.app.data.local.datastore.DictionaryDataStore
import com.jkangangi.en_dictionary.settings.AppTheme.LIGHT_THEME
import com.jkangangi.en_dictionary.settings.PreferenceKeys.FONT_KEY
import com.jkangangi.en_dictionary.settings.PreferenceKeys.THEME_KEY
import com.jkangangi.en_dictionary.settings.fonts.AppFont
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch


class SettingsViewModel (
    private val dataStore: DictionaryDataStore
) : ViewModel() {

    val currentTheme = dataStore.getData(key = THEME_KEY).map { theme ->
        theme ?: LIGHT_THEME
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = LIGHT_THEME
    )

    val currentFont = dataStore.getData(key = FONT_KEY).map { fontName ->
        if (fontName != null) {
            AppFont.valueOf(fontName)
        } else
            AppFont.SansSerif
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = AppFont.SansSerif
    )

    fun updateTheme(newTheme: String) {
        viewModelScope.launch {
            dataStore.setData(key = THEME_KEY, value = newTheme)
        }
    }

    fun updateFont(newFont: AppFont) {
        viewModelScope.launch {
            dataStore.setData(key = FONT_KEY, value = newFont.name)
        }
    }

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val dictionaryDataStore = DictionaryApplication.appModule.dictionaryDataStore
                SettingsViewModel(
                    dataStore = dictionaryDataStore
                )
            }
        }
    }


}