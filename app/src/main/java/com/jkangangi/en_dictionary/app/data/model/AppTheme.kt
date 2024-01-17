package com.jkangangi.en_dictionary.app.data.model

import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DarkMode
import androidx.compose.material.icons.filled.Lightbulb
import androidx.compose.material.icons.filled.WbSunny
import androidx.compose.ui.graphics.vector.ImageVector
import com.jkangangi.en_dictionary.R

enum class AppTheme(@StringRes val titleId: Int, val icon: ImageVector) {
    Light(R.string.light_theme, Icons.Default.WbSunny),
    Dark(R.string.dark_theme, Icons.Default.DarkMode),
    System(R.string.theme_system, Icons.Default.Lightbulb)
}