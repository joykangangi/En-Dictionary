package com.jkangangi.en_dictionary.history

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.bumble.appyx.core.modality.BuildContext
import com.bumble.appyx.core.node.Node
import com.bumble.appyx.navmodel.backstack.BackStack
import com.bumble.appyx.navmodel.backstack.operation.push
import com.jkangangi.en_dictionary.app.data.local.room.DictionaryEntity
import com.jkangangi.en_dictionary.app.navigation.Route
import com.jkangangi.en_dictionary.app.util.DictionaryViewModelFactory

class HistoryRoute(
    buildContext: BuildContext,
    private val backStack: BackStack<Route>
) : Node(buildContext = buildContext) {

    @Composable
    override fun View(modifier: Modifier) {
        HistoryScreenView(modifier = modifier)
    }


    @Composable
    fun HistoryScreenView(
        modifier: Modifier,
        viewModel: HistoryViewModel = viewModel(factory = DictionaryViewModelFactory)
    ) {

        val state by viewModel.filteredItems.collectAsState()

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

        val toWordClick = remember {
            { dfn: DictionaryEntity ->
                backStack.push(Route.SearchDetail(sentence = dfn.sentence))
            }
        }

        val searchQuery by remember { viewModel.userQuery }

        HistoryScreen(
            dictionaryItems = state,
            onClearHistory = onHistoryCleared,
            deleteWord = deleteDictionaryItem,
            modifier = modifier,
            onWordClick =  toWordClick,
            searchQuery = searchQuery,
            onTypeQuery = viewModel::onQueryTyped
        )

    }
}