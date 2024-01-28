package com.jkangangi.en_dictionary.search


import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.jkangangi.en_dictionary.R
import com.jkangangi.en_dictionary.app.data.local.room.DictionaryEntity
import com.jkangangi.en_dictionary.app.data.remote.dto.RequestDTO
import com.jkangangi.en_dictionary.app.theme.En_DictionaryTheme
import com.jkangangi.en_dictionary.app.widgets.TextInput
import com.jkangangi.en_dictionary.settings.fonts.AppFont
import com.jkangangi.en_dictionary.settings.fonts.AppFont.SansSerif

@OptIn(ExperimentalMaterial3Api::class, ExperimentalComposeUiApi::class)

@Composable
fun SearchScreen(
    modifier: Modifier,
    state: SearchScreenState,
    updateQuery: (RequestDTO) -> Unit,
    onSearchClick: () -> Unit,
    toWordDefinition: (DictionaryEntity) -> Unit,
    isDarkTheme: Boolean,
    updateTheme: (Boolean) -> Unit,
    currentFont: AppFont,
    updateFont: (AppFont) -> Unit,
) {
    val keyBoardController =  LocalSoftwareKeyboardController.current
    val showSearchStatus = remember {
        mutableStateOf(false)
    }

    Scaffold(
        modifier = modifier,
        topBar = {
            SearchTopBar(
                isDarkTheme = isDarkTheme,
                toggleTheme = updateTheme,
                font = currentFont,
                updateFont = updateFont
            )
        },
        content = { contentPadding ->
            Column(
                modifier = modifier
                    .padding(contentPadding)
                    .padding(start = 12.dp, end = 12.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(3.dp),
                content = {

                    Row(
                        modifier = modifier.padding(top = 6.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(imageVector = Icons.Default.Search, contentDescription = "search")
                        Text(
                            text = "Search for a word or a phrase",
                            fontFamily = FontFamily.SansSerif,
                            style = MaterialTheme.typography.bodyLarge
                        )
                    }

                    //BeforeTarget
                    TextInput(
                        modifier = modifier,
                        input = state.requests.textBeforeSelection,
                        onInputChange = { updateQuery(state.requests.copy(textBeforeSelection = it)) },
                        txtLabel = stringResource(id = R.string.string_b4),
                        onClearInput = { updateQuery(state.requests.copy(textBeforeSelection = "")) },
                        isValid = state.beforeError
                    )

                    //target
                    TextInput(
                        modifier = modifier,
                        input = state.requests.selection,
                        onInputChange = { updateQuery(state.requests.copy(selection = it)) },
                        txtLabel = stringResource(id = R.string.target),
                        isRequired = true,
                        onClearInput = { updateQuery(state.requests.copy(selection = "")) },
                        isValid = state.targetError
                    )

                    //afterTarget
                    TextInput(
                        modifier = modifier,
                        input = state.requests.textAfterSelection,
                        onInputChange = { updateQuery(state.requests.copy(textAfterSelection = it)) },
                        txtLabel = stringResource(id = R.string.string_after),
                        onClearInput = { updateQuery(state.requests.copy(textAfterSelection = "")) },
                        isValid = state.afterError
                    )

                    Spacer(modifier = modifier)

                    Button(
                        modifier = modifier,
                        enabled = (state.beforeError && state.targetError && state.afterError),
                        onClick = {
                            keyBoardController?.hide()
                            onSearchClick()
                            showSearchStatus.value = true
                        }
                    ) {
                        Text(
                            text = "Search",
                            style = MaterialTheme.typography.bodyMedium
                        )
                    }
                    Spacer(modifier = modifier.height(10.dp))
                    AnimatedVisibility(visible = showSearchStatus.value) {
                        SearchResult(
                            state = state,
                            modifier = modifier,
                            toWordDefinition = toWordDefinition
                        )
                    }

                }
            )
        })
}


@Composable
private fun SearchResult(
    state: SearchScreenState,
    modifier: Modifier,
    toWordDefinition: (DictionaryEntity) -> Unit,
) {

    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier.padding(top = 8.dp),
        content = {
            when {
                state.isLoading -> CircularProgressIndicator()

                state.serverError?.isNotEmpty() == true && state.wordItem == null -> {
                    Text(
                        text = state.serverError,
                        color = MaterialTheme.colorScheme.error,
                        textAlign = TextAlign.Center,
                        modifier = modifier,
                        fontFamily = FontFamily.SansSerif,
                        style = MaterialTheme.typography.bodyMedium,
                    )
                }

                state.serverError == null && state.wordItem == null -> {
                    Text(
                        text = "Word not found, check spelling",
                        color = MaterialTheme.colorScheme.error,
                        textAlign = TextAlign.Center,
                        modifier = modifier,
                        fontFamily = FontFamily.SansSerif,
                        style = MaterialTheme.typography.bodyMedium,
                    )
                }

                state.wordItem != null -> {
                    toWordDefinition(state.wordItem)
                }
            }
        }
    )
}


@Preview
@Composable
private fun SearchScreenPreview() {
    En_DictionaryTheme {
        SearchScreen(
            modifier = Modifier.fillMaxWidth(),
            state = SearchScreenState(),
            updateQuery = { },
            onSearchClick = { },
            toWordDefinition = { },
            isDarkTheme = false,
            updateTheme = { },
            currentFont = SansSerif,
            updateFont = { }
        )
    }
}