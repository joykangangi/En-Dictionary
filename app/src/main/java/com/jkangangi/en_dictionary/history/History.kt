package com.jkangangi.en_dictionary.history

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Button
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.jkangangi.en_dictionary.R

//todo room
data class HistoryItem(val word: String)


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HistoryScreen(
    historyItems: List<HistoryItem>,//Todo ImmutableList
    onClearHistory: () -> Unit,
    deleteWord: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val isEmpty = remember { mutableStateOf(historyItems.isEmpty()) }

    Scaffold(
        modifier = modifier
            .shadow(elevation = 3.dp)
            .padding(16.dp),
        topBar = {
            TopAppBar(
                title = { },
                actions = {
                    Button(onClick = onClearHistory, enabled = !isEmpty.value,) {
                        Text(stringResource(id = R.string.clear_history))
                    }
                })
        },
        content = { contentPadding ->
            if (isEmpty.value) {
                EmptyContent(
                    modifier = modifier.fillMaxSize(),
                    text = stringResource(id = R.string.empty_history),
                )
            } else {
                // List of search history items
                LazyColumn(
                    contentPadding = contentPadding,
                    modifier = modifier.padding(8.dp),
                    content = {
                    items(historyItems) { item ->
                        HistoryItemCard(
                            history = item,
                            onDeleteWord = deleteWord,
                        )
                    }
                })
            }
        },
    )
}

@Composable
fun HistoryItemCard(
    history: HistoryItem,
    onDeleteWord: () -> Unit,
) {
    ElevatedCard(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        shape = RoundedCornerShape(8.dp),
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(text = history.word, style = MaterialTheme.typography.headlineSmall)
            IconButton(onClick = onDeleteWord, content = {
                Icon(
                    imageVector = Icons.Default.Delete,
                    contentDescription = stringResource(id = R.string.delete),
                )
            })
        }
    }
}

@Preview
@Composable
fun PreviewHistory() {
    HistoryScreen(
        historyItems = emptyList(),
        deleteWord = { },
        onClearHistory = { }
    )
}