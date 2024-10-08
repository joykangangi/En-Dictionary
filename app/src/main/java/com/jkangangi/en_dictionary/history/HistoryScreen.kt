package com.jkangangi.en_dictionary.history

import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.jkangangi.en_dictionary.R
import com.jkangangi.en_dictionary.app.data.local.room.DictionaryEntity
import com.jkangangi.en_dictionary.app.widgets.EmptyListView
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf


@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun HistoryScreen(
    dictionaryItems: ImmutableList<DictionaryEntity>,
    onClearHistory: () -> Unit,
    deleteWord: (List<String>) -> Unit,
    onWordClick: (String) -> Unit,
    searchQuery: String,
    onTypeQuery: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    val isEmpty = dictionaryItems.isEmpty()

    Scaffold(
        modifier = modifier,
        topBar = {
            TopAppBar(
                title = { },
                modifier = Modifier.padding(10.dp),
                actions = {
                    Button(onClick = onClearHistory, enabled = !isEmpty) {
                        Text(stringResource(id = R.string.clear_history))
                    }
                }
            )
        },
        content = { contentPadding ->
            if (isEmpty) {
                EmptyListView(
                    modifier = Modifier.fillMaxSize(),
                    stringId = R.string.empty_history
                     )
            }
            else {
                // List of search history items

                LazyColumn(
                    contentPadding = contentPadding,
                    modifier = Modifier.padding(16.dp),
                    content = {

                        item {
                            SearchBar(
                                query = searchQuery,
                                onQueryChanged = onTypeQuery
                            )
                        }

                        items(dictionaryItems) { dictionary ->
                            HistoryItemCard(
                                dictionary = dictionary,
                                onDeleteWord = deleteWord,
                                onWordClick = onWordClick,
                                modifier = Modifier.animateItemPlacement(
                                    animationSpec = tween(durationMillis = 1000)
                                )
                            )
                        }
                    })
            }
        }
    )
}


@Preview
@Composable
fun PreviewHistory() {
    HistoryScreen(
        dictionaryItems = persistentListOf(
            DictionaryEntity(sentence = "Magic"),
            DictionaryEntity(sentence = "Invalidate")
        ),
        deleteWord = { },
        onClearHistory = { },
        onWordClick = { },
        searchQuery = "",
        onTypeQuery = { }
    )
}