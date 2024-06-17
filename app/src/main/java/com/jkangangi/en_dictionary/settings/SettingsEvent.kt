package com.jkangangi.en_dictionary.settings

import com.jkangangi.en_dictionary.settings.fonts.AppFont

sealed interface SettingsEvent {

    data class UpdateTheme(val isDark: Boolean): SettingsEvent

    data class UpdateFonts(val font: AppFont): SettingsEvent

}