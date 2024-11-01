package com.jkangangi.en_dictionary.search

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.jkangangi.en_dictionary.app.util.DictionaryViewModelFactory
import com.jkangangi.en_dictionary.search.screens.SearchScreen
import com.jkangangi.en_dictionary.settings.SettingsViewModel
import kotlinx.serialization.Serializable

@Serializable
data object MainSearchRoute

fun NavController.navigateToMainSearch(navOptions: NavOptions) = navigate(route = MainSearchRoute, navOptions = navOptions)

fun NavGraphBuilder.mainSearchGraph(
    toWordDetailClick: (String) -> Unit,
) {
    composable<MainSearchRoute> {
        MainSearchScreen(toWordDetailClick = toWordDetailClick)
    }
}


@Composable
internal fun MainSearchScreen(
    toWordDetailClick: (String) -> Unit,
    modifier: Modifier = Modifier,
    searchViewModel: SearchViewModel = viewModel(factory = DictionaryViewModelFactory),
    settingsViewModel: SettingsViewModel = viewModel(factory = SettingsViewModel.Factory),
) {

    val toWordClick: (String) -> Unit = remember {
        {
            searchViewModel.clearState()
            toWordDetailClick(it)
        }
    }
    val searchScreenState by searchViewModel.searchScreenState.collectAsState()
    val settingsState by settingsViewModel.settingsState.collectAsState()

    SearchScreen(
        modifier = modifier,
        settingsState = settingsState,
        updateSettings = settingsViewModel::updateSettings,
        searchScreenState = searchScreenState,
        performEvent = searchViewModel::performEvent,
        toWordClick = toWordClick
    )
}