package com.jkangangi.en_dictionary.app.settings

import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jkangangi.en_dictionary.app.data.local.datastore.DictionaryDataStore
import com.jkangangi.en_dictionary.app.settings.PreferenceKeys.THEME_KEY
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flowOn
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

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val dataStore: DictionaryDataStore
) : ViewModel() {

    val isDarkTheme = dataStore.getData(key = THEME_KEY).flowOn(Dispatchers.Default)
//        .stateIn( //X not be cached
//            scope = viewModelScope,
//            started = SharingStarted.WhileSubscribed(2500L),
//            initialValue = LIGHT_THEME
//        )

    fun updateTheme(newTheme: String) {
        viewModelScope.launch {
            dataStore.setData(key = THEME_KEY, value = newTheme)
        }
    }


}