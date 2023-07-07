package com.jkangangi.en_dictionary.search

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.bumble.appyx.core.modality.BuildContext
import com.bumble.appyx.core.node.Node
import com.bumble.appyx.navmodel.backstack.BackStack
import com.bumble.appyx.navmodel.backstack.operation.singleTop
import com.jkangangi.en_dictionary.app.navigation.Route
import com.jkangangi.en_dictionary.word.WordViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class SearchRoute(
    buildContext: BuildContext,
    private val backStack: BackStack<Route>, //navController
) : Node(buildContext = buildContext) {

    val search = MutableStateFlow("")
    val isDarkTheme = MutableStateFlow(true)

    @Composable
    override fun View(modifier: Modifier) {

        val searchText = search.collectAsState().value
        val switch = isDarkTheme.collectAsState().value

        val scope = rememberCoroutineScope()
        val mviewmodel: WordViewModel = hiltViewModel()
        val searchClick: () -> Unit = remember {
            {
                scope.launch {
                    mviewmodel.getWordDetail(searchText)
                    backStack.singleTop(Route.Definition(searchText))
                }
            }
        }

        SearchScreen(
            isDarkTheme = switch,
            toggleTheme = this::updateTheme,
            query = searchText,
            updateQuery = this::updateSearch,
            modifier = modifier,
            onSearchClick = searchClick,
        )
    }

    private fun updateSearch(query: String) {
        search.update { query }
    }
    private fun updateTheme(darkTheme: Boolean) {
        isDarkTheme.update { !darkTheme }
    }
}
