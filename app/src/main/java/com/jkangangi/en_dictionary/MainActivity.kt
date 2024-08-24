package com.jkangangi.en_dictionary

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.viewmodel.compose.viewModel
import com.bumble.appyx.core.integration.NodeHost
import com.bumble.appyx.core.integrationpoint.ActivityIntegrationPoint
import com.bumble.appyx.core.integrationpoint.IntegrationPointProvider
import com.jkangangi.en_dictionary.app.navigation.Navigation
import com.jkangangi.en_dictionary.app.theme.En_DictionaryTheme
import com.jkangangi.en_dictionary.settings.SettingsViewModel


/**
 * Root Activity for the app
 *
 * Using NodeComponentActivity causes a build error:
 *  -Inherited platform declarations clash: The following declarations have the same JVM signature (onRequestPermissionsResult(I[Ljava/lang/String;[I)V):
 *     fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray): Unit defined in appyx.MainActivity
 *     fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray): Unit defined in .appyx.MainActivity
 *
 * Inheriting [IntegrationPointProvider] manually seems to help.
 */

class MainActivity : ComponentActivity(), IntegrationPointProvider {

    override lateinit var appyxV1IntegrationPoint: ActivityIntegrationPoint

    private fun createIntegrationPoint(savedInstanceState: Bundle?) =
        ActivityIntegrationPoint(
            activity = this,
            savedInstanceState = savedInstanceState
        )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        appyxV1IntegrationPoint = createIntegrationPoint(savedInstanceState)

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

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        appyxV1IntegrationPoint.onSaveInstanceState(outState)
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