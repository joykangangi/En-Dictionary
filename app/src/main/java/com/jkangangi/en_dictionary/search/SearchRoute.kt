package com.jkangangi.en_dictionary.search

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.bumble.appyx.core.modality.BuildContext
import com.bumble.appyx.core.node.Node
import com.bumble.appyx.navmodel.backstack.BackStack
import com.bumble.appyx.navmodel.backstack.operation.push
import com.jkangangi.en_dictionary.app.navigation.Route
import com.jkangangi.en_dictionary.app.util.DictionaryViewModelFactory
import com.jkangangi.en_dictionary.settings.AppTheme.DARK_THEME
import com.jkangangi.en_dictionary.settings.AppTheme.LIGHT_THEME
import com.jkangangi.en_dictionary.settings.SettingsViewModel
import com.jkangangi.en_dictionary.settings.fonts.AppFont
import kotlinx.coroutines.launch

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
        searchViewModel: SearchViewModel = viewModel(factory = DictionaryViewModelFactory),
        settingsViewModel: SettingsViewModel = viewModel(factory = SettingsViewModel.Factory),
    ) {

        val state = searchViewModel.searchState.collectAsState()



        LaunchedEffect(key1 = state.value.wordItem) {
            val wordItem = state.value.wordItem
            if (wordItem != null) {
                searchViewModel.clearState()
                backStack.push(Route.SearchDetail(sentence = wordItem.sentence))
            }
        }

        val scope = rememberCoroutineScope()

        val onUpdateTheme: (Boolean) -> Unit = remember {
            { isDark ->
                scope.launch {
                    if (isDark)
                        settingsViewModel.updateTheme(DARK_THEME)
                    else {
                        settingsViewModel.updateTheme(LIGHT_THEME)
                    }
                }
            }
        }

        val onUpdateFont: (AppFont) -> Unit = remember {
            { font ->
                scope.launch {
                    settingsViewModel.updateFont(font)
                }
            }
        }

        val isDark by settingsViewModel.currentTheme.collectAsState(initial = LIGHT_THEME)
        val font by settingsViewModel.currentFont.collectAsState(initial = AppFont.SansSerif)


        SearchScreen(
            modifier = modifier,
            state = state.value,
            updateQuery = searchViewModel::updateQuery,
            onSearchClick = searchViewModel::doWordSearch,
            isDarkTheme = isDark == DARK_THEME,
            updateTheme = onUpdateTheme,
            currentFont = font,
            updateFont = onUpdateFont
        )

    }
}