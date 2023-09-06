package com.jkangangi.en_dictionary.game

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.bumble.appyx.core.modality.BuildContext
import com.bumble.appyx.core.node.Node
import com.bumble.appyx.navmodel.backstack.BackStack
import com.jkangangi.en_dictionary.app.navigation.Route

class GameRoute(
    buildContext: BuildContext,
    private val backStack: BackStack<Route>
) : Node(buildContext = buildContext) {

    @Composable
    override fun View(modifier: Modifier) {
        GameScreen(savedWords = listOf() , onDeleteClicked = { }, doSort = true, sortWords = { })
    }
    
}