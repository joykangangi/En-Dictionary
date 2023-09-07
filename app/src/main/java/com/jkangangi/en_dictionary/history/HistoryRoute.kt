package com.jkangangi.en_dictionary.history

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.bumble.appyx.core.modality.BuildContext
import com.bumble.appyx.core.node.Node
import org.jsoup.select.Evaluator.IsEmpty

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

        val state by viewModel.allHistoryItems.collectAsState()
        Log.i("HistoryRoute","IsEmpty = ${state.isEmpty()}}")

        val onHistoryCleared = remember {
            {
                viewModel.clearDictionaryItems()
            }
        }

        val deleteDictionaryItem = remember {
            { sentences: List<String> ->
                viewModel.deleteDictionaryItem(sentences)
            }
        }

        HistoryScreen(
            dictionaryItems = state,
            onClearHistory = onHistoryCleared,
            deleteWord = deleteDictionaryItem,
            modifier = modifier
        )

    }
}