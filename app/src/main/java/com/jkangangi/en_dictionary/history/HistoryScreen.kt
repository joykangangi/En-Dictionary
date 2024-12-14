package com.jkangangi.en_dictionary.history

import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.jkangangi.en_dictionary.R
import com.jkangangi.en_dictionary.app.data.local.room.DictionaryEntity
import com.jkangangi.en_dictionary.app.theme.padding10
import com.jkangangi.en_dictionary.app.theme.padding16
import com.jkangangi.en_dictionary.app.util.PaginationState
import com.jkangangi.en_dictionary.app.widgets.EmptyListView
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.persistentMapOf


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HistoryScreen(
    lazyListState: LazyListState,
    paginationState: PaginationState,
    snackbarHostState: SnackbarHostState,
    dictionaryItems: Map<String, List<DictionaryEntity>>,
    onClearHistory: () -> Unit,
    deleteWord: (List<String>) -> Unit,
    onWordClick: (String) -> Unit,
    searchQuery: String,
    onTypeQuery: (String) -> Unit,
    retryFetchingHistory:() -> Unit,
    modifier: Modifier = Modifier,
) {

    val message = stringResource(id = R.string.error_fetching_histo)
    val retry = stringResource(id = R.string.retry)

    LaunchedEffect(key1 = paginationState) {
        if (paginationState == PaginationState.ERROR) {
            val result = snackbarHostState.showSnackbar(
                message = message,
                actionLabel = retry
            )

            if (result == SnackbarResult.ActionPerformed) {
                retryFetchingHistory()
            }
        }
    }

    Scaffold(
        modifier = modifier,
        topBar = {
            TopAppBar(
                title = {
                    SearchBar(
                        query = searchQuery,
                        onQueryChanged = onTypeQuery
                    )
                },
                modifier = Modifier.padding(10.dp),
            )
        },
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) },
        content = { contentPadding ->

            LazyColumn(
                state = lazyListState,
                contentPadding = contentPadding,
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.padding(16.dp),
                content = {

                    item {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.End,
                            content = {
                                Button(
                                    onClick = onClearHistory
                                ) {
                                    Text(stringResource(id = R.string.clear_history))
                                }
                            }
                        )
                    }

                    dictionaryItems.forEach { (date, dictionaryEntities) ->
                        item {
                            Spacer(modifier = Modifier.height(padding10()))
                        }

                        item {
                            Text(
                                text = date,
                                color = MaterialTheme.colorScheme.primary
                            )
                        }

                        items(dictionaryEntities) { dictionary ->
                            HistoryItemCard(
                                dictionary = dictionary,
                                onDeleteWord = deleteWord,
                                onWordClick = onWordClick,
                                modifier = Modifier.animateItem(
                                    fadeInSpec = null,
                                    fadeOutSpec = null,
                                    placementSpec = tween(durationMillis = 500)
                                )
                            )
                        }
                    }

                    item {
                        Spacer(modifier = Modifier.height(padding16()))
                    }

                    when (paginationState) {
                        PaginationState.REQUEST_INACTIVE -> {}

                        PaginationState.LOADING -> {
                            item {
                                Box(
                                    contentAlignment = Alignment.Center,
                                    content = {
                                        CircularProgressIndicator()
                                    }
                                )
                            }
                        }

                        PaginationState.PAGINATING -> {
                            item {
                                Box(
                                    contentAlignment = Alignment.Center,
                                    content = {
                                    CircularProgressIndicator()
                                    }
                                )
                            }
                        }

                        PaginationState.ERROR -> {

                        }

                        PaginationState.PAGINATION_EXHAUST -> {
                            item {
                               HistoryFooter()
                            }
                        }

                        PaginationState.EMPTY -> {
                            item {
                                EmptyListView(
                                    modifier = Modifier.fillMaxSize(),
                                    stringId = R.string.empty_history
                                )
                            }
                        }
                    }
                }
            )
        }
    )
}


@Preview
@Composable
fun PreviewHistory() {
    HistoryScreen(
        lazyListState = LazyListState(),
        dictionaryItems = persistentMapOf(
            "June - 2024" to persistentListOf(
            DictionaryEntity(sentence = "Magic"),
            DictionaryEntity(sentence = "Invalidate")
            )
        ),
        deleteWord = { },
        onClearHistory = { },
        onWordClick = { },
        searchQuery = "",
        onTypeQuery = { },
        snackbarHostState = SnackbarHostState(),
        paginationState = PaginationState.REQUEST_INACTIVE,
        retryFetchingHistory = { }
    )
}