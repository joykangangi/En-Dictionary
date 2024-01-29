package com.jkangangi.en_dictionary.settings.fonts

import androidx.annotation.StringRes
import androidx.compose.ui.text.font.FontFamily
import com.jkangangi.en_dictionary.R
import com.jkangangi.en_dictionary.app.theme.MerriWeatherFontFamily


enum class AppFont(@StringRes val nameId: Int, val fontFamily: FontFamily) {
    Monospace(nameId = R.string.monospace, fontFamily = FontFamily.Monospace),
    SansSerif(nameId = R.string.sans_serif, fontFamily = FontFamily.SansSerif),
    MerriWeather(nameId = R.string.merri_weather, fontFamily = MerriWeatherFontFamily)
}