package com.jkangangi.en_dictionary.search

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.DarkMode
import androidx.compose.material.icons.filled.WbSunny
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.jkangangi.en_dictionary.R
import com.jkangangi.en_dictionary.app.data.model.Dictionary
import com.jkangangi.en_dictionary.app.data.remote.dto.RequestDTO
import com.jkangangi.en_dictionary.app.widgets.TextInput

//todo update modifiers
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

                    //B4
                    TextInput(
                        modifier = modifier.fillMaxWidth(),
                        input = state.requests.textBeforeSelection,
                        onInputChange = { updateQuery(state.requests.copy(textBeforeSelection = it)) },
                        txtLabel = stringResource(id = R.string.string_b4),
                        isRequired = false
                    )

                    //target
                    TextInput(
                        modifier = modifier.fillMaxWidth(),
                        input = state.requests.selection,
                        onInputChange = { updateQuery(state.requests.copy(selection = it)) },
                        txtLabel = stringResource(id = R.string.target),
                        isRequired = true
                    )

                    //after
                    TextInput(
                        modifier = modifier.fillMaxWidth(),
                        input = state.requests.textAfterSelection,
                        onInputChange = { updateQuery(state.requests.copy(textAfterSelection = it)) },
                        txtLabel = stringResource(id = R.string.string_after),
                        isRequired = false,
                        imeAction = ImeAction.Done
                    )

                    Spacer(modifier = modifier)

                    OutlinedButton(onClick = onSearchClick) {
                        Text(text = "Search")
                    }

                    Box(
                        modifier = modifier,
                        content = {

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
                            } else {
                                ElevatedCard(
                                    modifier = modifier
                                        .fillMaxWidth()
                                        .padding(8.dp),
                                    onClick = { state.wordItem?.let { onWordClick(it) } },
                                    content = {
                                        Text(
                                            text = state.wordItem?.target ?: "",
                                            textAlign = TextAlign.Center,
                                            style = MaterialTheme.typography.titleMedium
                                        )
                                        Spacer(modifier = modifier.height(8.dp))
                                    }
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