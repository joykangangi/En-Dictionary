package com.jkangangi.en_dictionary.history

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.jkangangi.en_dictionary.app.util.DictionaryViewModelFactory
import kotlinx.serialization.Serializable

@Serializable
data object HistoryRoute

fun NavController.navigateToHistory(
    navOptions: NavOptions? = null,
) = navigate(HistoryRoute, navOptions)

fun NavGraphBuilder.historyScreen(
    toWordDfn: (String) -> Unit
) {
    composable<HistoryRoute> {
        HistoryScreen(toWordDfn = toWordDfn)
    }
}

@Composable
internal fun HistoryScreen(
    toWordDfn: (String) -> Unit,
    modifier: Modifier = Modifier,
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

    val searchQuery by remember { viewModel.userQuery }

    HistoryScreen(
        dictionaryItems = state,
        onClearHistory = onHistoryCleared,
        deleteWord = deleteDictionaryItem,
        modifier = modifier,
        onWordClick = toWordDfn,
        searchQuery = searchQuery,
        onTypeQuery = viewModel::onQueryTyped
    )
}