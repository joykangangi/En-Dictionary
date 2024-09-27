package com.jkangangi.en_dictionary

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.viewmodel.compose.viewModel
import com.bumble.appyx.core.integration.NodeHost
import com.jkangangi.en_dictionary.app.navigation.Navigation
import com.jkangangi.en_dictionary.app.theme.En_DictionaryTheme
import com.jkangangi.en_dictionary.settings.SettingsViewModel


class MainActivity : DictionaryIntegrationPointProvider() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {

            val settingsViewModel: SettingsViewModel = viewModel(factory = SettingsViewModel.Factory)
            val settings by settingsViewModel.settingsState.collectAsState()

            En_DictionaryTheme(
                darkTheme = settings.darkTheme,
                fontFamily = settings.font.fontFamily,
                content = {
                    NodeHost(
                        integrationPoint = appyxV1IntegrationPoint,
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

//class MainActivity : NodeComponentActivity() {
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//
//        setContent {
//
//            val settingsViewModel: SettingsViewModel = viewModel(factory = SettingsViewModel.Factory)
//            val settings by settingsViewModel.settingsState.collectAsState()
//
//            En_DictionaryTheme(
//                darkTheme = settings.darkTheme,
//                fontFamily = settings.font.fontFamily,
//                content = {
//                    NodeHost(
//                        integrationPoint = appyxV1IntegrationPoint,
//                        factory = { context ->
//                            Navigation(
//                                rootBuildContext = context
//                            )
//                        },
//                    )
//                }
//            )
//        }
//    }
//}