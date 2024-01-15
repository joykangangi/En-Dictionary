package com.jkangangi.en_dictionary

import android.content.res.Configuration
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import com.bumble.appyx.core.integration.NodeHost
import com.bumble.appyx.core.integrationpoint.NodeComponentActivity
import com.jkangangi.en_dictionary.app.navigation.Navigation
import com.jkangangi.en_dictionary.app.settings.rememberSettingsViewModel
import com.jkangangi.en_dictionary.app.theme.En_DictionaryTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : NodeComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
                    En_DictionaryTheme(
                        darkTheme = isDarkMode(),
                        content = {
                            NodeHost(
                                integrationPoint = appyxIntegrationPoint,
                                factory = { context -> Navigation(rootBuildContext = context) },
                            )
                        }
                    )
        }
    }

    @Composable
    private fun isDarkMode(): Boolean {
        val viewModel = rememberSettingsViewModel()
        val systemTheme =
            when (resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK) {
                Configuration.UI_MODE_NIGHT_YES -> {
                    true
                }

                Configuration.UI_MODE_NIGHT_NO -> {
                    false
                }

                else -> {
                    false
                }
            }
        return viewModel.getTheme(systemTheme).collectAsState(initial = systemTheme).value
    }
}