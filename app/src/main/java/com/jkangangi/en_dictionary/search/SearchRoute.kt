package com.jkangangi.en_dictionary.search

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.bumble.appyx.core.modality.BuildContext
import com.bumble.appyx.core.node.Node
import com.bumble.appyx.navmodel.backstack.BackStack
import com.jkangangi.en_dictionary.app.navigation.Navigation
import com.jkangangi.en_dictionary.app.navigation.Route

class SearchRoute(
    buildContext: BuildContext,
    private val backStack: BackStack<Route>,
    //you can add an onclick which can help in moving to next screen + push a screen to backstack
) : Node(buildContext = buildContext) {

    @Composable
    override fun View(modifier: Modifier) {
        //Todo Viewmodels
        SearchScreen(isDarkTheme = true, toggleTheme = { }, modifier = modifier)

    }

}