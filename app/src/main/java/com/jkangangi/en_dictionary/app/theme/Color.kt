package com.jkangangi.en_dictionary.app.theme

import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.ui.graphics.Color

internal val lightColorScheme = lightColorScheme(
    primary = Color(0xFF38086F),
    onPrimary = Color(0xFFFFFFFF),
    background = Color(0xFFE7E5EB),
    onBackground = Color(0xFF000000),
    surface = Color(0xFFF7F3F7),
    error = Color(0xFFBA1A1A),
)

internal val darkColorScheme = darkColorScheme(
    primary = Color(0xFFCABEFF), //button
    onPrimary = Color(0xFF0C021D),//button text
    background = Color(0xFF1A0244),
    onBackground = Color(0xFFE6E1E6),
    surface = Color(0xFF673AB7), //top&bottom bar + cards
    error = Color(0xFFFFB4AB),
)