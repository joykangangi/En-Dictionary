package com.jkangangi.en_dictionary

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.text.font.FontFamily
import androidx.lifecycle.viewmodel.compose.viewModel
import com.bumble.appyx.core.integration.NodeHost
import com.bumble.appyx.core.integrationpoint.NodeComponentActivity
import com.jkangangi.en_dictionary.app.navigation.Navigation
import com.jkangangi.en_dictionary.app.theme.En_DictionaryTheme
import com.jkangangi.en_dictionary.settings.AppTheme.DARK_THEME
import com.jkangangi.en_dictionary.settings.AppTheme.LIGHT_THEME
import com.jkangangi.en_dictionary.settings.SettingsViewModel
import com.jkangangi.en_dictionary.settings.fonts.AppFont


class MainActivity : NodeComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {


            En_DictionaryTheme(
                darkTheme = isDarkTheme(),
                fontFamily = getFontFamily(),
                content = {
                    NodeHost(
                        integrationPoint = appyxIntegrationPoint,
                        factory = { context ->
                            Navigation(
                                rootBuildContext = context
                            )
                        },
                    )
                }
            )
        }
    }
}

@Composable
private fun isDarkTheme(): Boolean {
    val settingsViewModel: SettingsViewModel = viewModel(factory = SettingsViewModel.Factory)
    val systemTheme = isSystemInDarkTheme()
    val theme by settingsViewModel.currentTheme.collectAsState(initial = LIGHT_THEME)
    return remember(theme) {
        when (theme) {
            LIGHT_THEME -> false
            DARK_THEME -> true
            else -> systemTheme
        }
    }
}

@Composable
private fun getFontFamily(): FontFamily {
    val settingsViewModel: SettingsViewModel = viewModel(factory = SettingsViewModel.Factory)
    val font by settingsViewModel.currentFont.collectAsState(initial = AppFont.SansSerif)
    return remember(font) {
        when (font) {
            AppFont.MerriWeather -> AppFont.MerriWeather.fontFamily
            AppFont.SansSerif -> AppFont.SansSerif.fontFamily
            AppFont.Monospace -> AppFont.Monospace.fontFamily
        }
    }
}