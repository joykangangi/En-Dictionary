package com.jkangangi.en_dictionary.app.settings

import androidx.datastore.preferences.core.edit
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class SettingsViewModel @Inject constructor(
    dataStoreUtil: DataStoreUtil,
) : ViewModel() {
    private val _isDarkThemeEnabled = MutableStateFlow(false)
    val isDarkThemeEnabled = _isDarkThemeEnabled.asStateFlow()
    private val dataStore = dataStoreUtil.dataStore


    fun getTheme(isSystemDarkTheme: Boolean): Flow<Boolean> {
        _isDarkThemeEnabled.update { isSystemDarkTheme }
        return dataStore.data
            .map { preferences ->
                preferences[DataStoreUtil.THEME_KEY] ?: isSystemDarkTheme
            }
    }

    fun saveTheme(isDarkThemeEnabled: Boolean) {
        viewModelScope.launch {
            dataStore.edit { preferences ->
                preferences[DataStoreUtil.THEME_KEY] = isDarkThemeEnabled
            }
        }
    }

}