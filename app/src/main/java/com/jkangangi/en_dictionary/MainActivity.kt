package com.jkangangi.en_dictionary

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import com.bumble.appyx.core.integration.NodeHost
import com.bumble.appyx.core.integrationpoint.NodeComponentActivity
import com.jkangangi.en_dictionary.app.navigation.Navigation
import com.jkangangi.en_dictionary.app.settings.SettingsViewModel
import com.jkangangi.en_dictionary.app.theme.En_DictionaryTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : NodeComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            val settingsViewModel: SettingsViewModel = hiltViewModel()
            val isDarkMode by settingsViewModel.isDarkTheme.collectAsState()
            En_DictionaryTheme(
                darkTheme = isDarkMode,
                content = {
                    NodeHost(
                        integrationPoint = appyxIntegrationPoint,
                        factory = { context -> Navigation(rootBuildContext = context) },
                    )
                }
            )
        }
    }
}