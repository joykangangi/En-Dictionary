package com.jkangangi.en_dictionary.search


import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocalLibrary
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.jkangangi.en_dictionary.R
import com.jkangangi.en_dictionary.app.theme.En_DictionaryTheme
import com.jkangangi.en_dictionary.app.widgets.CustomFilledButton
import com.jkangangi.en_dictionary.app.widgets.CustomOutlinedButton
import com.jkangangi.en_dictionary.app.widgets.TextInput
import com.jkangangi.en_dictionary.settings.SettingsEvent
import com.jkangangi.en_dictionary.settings.SettingsState
import com.jkangangi.en_dictionary.settings.fonts.FontBottomSheet
import kotlinx.coroutines.launch


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchScreen(
    settingsState: SettingsState,
    updateSettings: (SettingsEvent) -> Unit,
    searchScreenState: SearchScreenState,
    performEvent: (SearchScreenEvent) -> Unit,
    toWordClick: (String) -> Unit,
    modifier: Modifier,
) {


    val showAdvancedSearch = remember {
        mutableStateOf(false)
    }
    val sheetState = rememberModalBottomSheetState()
    val scope = rememberCoroutineScope()

    Scaffold(
        modifier = modifier,
        topBar = {
            SearchTopBar(
                isDarkTheme = settingsState.darkTheme,
                toggleTheme = { updateSettings(SettingsEvent.UpdateTheme(it)) },
                onFontClick = { scope.launch { sheetState.show() } }
            )
        },
        content = { contentPadding ->
            Column(
                modifier = Modifier
                    .padding(contentPadding)
                    .padding(start = 16.dp, end = 16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.SpaceAround,
                content = {

                    AnimatedContent(
                        targetState = showAdvancedSearch.value,
                        label = "SearchField"
                    ) { isAdvancedSearch ->
                        if (isAdvancedSearch) {
                            AdvancedSearchView(
                                inputState = searchScreenState.inputState,
                                errorState = searchScreenState.errorState,
                                updateQuery = {
                                    performEvent(SearchScreenEvent.UpdateQueries(it))
                                },
                                onSearchClick = { SearchScreenEvent.DoSearch },
                                showAdvancedSearch = showAdvancedSearch
                            )
                        } else {
                            SimpleSearchView(
                                selection = searchScreenState.inputState.selection.value,
                                isSelectionValid = searchScreenState.errorState.targetError,
                                updateQuery = {
                                    performEvent(SearchScreenEvent.UpdateQueries(it))
                                },
                                onSearchClick = {
                                    performEvent(SearchScreenEvent.DoSearch)
                                },
                                showAdvancedSearch = showAdvancedSearch
                            )
                        }
                    }


                    AnimatedVisibility(
                        visible = searchScreenState.errorState.targetError && searchScreenState.errorState.beforeError && searchScreenState.errorState.afterError
                    ) {
                        SearchResult(
                            state = searchScreenState.networkState,
                            toWordClick = toWordClick
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
                            font = settingsState.font,
                            onFontChange = {
                                updateSettings(SettingsEvent.UpdateFonts(it))
                            }
                        )
                    }
                }
            )
        }
    )
}


//Simple Search View
@Composable
private fun SimpleSearchView(
    selection: String,
    isSelectionValid: Boolean,
    updateQuery: (SearchInputEvents) -> Unit,
    onSearchClick: () -> Unit,
    showAdvancedSearch: MutableState<Boolean>,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.SpaceAround,
        horizontalAlignment = Alignment.CenterHorizontally,
        content = {
            SearchDetailsText(detailsTextId = R.string.single_search_detail)

            //target
            TextInput(
                input = selection,
                onInputChange = {
                    SearchScreenEvent.UpdateQueries(
                        SearchInputEvents.UpdateTarget(it)
                    )
                    updateQuery(SearchInputEvents.UpdateTarget(it))
                },
                txtLabel = stringResource(id = R.string.target),
                txtPlaceholder = stringResource(id = R.string.single_placeholder),
                isRequired = true,
                onClearInput = { updateQuery(SearchInputEvents.UpdateTarget(targetInput = "")) },
                isValid = isSelectionValid
            )


            Spacer(modifier = Modifier.height(8.dp))

            SearchButtons(
                onSearchClick = onSearchClick,
                enabled = isSelectionValid,
                showAdvancedSearch = showAdvancedSearch
            )
            Spacer(modifier = Modifier.height(10.dp))
        }
    )

}


// Advanced Search View

@Composable
private fun AdvancedSearchView(
    inputState: SearchInputState,
    errorState: SearchInputErrorState,
    updateQuery: (SearchInputEvents) -> Unit,
    onSearchClick: () -> Unit,
    showAdvancedSearch: MutableState<Boolean>,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.SpaceAround,
        horizontalAlignment = Alignment.CenterHorizontally,
        content = {
            SearchDetailsText(detailsTextId = R.string.adv_search_detail)
            Text(
                text = stringResource(id = R.string.adv_placeholder),
                style = MaterialTheme.typography.bodySmall
            )
            Spacer(modifier = Modifier.height(8.dp))


            //BeforeTarget
            TextInput(
                input = inputState.beforeSelection.value,
                onInputChange = {
                    updateQuery(SearchInputEvents.UpdateBeforeSelection(it))
                },
                txtLabel = stringResource(id = R.string.string_b4),
                txtPlaceholder = stringResource(id = R.string.b4_placeholder),
                onClearInput = { updateQuery(SearchInputEvents.UpdateBeforeSelection(beforeInput = "")) },
                isValid = errorState.beforeError
            )

            //target
            TextInput(
                input = inputState.selection.value,
                onInputChange = { updateQuery(SearchInputEvents.UpdateTarget(it)) },
                txtLabel = stringResource(id = R.string.target),
                txtPlaceholder = stringResource(id = R.string.target_placeholder),
                isRequired = true,
                onClearInput = { updateQuery(SearchInputEvents.UpdateTarget(targetInput = "")) },
                isValid = errorState.targetError
            )

            //afterTarget
            TextInput(
                modifier = modifier,
                input = inputState.afterSelection.value,
                onInputChange = { updateQuery(SearchInputEvents.UpdateAfterSelection(it)) },
                txtLabel = stringResource(id = R.string.string_after),
                txtPlaceholder = stringResource(id = R.string.after_placeholder),
                onClearInput = { updateQuery(SearchInputEvents.UpdateAfterSelection(afterInput = "")) },
                isValid = errorState.afterError
            )

            Spacer(modifier = modifier.height(8.dp))

            SearchButtons(
                onSearchClick = onSearchClick,
                enabled = errorState.targetError && errorState.beforeError && errorState.afterError,
                showAdvancedSearch = showAdvancedSearch
            )
            Spacer(modifier = Modifier.height(10.dp))

        }
    )
}

/**
 * Shared Components between Single and Advanced Search Views
 */
//Shown at the top of [SearchScreen]
@Composable
private fun SearchDetailsText(
    detailsTextId: Int,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier.padding(top = 8.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        content = {
            Icon(
                imageVector = Icons.Default.LocalLibrary,
                contentDescription = stringResource(id = R.string.search_btn),
                modifier = Modifier
                    .size(40.dp),
            )

            Text(
                text = stringResource(id = detailsTextId),
                style = MaterialTheme.typography.bodyLarge,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(12.dp))
        }
    )
}

//The 2 buttons in [SearchScreen]
@Composable
private fun SearchButtons(
    onSearchClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean,
    showAdvancedSearch: MutableState<Boolean>,
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(IntrinsicSize.Min)
            .padding(8.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        content = {
            CustomOutlinedButton(
                modifier = Modifier.fillMaxHeight(),
                onBtnClicked = {
                    showAdvancedSearch.value = !showAdvancedSearch.value
                },
                buttonTextId = if (showAdvancedSearch.value) R.string.simple_search_btn else R.string.adv_search_btn
            )

            CustomFilledButton(
                modifier = Modifier
                    .fillMaxHeight(),
                onBtnClicked = onSearchClick,
                buttonTextId = R.string.search_btn,
                isEnabled = enabled
            )
        }
    )
}

//Results from Searching in [SearchScreen]
@Composable
private fun SearchResult(
    state: SearchResultUiState,
    toWordClick: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    val scope = rememberCoroutineScope()

    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier.padding(top = 8.dp),
        content = {
            when (state) {
                is SearchResultUiState.Error -> {
                    Text(
                        text = state.serverError,
                        color = MaterialTheme.colorScheme.error,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.fillMaxWidth(),
                        style = MaterialTheme.typography.bodyMedium,
                    )
                }

                is SearchResultUiState.Success -> {
                    SideEffect {
                        scope.launch {
                            toWordClick(state.wordItem.sentence)
                        }
                    }
                }

                is SearchResultUiState.Loading -> CircularProgressIndicator()
                is SearchResultUiState.Idle -> {}
                is SearchResultUiState.EmptyBody -> {
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


@Preview(showBackground = true)
@Composable
private fun SearchScreenPreview() {
    En_DictionaryTheme {
        SearchScreen(
            performEvent = { },
            settingsState = SettingsState(),
            updateSettings = { },
            searchScreenState = SearchScreenState(),
            toWordClick = { },
            modifier = Modifier
        )
    }
}