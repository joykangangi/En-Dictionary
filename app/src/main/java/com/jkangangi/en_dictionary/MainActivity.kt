package com.jkangangi.en_dictionary

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import com.bumble.appyx.core.integration.NodeHost
import com.bumble.appyx.core.integrationpoint.NodeComponentActivity
import com.bumble.appyx.core.modality.BuildContext
import com.jkangangi.en_dictionary.app.navigation.BottomNavBar
import com.jkangangi.en_dictionary.app.navigation.Navigation
import com.jkangangi.en_dictionary.app.theme.En_DictionaryTheme

class MainActivity : NodeComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            En_DictionaryTheme {
                var buildContext: BuildContext? = null
                Scaffold(
                    modifier = Modifier,
                    content = { scaffoldPadding ->
                        NodeHost(
                            integrationPoint = appyxIntegrationPoint,
                            factory = { context ->
                                buildContext = context
                                Navigation(rootBuildContext = buildContext!!)
                            },
                            modifier = Modifier.padding(scaffoldPadding)
                        )
                    },
                    bottomBar = {
                        buildContext?.let { Navigation(rootBuildContext = it) }
                            ?.let { BottomNavBar(navigation = it) }
                    }
                )
            }
        }
    }
}