package com.jkangangi.en_dictionary

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.viewmodel.compose.viewModel
import com.bumble.appyx.core.integration.NodeHost
import com.jkangangi.en_dictionary.app.navigation.Navigation
import com.jkangangi.en_dictionary.app.theme.En_DictionaryTheme
import com.jkangangi.en_dictionary.settings.SettingsViewModel


class MainActivity : DictionaryIntegrationPointProvider() {

    private val settingsViewModel by viewModels<SettingsViewModel>(
        factoryProducer = { SettingsViewModel.Factory }
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {

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


class DictionaryActivity: ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val settingsViewModel: SettingsViewModel = viewModel(factory = SettingsViewModel.Factory)
            val settings by settingsViewModel.settingsState.collectAsState()

            DisposableEffect(settings.darkTheme) {
                enableEdgeToEdge(
                    statusBarStyle = SystemBarStyle.auto(
                        android.graphics.Color.TRANSPARENT,
                        android.graphics.Color.TRANSPARENT,
                    ) { settings.darkTheme },
                    navigationBarStyle = SystemBarStyle.auto(
                        lightScrim,
                        darkScrim,
                    ) { settings.darkTheme },
                )
                onDispose {}
            }

            En_DictionaryTheme(
                darkTheme = settings.darkTheme,
                fontFamily = settings.font.fontFamily,
                content = {

                }
            )
        }
    }
}


private val lightScrim = android.graphics.Color.argb(0xe6, 0xFF, 0xFF, 0xFF)

private val darkScrim = android.graphics.Color.argb(0x80, 0x1b, 0x1b, 0x1b)
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