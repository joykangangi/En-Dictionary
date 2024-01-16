package com.jkangangi.en_dictionary.app.data.repository

import com.jkangangi.en_dictionary.app.settings.UserPreferences
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class UserPreferenceRepositoryImpl @Inject constructor(
    private val preferences: UserPreferences
): UserPreferenceRepository {
    override suspend fun setTheme(isDarkThemeEnabled: Boolean) {
        preferences.setTheme(isDarkTheme = isDarkThemeEnabled)
    }

    override fun getTheme(): Flow<Boolean> {
      return preferences.getTheme()
    }

}