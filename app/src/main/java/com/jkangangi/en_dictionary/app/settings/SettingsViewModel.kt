package com.jkangangi.en_dictionary.app.settings

import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jkangangi.en_dictionary.app.data.local.datastore.DictionaryDataStore
import com.jkangangi.en_dictionary.app.settings.Constants.LIGHT_THEME
import com.jkangangi.en_dictionary.app.settings.PreferenceKeys.THEME_KEY
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * UserPreference -> UserRepositoryImpl -> SettingsVM
 */

//@HiltViewModel
//class SettingsViewModel @Inject constructor(
//    private val userPreferenceRepository: UserPreferenceRepository,
//) : ViewModel() {
//    val isDarkTheme = userPreferenceRepository.getTheme()
//        .stateIn(
//            scope = viewModelScope,
//            started = SharingStarted.WhileSubscribed(2500L),
//            initialValue = false
//        )
//
//    fun updateTheme(isDarkTheme: Boolean) {
//        viewModelScope.launch {
//            userPreferenceRepository.setTheme(isDarkThemeEnabled = !isDarkTheme)
//        }
//    }
//}
private object PreferenceKeys {
    val THEME_KEY = stringPreferencesKey("theme_key")
}
object Constants {
    const val LIGHT_THEME = "light_theme"
    const val DARK_THEME = "dark_theme"
}

class SettingsViewModel @Inject constructor(
    private val userPreferences: DictionaryDataStore
) : ViewModel() {

    val isDarkTheme = userPreferences.getData(key = THEME_KEY)
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(2500L),
            initialValue = LIGHT_THEME
        )

    fun updateTheme(newTheme: String) {
        viewModelScope.launch {
            userPreferences.setData(key = THEME_KEY, value = newTheme)
        }
    }


}