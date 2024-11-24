package com.jkangangi.en_dictionary.history

import android.util.Log
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.jkangangi.en_dictionary.app.util.DictionaryViewModelFactory
import com.jkangangi.en_dictionary.app.util.PaginationState
import kotlinx.coroutines.delay
import kotlinx.serialization.Serializable

/**
 * 1 NavController
 * 1 NavGraph
 */

@Serializable
data object HistoryRoute

fun NavController.navigateToHistory(
    navOptions: NavOptions? = null,
) = navigate(HistoryRoute, navOptions)

fun NavGraphBuilder.historyGraph(
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

    val lazyColumnState = rememberLazyListState()

    val historyList by viewModel.historyItems.collectAsStateWithLifecycle()
    val pagingState by viewModel.pagingState.collectAsStateWithLifecycle()
    val snackbarHostState = remember { SnackbarHostState() }

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


    /**
     * lazyColumnState.layoutInfo.totalItemsCount - 3 =>
     *    Ensures that new data is loaded before
     *    the user reaches the very end of the list,
     *    avoiding delays or a blank UI.
     */
    val shouldPaginate = remember {
        derivedStateOf {
            viewModel.canPaginate &&
                    (lazyColumnState.layoutInfo.visibleItemsInfo.lastOrNull()?.index
                        ?: -5) >= (lazyColumnState.layoutInfo.totalItemsCount - 3)
        }
    }

    Log.i("History route","history size = ${historyList.values.sumOf { it.size }}")

    LaunchedEffect(key1 = shouldPaginate.value) {
        if (shouldPaginate.value && pagingState == PaginationState.REQUEST_INACTIVE) {
            delay(500)
           viewModel.getPagingHistory()
        }
    }

    HistoryScreen(
        lazyListState = lazyColumnState,
        dictionaryItems = historyList,
        onClearHistory = onHistoryCleared,
        deleteWord = deleteDictionaryItem,
        modifier = modifier,
        onWordClick = toWordDfn,
        searchQuery = searchQuery,
        onTypeQuery = viewModel::onQueryTyped,
        paginationState = pagingState,
        snackbarHostState = snackbarHostState,
        retryFetchingHistory = viewModel::getPagingHistory
    )
}