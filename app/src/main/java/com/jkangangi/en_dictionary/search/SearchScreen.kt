package com.jkangangi.en_dictionary.search


import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.jkangangi.en_dictionary.R
import com.jkangangi.en_dictionary.app.theme.En_DictionaryTheme
import com.jkangangi.en_dictionary.app.widgets.TextInput
import com.jkangangi.en_dictionary.settings.fonts.AppFont
import com.jkangangi.en_dictionary.settings.fonts.AppFont.SansSerif
import com.jkangangi.en_dictionary.settings.fonts.FontBottomSheet
import kotlinx.coroutines.launch


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchScreen(
    state: SearchScreenState,
    updateQuery: (SearchInputEvents) -> Unit,
    onSearchClick: () -> Unit,
    isDarkTheme: Boolean,
    updateTheme: (Boolean) -> Unit,
    currentFont: AppFont,
    updateFont: (AppFont) -> Unit,
    textBeforeSelection: String,
    selection: String,
    textAfterSelection: String,
    modifier: Modifier = Modifier,
) {
    val keyBoardController = LocalSoftwareKeyboardController.current
    val showSearchStatus = remember {
        mutableStateOf(false)
    }
    val sheetState = rememberModalBottomSheetState()
    val scope = rememberCoroutineScope()


    Scaffold(
        modifier = modifier,
        topBar = {
            SearchTopBar(
                isDarkTheme = isDarkTheme,
                toggleTheme = updateTheme,
                onFontClick = { scope.launch { sheetState.show() } }
            )
        },
        content = { contentPadding ->
            Column(
                modifier = Modifier
                    .padding(contentPadding)
                    .padding(start = 12.dp, end = 12.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(8.dp),
                content = {

                    Row(
                        modifier = Modifier.padding(top = 8.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(5.dp),
                        content = {
                            Icon(
                                imageVector = Icons.Default.Search,
                                contentDescription = "search",
                                modifier = Modifier.size(40.dp)
                            )
                            Text(
                                text = "Search for a word or a phrase",
                                style = MaterialTheme.typography.bodyLarge,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    //BeforeTarget
                    TextInput(
                        input = textBeforeSelection,
                        onInputChange = { updateQuery(SearchInputEvents.UpdateBeforeSelection(it)) },
                        txtLabel = stringResource(id = R.string.string_b4),
                        onClearInput = { updateQuery(SearchInputEvents.UpdateBeforeSelection(beforeInput = "")) },
                        isValid = state.beforeError
                    )

                    //target
                    TextInput(
                        input = selection,
                        onInputChange = { updateQuery(SearchInputEvents.UpdateTarget(it)) },
                        txtLabel = stringResource(id = R.string.target),
                        isRequired = true,
                        onClearInput = { updateQuery(SearchInputEvents.UpdateTarget(targetInput = "")) },
                        isValid = state.targetError
                    )

                    //afterTarget
                    TextInput(
                        input = textAfterSelection,
                        onInputChange = { updateQuery(SearchInputEvents.UpdateAfterSelection(it)) },
                        txtLabel = stringResource(id = R.string.string_after),
                        onClearInput = { updateQuery(SearchInputEvents.UpdateAfterSelection(afterInput = "")) },
                        isValid = state.afterError
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    Button(
                        modifier = Modifier.height(48.dp),
                        enabled = (state.beforeError && state.targetError && state.afterError),
                        onClick = {
                            keyBoardController?.hide()
                            onSearchClick()
                            showSearchStatus.value = true
                        },
                    ) {
                        Text(
                            text = "Search",
                            style = MaterialTheme.typography.bodyMedium,
                        )
                    }
                    Spacer(modifier = Modifier.height(10.dp))
                    AnimatedVisibility(visible = showSearchStatus.value) {
                        SearchResult(
                            state = state,
                        )
                    }

                    AnimatedVisibility(
                        visible = sheetState.isVisible,
                        enter = slideInVertically(
                            initialOffsetY = { it / 2 }
                        ),
                        exit = slideOutVertically(targetOffsetY = { it })
                    ) {
                        FontBottomSheet(
                            sheetState = sheetState,
                            onDismissSheet = { scope.launch { sheetState.hide() } },
                            font = currentFont,
                            onFontChange = updateFont
                        )
                    }
                }
            )
        }
    )
}

@Composable
private fun SearchResult(
    state: SearchScreenState,
    modifier: Modifier = Modifier,
) {

    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier.padding(top = 8.dp),
        content = {
            when {
                state.isLoading -> CircularProgressIndicator()

                state.serverError?.isNotEmpty() == true -> {
                    Text(
                        text = state.serverError,
                        color = MaterialTheme.colorScheme.error,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.fillMaxWidth(),
                        fontFamily = FontFamily.SansSerif,
                        style = MaterialTheme.typography.bodyMedium,
                    )
                }

                else -> {
                    Text(
                        text = "Word not found, check spelling",
                        color = MaterialTheme.colorScheme.error,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.fillMaxWidth(),
                        style = MaterialTheme.typography.bodyMedium,
                    )
                }
            }
        }
    )
}


@Preview(apiLevel = 33, showBackground = true)
@Composable
private fun SearchScreenPreview() {
    En_DictionaryTheme {
        SearchScreen(
            state = SearchScreenState(),
            updateQuery = { },
            onSearchClick = { },
            isDarkTheme = false,
            updateTheme = { },
            currentFont = SansSerif,
            updateFont = { },
            textBeforeSelection = "",
            selection = "",
            textAfterSelection = ""
        )
    }
}