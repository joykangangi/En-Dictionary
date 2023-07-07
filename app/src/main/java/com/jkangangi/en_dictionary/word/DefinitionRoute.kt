package com.jkangangi.en_dictionary.word

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.bumble.appyx.core.modality.BuildContext
import com.bumble.appyx.core.node.Node
import com.bumble.appyx.navmodel.backstack.BackStack
import com.bumble.appyx.navmodel.backstack.activeElement
import com.jkangangi.en_dictionary.app.data.model.Word
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
        Log.d("Dfn Route", "${backStack.activeElement?.title}")
    }

    //change word to list of words in UI
    @Composable
    private fun WordDetailView(
        modifier: Modifier,
        viewModel: WordViewModel = hiltViewModel()
    ) {
        val state by viewModel.wordDetailState.collectAsState()
        val word = Word(
            meanings = listOf(),
            phonetic = "igzaempl",
            word = "example",
            phonetics = listOf(),
            sourceUrls = listOf()
        )
        WordScreen(
            state = state,
            word = word,
            onSave = { },
            modifier = modifier
        )
    }
}

