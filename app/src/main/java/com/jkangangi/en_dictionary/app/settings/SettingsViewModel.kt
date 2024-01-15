package com.jkangangi.en_dictionary.app.settings

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
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
        return dataStore.data
            .map { preferences ->
                _isDarkThemeEnabled.update { preferences[DataStoreUtil.THEME_KEY] ?: isSystemDarkTheme }
                preferences[DataStoreUtil.THEME_KEY] ?: isSystemDarkTheme
            }
    }

    fun saveTheme(darkTheme: Boolean) {
        viewModelScope.launch {
            dataStore.edit { preferences ->
                preferences[DataStoreUtil.THEME_KEY] = darkTheme
                _isDarkThemeEnabled.update { preferences[DataStoreUtil.THEME_KEY] ?: darkTheme }
            }
        }
    }

}

@Composable
fun rememberSettingsViewModel():SettingsViewModel {
    val context = LocalContext.current
    val dataStoreUtil = DataStoreUtil(context)
    return remember {
        SettingsViewModel(dataStoreUtil = dataStoreUtil)
    }
}