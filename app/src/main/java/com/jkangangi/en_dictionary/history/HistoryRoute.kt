package com.jkangangi.en_dictionary.history

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.bumble.appyx.core.modality.BuildContext
import com.bumble.appyx.core.node.Node

class HistoryRoute(
    buildContext: BuildContext,
) : Node(buildContext = buildContext) {

    @Composable
    override fun View(modifier: Modifier) {
        HistoryScreenView(modifier = modifier)
    }


    @Composable
    fun HistoryScreenView(
        modifier: Modifier,
        viewModel: HistoryViewModel = hiltViewModel()
    ) {

        val state by viewModel.historyState.collectAsState()

        val onHistoryCleared =
            {
                viewModel.deleteDictionaryItems(state.historyItems.map { it.sentence })
            }


        val deleteDictionaryItems =
            { sentences: List<String> ->
                viewModel.deleteDictionaryItems(sentences)
            }


        HistoryScreen(
            dictionaryItems = state.historyItems,
            onClearHistory = onHistoryCleared,
            deleteWord = deleteDictionaryItems,
            modifier = modifier
        )

    }
}