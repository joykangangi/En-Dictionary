package com.jkangangi.en_dictionary.saved

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.jkangangi.en_dictionary.R
import com.jkangangi.en_dictionary.app.theme.En_DictionaryTheme
import com.jkangangi.en_dictionary.history.EmptyContent

//Todo Table 1 Room
data class SavedItem(
    val word: String,
    val phonetics: String,
    val meaning: String
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SavedWords(
    savedWords: List<SavedItem>,
    onDeleteClicked: (SavedItem) -> Unit,
    doSort: Boolean,
    sortWords: (Boolean) -> Unit,
    modifier: Modifier = Modifier,
) {
    Scaffold(
        modifier = modifier
            .shadow(elevation = 3.dp),
        topBar = {
            TopAppBar(
                title = { },
                actions = {
                    FilterChip(
                        selected = doSort,
                        onClick = { sortWords(!doSort) },
                        label = { Text(text = stringResource(id = R.string.sort_abc)) },
                        leadingIcon = {
                            if (doSort) {
                                Icon(
                                    imageVector = Icons.Default.CheckCircle,
                                    contentDescription = stringResource(
                                        id = R.string.sort_abc
                                    ),
                                )
                            }
                        },
                        shape = RoundedCornerShape(50)
                    )
                },
            )
        },
        content = { contentPadding ->
            if (savedWords.isEmpty()) {
                EmptyContent(
                    modifier = modifier.fillMaxSize(),
                    text = stringResource(id = R.string.empty_saves),
                )

            } else {
                LazyColumn(contentPadding = contentPadding) {
                    items(savedWords) { savedItem ->
                        SavedWordCard(item = savedItem, onDeleteClicked = onDeleteClicked)
                    }
                }
            }
        })
}

@Composable
fun SavedWordCard(
    item: SavedItem,
    onDeleteClicked: (SavedItem) -> Unit,
    modifier: Modifier = Modifier,
) {
    ElevatedCard(
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp),
        ) {
        Column(modifier = modifier.padding(8.dp)) {
            Row(
                modifier = modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Text(
                    text = item.word,
                    style = TextStyle(fontSize = 20.sp, fontWeight = FontWeight.Bold)
                )

                IconButton(
                    onClick = { onDeleteClicked(item) }
                ) {
                    Icon(
                        Icons.Default.Delete,
                        contentDescription = stringResource(id = R.string.delete)
                    )
                }
            }

            Text(
                text = item.phonetics,
                style = TextStyle(
                    fontSize = 16.sp,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            )
            Spacer(modifier = modifier.height(16.dp))
            Text(
                text = item.meaning,
                style = TextStyle(fontSize = 18.sp)
            )
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
fun PreviewSavedWords() {
    En_DictionaryTheme {
        Scaffold {
            val saves = listOf(
                SavedItem(word = "goat", phonetics = "/gout/", meaning = "A herbivore"),
                SavedItem(
                    word = "cat",
                    phonetics = "/caet/",
                    meaning = "A pet that is of the lion family. It is kept to catch rats and aesthetics"
                ),
                SavedItem(
                    word = "Bury",
                    phonetics = "/bery/",
                    meaning = "To dig and put something inside the soil"
                ),
            )
            SavedWords(
                savedWords = saves,
                modifier = Modifier.padding(it),
                onDeleteClicked = { },
                doSort = true,
                sortWords = { }
            )
        }
    }
}