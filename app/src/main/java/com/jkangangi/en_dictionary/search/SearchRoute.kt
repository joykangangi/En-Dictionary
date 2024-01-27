package com.jkangangi.en_dictionary.search

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.bumble.appyx.core.modality.BuildContext
import com.bumble.appyx.core.node.Node
import com.bumble.appyx.navmodel.backstack.BackStack
import com.bumble.appyx.navmodel.backstack.operation.push
import com.jkangangi.en_dictionary.app.data.local.room.DictionaryEntity
import com.jkangangi.en_dictionary.app.navigation.Route
import com.jkangangi.en_dictionary.isDarkTheme
import com.jkangangi.en_dictionary.settings.AppTheme.DARK_THEME
import com.jkangangi.en_dictionary.settings.AppTheme.LIGHT_THEME
import com.jkangangi.en_dictionary.settings.SettingsViewModel
import com.jkangangi.en_dictionary.settings.fonts.AppFont.SANS_SERIF

class SearchRoute(
    buildContext: BuildContext,
    private val backStack: BackStack<Route>, //navController
) : Node(buildContext = buildContext) {

    @Composable
    override fun View(modifier: Modifier) {

        SearchScreenView(modifier = modifier)
    }

    @Composable
    fun SearchScreenView(
        modifier: Modifier,
        searchViewModel: SearchViewModel = hiltViewModel(),
        settingsViewModel: SettingsViewModel = hiltViewModel(),
    ) {

        val state = searchViewModel.searchState.collectAsState()

        val toWordClick = remember {
            { dfn: DictionaryEntity ->
                backStack.push(Route.SearchDetail(sentence = dfn.sentence))
            }
        }


        val onSearchClicked =
            {
                searchViewModel.doWordSearch()
            }
        val onUpdateTheme = remember {
            { isDark: Boolean ->
                if (isDark)
                    settingsViewModel.updateTheme(DARK_THEME)
                else {
                    settingsViewModel.updateTheme(LIGHT_THEME)
                }
            }
        }

        val onUpdateFont = remember {
            { font: String->
                settingsViewModel.updateFont(font)
            }
        }

        DisposableEffect(key1 = Unit, effect = {
            onDispose { searchViewModel.closeClient() }
        })

        val font by settingsViewModel.currentFont.collectAsState(initial = SANS_SERIF)

        SearchScreen(
            modifier = modifier.fillMaxWidth(),
            state = state.value,
            updateQuery = searchViewModel::updateQuery,
            onSearchClick = onSearchClicked,
            toWordDefinition = toWordClick,
            isDarkTheme = isDarkTheme(),
            updateTheme = onUpdateTheme,
            currentFont = font.toString(),
            updateFont = onUpdateFont

        )
    }
}
