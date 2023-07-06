package com.jkangangi.en_dictionary.word

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
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
        WordDetailView(modifier = modifier)
    }

//change word to list of words in UI
    @Composable
    private fun WordDetailView(
        modifier: Modifier,
        viewModel: WordViewModel = hiltViewModel()
    ) {
        val state by viewModel.wordDetailState.collectAsState()
        WordScreen(
            state = state ,
            word = state.wordItems[0],
            onSave = { },
            modifier = modifier
        )
    }
}

