package com.jkangangi.en_dictionary.app.data.repository

import kotlinx.coroutines.flow.Flow

interface UserPreferenceRepository {
    suspend fun setTheme(isDarkThemeEnabled: Boolean)

    fun getTheme(): Flow<Boolean>
}
