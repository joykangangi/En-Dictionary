package com.jkangangi.en_dictionary.word

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.bumble.appyx.core.modality.BuildContext
import com.bumble.appyx.core.node.Node
import com.bumble.appyx.navmodel.backstack.BackStack
import com.jkangangi.en_dictionary.app.navigation.Navigation
import com.jkangangi.en_dictionary.app.navigation.Route

class DefinitionRoute(
    buildContext: BuildContext,
    private val backStack: BackStack<Route>
) : Node(buildContext = buildContext) {

    @Composable
    override fun View(
        modifier: Modifier,
    ) {
        //Todo Viewmodels
        WordScreen()
    }

}