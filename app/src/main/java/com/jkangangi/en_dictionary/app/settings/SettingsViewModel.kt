package com.jkangangi.en_dictionary.app.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jkangangi.en_dictionary.app.data.repository.UserPreferenceRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val userPreferenceRepository: UserPreferenceRepository,
) : ViewModel() {
    val isDarkTheme = userPreferenceRepository.getTheme()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(2500L),
            initialValue = false
        )

    fun updateTheme(isDarkTheme: Boolean) {
        viewModelScope.launch {
            userPreferenceRepository.setTheme(isDarkThemeEnabled = !isDarkTheme)
        }
    }
}