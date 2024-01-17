package com.jkangangi.en_dictionary.history

import androidx.compose.foundation.ExperimentalFoundationApi
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
import androidx.compose.ui.draw.shadow
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
    modifier: Modifier = Modifier,
) {
    val isEmpty = dictionaryItems.isEmpty()

    Scaffold(
        modifier = modifier
            .shadow(elevation = 3.dp),
        topBar = {
            TopAppBar(
                title = { },
                actions = {
                    Button(onClick = onClearHistory, enabled = !isEmpty) {
                        Text(stringResource(id = R.string.clear_history))
                    }
                })
        },
        content = { contentPadding ->
            if (isEmpty) {
                EmptyListView(stringId = R.string.empty_history)
            } else {
                // List of search history items
                LazyColumn(
                    contentPadding = contentPadding,
                    modifier = modifier.padding(8.dp),
                    content = {
                        items(dictionaryItems) { dictionary ->
                            HistoryItemCard(
                                dictionary = dictionary,
                                onDeleteWord = deleteWord,
                                modifier = modifier.animateItemPlacement()
                            )
                        }
                    })
            }
        },
    )
}


@Preview
@Composable
fun PreviewHistory() {
    HistoryScreen(
        dictionaryItems = persistentListOf(),
        deleteWord = { },
        onClearHistory = { }
    )
}