package com.jkangangi.en_dictionary.saved

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.bumble.appyx.core.modality.BuildContext
import com.bumble.appyx.core.node.Node
import com.bumble.appyx.navmodel.backstack.BackStack
import com.jkangangi.en_dictionary.app.navigation.Navigation

class SavedRoute(
    buildContext: BuildContext,
    private val backStack: BackStack<Navigation.Route>
) : Node(buildContext = buildContext) {

    @Composable
    override fun View(modifier: Modifier) {
        SavedWords(savedWords = listOf() , onDeleteClicked = { }, doSort = true, sortWords = { })
    }
    
}