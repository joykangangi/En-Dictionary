package com.jkangangi.en_dictionary.search

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.DarkMode
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.WbSunny
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.jkangangi.en_dictionary.R
import com.jkangangi.en_dictionary.app.data.model.Word
import com.jkangangi.en_dictionary.app.theme.En_DictionaryTheme

@OptIn(ExperimentalMaterial3Api::class, ExperimentalComposeUiApi::class)
@Composable
fun SearchScreen(
    modifier: Modifier,
    isDarkTheme: Boolean,
    toggleTheme: (Boolean) -> Unit,
    query: String,
    updateQuery: (String) -> Unit,
    searchWord: (String) -> Unit,
    state: SearchScreenState,
    onWordClick: (Word) -> Unit,
    onClearInput: () -> Unit,
) {

    val keyBoardController = LocalSoftwareKeyboardController.current

    val onSearchWord = remember {
        {
            searchWord(query)
            keyBoardController?.hide()
        }
    }

    Scaffold(
        modifier = modifier,
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
                modifier = modifier
                    .padding(12.dp)
                    .padding(contentPadding)
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(5.dp),
                content = {
                    Image(
                        painter = painterResource(id = R.drawable.dictionary),
                        contentDescription = null,
                        modifier = modifier.size(200.dp)
                    )
                    Text(
                        text = stringResource(id = R.string.home_body),
                        style = MaterialTheme.typography.bodyMedium,
                    )

                    OutlinedTextField(
                        value = query,
                        onValueChange = { updateQuery(it) },
                        singleLine = true,
                        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
                        keyboardActions = KeyboardActions(onSearch = { onSearchWord() }),
                        trailingIcon = {
                            if (query.isNotBlank())
                                IconButton(onClick = { onSearchWord() }) {
                                    Icon(
                                        imageVector = Icons.Default.Search,
                                        contentDescription = null
                                    )
                                }
                        },
                        placeholder = { Text(text = "Search...") },
                        leadingIcon = {
                            if (query.isNotBlank())
                                IconButton(onClick = onClearInput) {
                                    Icon(
                                        imageVector = Icons.Default.Close,
                                        contentDescription = null
                                    )
                                }
                        }
                    )
                    Spacer(modifier = modifier)

                    Box(
                        modifier = modifier,
                        content = {

                            LazyColumn(
                                modifier = modifier
                                    .fillMaxSize()
                                    .animateContentSize(),
                                contentPadding = PaddingValues(12.dp)
                            ) {
                                items(state.wordItems) { word: Word ->
                                    ElevatedCard(
                                        modifier = modifier
                                            .fillMaxWidth()
                                            .padding(vertical = 8.dp),
                                        onClick = { onWordClick(word) }) {
                                        Text(
                                            text = word.word,
                                            textAlign = TextAlign.Center,
                                            style = MaterialTheme.typography.titleMedium
                                        )
                                    }
                                }
                            }
                            if (state.isLoading) {
                                CircularProgressIndicator(modifier = modifier.align(Alignment.Center))
                            }

                            //if there is an error, message
                            if (state.error.isNotBlank()) {
                                Text(
                                    text = state.error,
                                    color = MaterialTheme.colorScheme.error,
                                    textAlign = TextAlign.Center,
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(horizontal = 20.dp)
                                        .align(Alignment.Center)
                                )
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

    En_DictionaryTheme {
        SearchScreen(
            modifier = Modifier,
            isDarkTheme = false,
            toggleTheme = { },
            query = "",
            updateQuery = { },
            state = SearchScreenState(),
            onWordClick = { },
            onClearInput = { },
            searchWord = { }
        )
    }
}