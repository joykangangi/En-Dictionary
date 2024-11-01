package com.jkangangi.en_dictionary.search.screens


import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.jkangangi.en_dictionary.R
import com.jkangangi.en_dictionary.app.data.model.DictionaryTabOptions
import com.jkangangi.en_dictionary.app.theme.En_DictionaryTheme
import com.jkangangi.en_dictionary.app.theme.largePadding
import com.jkangangi.en_dictionary.app.theme.largeSpacer
import com.jkangangi.en_dictionary.app.theme.smallSpacer
import com.jkangangi.en_dictionary.app.widgets.DynamicTabSelector
import com.jkangangi.en_dictionary.search.SearchResultUiState
import com.jkangangi.en_dictionary.search.SearchScreenEvent
import com.jkangangi.en_dictionary.search.SearchScreenState
import com.jkangangi.en_dictionary.search.SearchTopBar
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

    val selectedTab = remember {
        mutableIntStateOf(0)
    }

    val searchTabOptions = listOf(
        DictionaryTabOptions(
            nameId = R.string.simple_search_btn,
            searchOptionView = {
                BasicSearchView(
                    selection = searchScreenState.inputState.selection.value,
                    isSelectionValid = searchScreenState.errorState.targetError,
                    searchResultUiState = searchScreenState.networkState,
                    errorState = searchScreenState.errorState,
                    updateQuery = {
                        performEvent(SearchScreenEvent.UpdateQueries(it))
                    },
                    onSearchClick = {
                        performEvent(SearchScreenEvent.DoSearch)
                    },
                    toWordClick = toWordClick
                )
            }
        ),
        DictionaryTabOptions(
            nameId = R.string.adv_search_btn,
            searchOptionView = {
                ContextualSearchView(
                    inputState = searchScreenState.inputState,
                    errorState = searchScreenState.errorState,
                    searchResultUiState = searchScreenState.networkState,
                    updateQuery = {
                        performEvent(SearchScreenEvent.UpdateQueries(it))
                    },
                    onSearchClick = { SearchScreenEvent.DoSearch },
                    toWordClick = toWordClick
                )
            }
        ),
    )

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
                    .padding(start = largePadding(), end = largePadding()),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.SpaceAround,
                content = {
                    largeSpacer()

                    DynamicTabSelector(
                        tabs = searchTabOptions,
                        selectedTab = selectedTab.intValue,
                        onTabSelected = {
                            selectedTab.intValue = it
                        }
                    )

                    smallSpacer()

                    Box(modifier = Modifier.fillMaxSize()) {
                        searchTabOptions[selectedTab.intValue].searchOptionView()
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



//Results from Searching in [SearchScreen]
@Composable
fun SearchResult(
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