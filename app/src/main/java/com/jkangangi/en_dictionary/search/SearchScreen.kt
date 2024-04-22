package com.jkangangi.en_dictionary.search


import androidx.compose.animation.AnimatedContent
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
import com.jkangangi.en_dictionary.app.data.local.room.DictionaryEntity
import com.jkangangi.en_dictionary.app.theme.En_DictionaryTheme
import com.jkangangi.en_dictionary.app.widgets.CustomFilledButton
import com.jkangangi.en_dictionary.app.widgets.CustomOutlinedButton
import com.jkangangi.en_dictionary.app.widgets.TextInput
import com.jkangangi.en_dictionary.settings.fonts.AppFont
import com.jkangangi.en_dictionary.settings.fonts.AppFont.SansSerif
import com.jkangangi.en_dictionary.settings.fonts.FontBottomSheet
import kotlinx.coroutines.launch


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchScreen(
    isDarkTheme: Boolean,
    updateTheme: (Boolean) -> Unit,
    currentFont: AppFont,
    updateFont: (AppFont) -> Unit,
    textBeforeSelection: String,
    selection: String,
    textAfterSelection: String,
    errorState: SearchInputErrorState,
    networkState: SearchResultUiState,
    updateQuery: (SearchInputEvents) -> Unit,
    onSearchClick: () -> Unit,
    toWordClick: (DictionaryEntity) -> Unit,
    modifier: Modifier = Modifier,
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
                    
                    AnimatedContent(
                        targetState = showAdvancedSearch.value,
                        label = "SearchField"
                    ) { isAdvancedSearch ->
                        if (isAdvancedSearch) {
                            AdvancedSearchView(
                                textBeforeSelection = textBeforeSelection,
                                selection = selection,
                                textAfterSelection = textAfterSelection,
                                errorState = errorState,
                                updateQuery = updateQuery,
                                onSearchClick = onSearchClick,
                                showAdvancedSearch = showAdvancedSearch
                            )
                        } else {
                            SimpleSearchView(
                                selection = selection,
                                isSelectionValid = errorState.targetError,
                                updateQuery = updateQuery,
                                onSearchClick = onSearchClick,
                                showAdvancedSearch = showAdvancedSearch
                            )
                        }
                    }


                    AnimatedVisibility(
                        visible = errorState.targetError && errorState.beforeError && errorState.afterError
                    ) {
                        SearchResult(
                            state = networkState,
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
                            font = currentFont,
                            onFontChange = updateFont
                        )
                    }
                }
            )
        }
    )
}


//Simple Search View
@Composable
fun SimpleSearchView(
    selection: String,
    isSelectionValid: Boolean,
    updateQuery: (SearchInputEvents) -> Unit,
    onSearchClick: () -> Unit,
    showAdvancedSearch: MutableState<Boolean>,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier,
        content = {
            SearchDetailsText(detailsTextId = R.string.single_search_detail)
            Spacer(modifier = Modifier.height(8.dp))

            //target
            TextInput(
                input = selection,
                onInputChange = {
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
fun AdvancedSearchView(
    textBeforeSelection: String,
    selection: String,
    textAfterSelection: String,
    errorState: SearchInputErrorState,
    updateQuery: (SearchInputEvents) -> Unit,
    onSearchClick: () -> Unit,
    showAdvancedSearch: MutableState<Boolean>,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier,
        content = {
            SearchDetailsText(detailsTextId = R.string.adv_search_detail)
            Text(
                text = stringResource(id = R.string.adv_placeholder),
                style = MaterialTheme.typography.bodySmall
            )
            Spacer(modifier = Modifier.height(8.dp))


            //BeforeTarget
            TextInput(
                input = textBeforeSelection,
                onInputChange = { updateQuery(SearchInputEvents.UpdateBeforeSelection(it)) },
                txtLabel = stringResource(id = R.string.string_b4),
                txtPlaceholder = stringResource(id = R.string.b4_placeholder),
                onClearInput = { updateQuery(SearchInputEvents.UpdateBeforeSelection(beforeInput = "")) },
                isValid = errorState.beforeError
            )

            //target
            TextInput(
                input = selection,
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
                input = textAfterSelection,
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
fun SearchDetailsText(
    detailsTextId: Int,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier.padding(top = 8.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center,
        content = {
            Icon(
                imageVector = Icons.Default.Search,
                contentDescription = stringResource(id = R.string.search_btn),
                modifier = Modifier
                    .size(40.dp),
                   // .weight(0.4f),
                tint = MaterialTheme.colorScheme.primary
            )

            Text(
                text = stringResource(id = detailsTextId),
                style = MaterialTheme.typography.bodyLarge,
                fontWeight = FontWeight.Bold
            )
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
            .padding(8.dp),
        horizontalArrangement = Arrangement.spacedBy(4.dp),
        content = {
            CustomOutlinedButton(
                modifier = Modifier.weight(0.4f),
                onBtnClicked = {
                    showAdvancedSearch.value = !showAdvancedSearch.value
                },
                buttonTextId = if (showAdvancedSearch.value) R.string.simple_search_btn else R.string.adv_search_btn
            )

            CustomFilledButton(
                modifier = Modifier.weight(0.4f),
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
    toWordClick: (DictionaryEntity) -> Unit,
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
                            toWordClick(state.wordItem)
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


@Preview(apiLevel = 33, showBackground = true)
@Composable
private fun SearchScreenPreview() {
    En_DictionaryTheme {
        SearchScreen(
            errorState = SearchInputErrorState(),
            updateQuery = { },
            onSearchClick = { },
            isDarkTheme = false,
            updateTheme = { },
            currentFont = SansSerif,
            updateFont = { },
            textBeforeSelection = "",
            selection = "",
            textAfterSelection = "",
            networkState = SearchResultUiState.Idle,
            toWordClick = { }
        )
    }
}