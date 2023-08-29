package com.jkangangi.en_dictionary.definitions

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.bumble.appyx.core.modality.BuildContext
import com.bumble.appyx.core.node.Node
import com.bumble.appyx.navmodel.backstack.BackStack
import com.jkangangi.en_dictionary.app.navigation.Route
import com.jkangangi.en_dictionary.search.SearchViewModel

class WordDetailRoute(
    buildContext: BuildContext,
    private val backStack: BackStack<Route>
) : Node(buildContext = buildContext) {


    @Composable
    override fun View(
        modifier: Modifier,
    ) {

        val onBack: () -> Unit = remember {
            {
                backStack.handleUpNavigation()
            }
        }
        WordDetailView(modifier = modifier, onBack = onBack)

    }

    //change word to list of words in UI
    @Composable
    private fun WordDetailView(
        modifier: Modifier,
        viewModel: SearchViewModel = hiltViewModel(),
        onBack: () -> Unit,
    ) {
        val state by viewModel.detailState.collectAsState()

        state.word?.let {
            WordScreen(
                word = it,
                modifier = modifier,
                onSpeakerClick = { },
                onBack = onBack
            )
        }
    }
}

