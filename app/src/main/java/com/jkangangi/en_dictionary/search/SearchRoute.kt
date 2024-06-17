package com.jkangangi.en_dictionary.search

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.bumble.appyx.core.modality.BuildContext
import com.bumble.appyx.core.node.Node
import com.bumble.appyx.navmodel.backstack.BackStack
import com.bumble.appyx.navmodel.backstack.operation.push
import com.jkangangi.en_dictionary.app.navigation.Route
import com.jkangangi.en_dictionary.app.util.DictionaryViewModelFactory
import com.jkangangi.en_dictionary.settings.SettingsViewModel

class SearchRoute(
    buildContext: BuildContext,
    private val backStack: BackStack<Route>,
) : Node(buildContext = buildContext) {

    @Composable
    override fun View(modifier: Modifier) {

        SearchScreenView(modifier = modifier)
    }

    @Composable
    fun SearchScreenView(
        modifier: Modifier,
        searchViewModel: SearchViewModel = viewModel(factory = DictionaryViewModelFactory),
        settingsViewModel: SettingsViewModel = viewModel(factory = SettingsViewModel.Factory),
    ) {

        val searchScreenState by searchViewModel.searchScreenState.collectAsState()
        val settingsState by settingsViewModel.settingsState.collectAsState()

        val toWordClick: (String) -> Unit = {
            searchViewModel.clearState()
            backStack.push(Route.SearchDetail(sentence = it))
        }


        SearchScreen(
            modifier = modifier,
            settingsState = settingsState,
            updateSettings = settingsViewModel::updateSettings,
            searchScreenState = searchScreenState,
            performEvent = searchViewModel::performEvent,
            toWordClick = toWordClick
        )


    }
}