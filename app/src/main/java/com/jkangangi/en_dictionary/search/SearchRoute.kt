package com.jkangangi.en_dictionary.search

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.bumble.appyx.core.modality.BuildContext
import com.bumble.appyx.core.node.Node
import com.bumble.appyx.navmodel.backstack.BackStack
import com.bumble.appyx.navmodel.backstack.operation.singleTop
import com.jkangangi.en_dictionary.app.data.local.room.DictionaryEntity
import com.jkangangi.en_dictionary.app.navigation.Route
import com.jkangangi.en_dictionary.app.settings.Constants.DARK_THEME
import com.jkangangi.en_dictionary.app.settings.Constants.LIGHT_THEME
import com.jkangangi.en_dictionary.app.settings.SettingsViewModel

class SearchRoute(
    buildContext: BuildContext,
    private val backStack: BackStack<Route>, //navController
) : Node(buildContext = buildContext) {

    @Composable
    override fun View(modifier: Modifier) {

        SearchScreenView(modifier = modifier)
    }

    @OptIn(ExperimentalAnimationApi::class)
    @Composable
    fun SearchScreenView(
        modifier: Modifier,
        searchViewModel: SearchViewModel = hiltViewModel(),
        settingsViewModel: SettingsViewModel = hiltViewModel(),
    ) {

        val state = searchViewModel.searchState.collectAsState()
        val hasDark = settingsViewModel.isDarkTheme.collectAsState()

        val toWordClick = remember {
            { dfn: DictionaryEntity ->
                backStack.singleTop(Route.SearchDetail(sentence = dfn.sentence))
            }
        }

        val onSearchClicked =
            {
                searchViewModel.doWordSearch()
            }
        val onUpdateTheme =
            { isDark: Boolean ->
                if (isDark)
                settingsViewModel.updateTheme(DARK_THEME)
                else{
                    settingsViewModel.updateTheme(LIGHT_THEME)
                }
            }

        DisposableEffect(key1 = Unit, effect = {
            onDispose { searchViewModel.closeClient() }
        })

            SearchScreen(
                modifier = modifier.fillMaxWidth(),
                isDarkTheme = hasDark.value == DARK_THEME,
                toggleTheme = { onUpdateTheme(hasDark.value == DARK_THEME) },
                state = state.value,
                updateQuery = searchViewModel::updateQuery,
                onSearchClick = onSearchClicked,
                onWordClick = { state.value.wordItem?.let { toWordClick(it) } }
            )
    }
}
