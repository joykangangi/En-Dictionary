package com.jkangangi.en_dictionary.search

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DarkMode
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.WbSunny
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
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
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.jkangangi.en_dictionary.R
import com.jkangangi.en_dictionary.app.data.model.Dictionary
import com.jkangangi.en_dictionary.app.data.remote.dto.RequestDTO
import com.jkangangi.en_dictionary.app.widgets.TextInput

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchScreen(
    modifier: Modifier,
    isDarkTheme: Boolean,
    toggleTheme: (Boolean) -> Unit,
    state: SearchScreenState,
    updateQuery: (RequestDTO) -> Unit,
    onSearchClick: () -> Unit,
    onWordClick: (Dictionary) -> Unit,
) {


    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = stringResource(id = R.string.home_text)) },
                actions = {
                    IconButton(onClick = { toggleTheme(isDarkTheme) }) {
                        Icon(
                            imageVector = if (isDarkTheme) Icons.Default.DarkMode else Icons.Default.WbSunny,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.primary
                        )
                    }
                },
                modifier = modifier.shadow(elevation = 2.dp),
            )
        },
        content = { contentPadding ->
            Column(
                modifier = modifier.padding(contentPadding),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(5.dp),
                content = {

                    Row(modifier = modifier, verticalAlignment = Alignment.CenterVertically) {
                        Text(text = "Search for a word or a phrase")
                        Icon(imageVector = Icons.Default.Search, contentDescription = "search")
                    }

                    //B4
                    TextInput(
                        modifier = modifier,
                        input = state.requests.textBeforeSelection,
                        onInputChange = { updateQuery(state.requests.copy(textBeforeSelection = it)) },
                        txtLabel = stringResource(id = R.string.string_b4),
                        isRequired = false,
                        onClearInput = { updateQuery(state.requests.copy(textBeforeSelection = "")) }
                    )

                    //target
                    TextInput(
                        modifier = modifier,
                        input = state.requests.selection,
                        onInputChange = { updateQuery(state.requests.copy(selection = it)) },
                        txtLabel = stringResource(id = R.string.target),
                        isRequired = true,
                        onClearInput = { updateQuery(state.requests.copy(selection = "")) }
                    )

                    //after
                    TextInput(
                        modifier = modifier,
                        input = state.requests.textAfterSelection,
                        onInputChange = { updateQuery(state.requests.copy(textAfterSelection = it)) },
                        txtLabel = stringResource(id = R.string.string_after),
                        isRequired = false,
                        imeAction = ImeAction.Done,
                        onClearInput = { updateQuery(state.requests.copy(textAfterSelection = "")) }
                    )

                    Spacer(modifier = modifier)

                    Button(
                        modifier = modifier,
                        onClick = onSearchClick
                    ) {
                        Text(text = "Search")
                    }

                    Box(
                        contentAlignment = Alignment.Center,
                        content = {

                            if (state.isLoading) {
                                CircularProgressIndicator()
                            }

                            //if there is an error, message
                            if (state.error.isNotBlank()) {
                                Text(
                                    text = state.error,
                                    color = MaterialTheme.colorScheme.error,
                                    textAlign = TextAlign.Center,
                                    modifier = modifier
                                )
                            } else {
                                state.wordItem?.let { word ->
                                    ElevatedCard(
                                        modifier = modifier,
                                        onClick = { onWordClick(word) },
                                        content = {
                                            Text(
                                                text = word.target,
                                                textAlign = TextAlign.Center,
                                                style = MaterialTheme.typography.titleMedium
                                            )
                                            Spacer(modifier = modifier.height(12.dp))
                                        }
                                    )
                                }
                            }
                        },
                    )
                },
            )
        },
    )
}

@Preview
@Composable
private fun HomePreview() {

    /* En_DictionaryTheme {
         SearchScreen(
             modifier = Modifier,
             isDarkTheme = false,
             toggleTheme = { },
             queryT = "",
             updateQueryT = { },
             queryA = "",
             updateQueryA = { },
             queryB = "",
             updateQueryB = { },
             state = SearchScreenState(),
             onWordClick = { },
             onClearInputT = { },
             onClearInputA = { },
             onClearInputB = { },
             searchWord = { }
         )
     }*/
}