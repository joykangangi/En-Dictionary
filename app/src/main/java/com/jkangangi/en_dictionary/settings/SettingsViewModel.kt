package com.jkangangi.en_dictionary.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.jkangangi.en_dictionary.DictionaryApplication
import com.jkangangi.en_dictionary.app.data.local.datastore.DictionaryDataStore
import com.jkangangi.en_dictionary.settings.AppTheme.DARK_THEME
import com.jkangangi.en_dictionary.settings.AppTheme.LIGHT_THEME
import com.jkangangi.en_dictionary.settings.PreferenceKeys.FONT_KEY
import com.jkangangi.en_dictionary.settings.PreferenceKeys.THEME_KEY
import com.jkangangi.en_dictionary.settings.fonts.AppFont
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

data class SettingsState(
    val darkTheme: Boolean = false,
    val font: AppFont = AppFont.SansSerif
)

class SettingsViewModel (
    private val dataStore: DictionaryDataStore
) : ViewModel() {

    private val currentTheme = dataStore.getData(key = THEME_KEY).map { theme ->
        theme ?: LIGHT_THEME
    }

   private val currentFont = dataStore.getData(key = FONT_KEY).map { fontName ->
        if (fontName != null) {
            AppFont.valueOf(fontName)
        } else
            AppFont.SansSerif
    }

    val settingsState = combine(
        flow = currentTheme,
        flow2 = currentFont,
        transform = { theme,appFont ->
            SettingsState(
                darkTheme = theme == DARK_THEME,
                font = appFont
            )
        }
    ).stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = SettingsState()
    )

    private fun updateTheme(newTheme: String) {
        viewModelScope.launch {
            dataStore.setData(key = THEME_KEY, value = newTheme)
        }
    }

    private fun updateFont(newFont: AppFont) {
        viewModelScope.launch {
            dataStore.setData(key = FONT_KEY, value = newFont.name)
        }
    }

    fun updateSettings(event: SettingsEvent) {
        when(event) {

            is SettingsEvent.UpdateFonts -> {
                updateFont(event.font)
            }

            is SettingsEvent.UpdateTheme -> {
                if (event.isDark)
                    updateTheme(DARK_THEME)
                else {
                    updateTheme(LIGHT_THEME)
                }
            }

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