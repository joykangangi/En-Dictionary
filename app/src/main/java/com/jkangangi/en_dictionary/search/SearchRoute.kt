package com.jkangangi.en_dictionary.search

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.bumble.appyx.core.modality.BuildContext
import com.bumble.appyx.core.node.Node
import com.bumble.appyx.navmodel.backstack.BackStack
import com.bumble.appyx.navmodel.backstack.operation.singleTop
import com.jkangangi.en_dictionary.app.data.model.Dictionary
import com.jkangangi.en_dictionary.app.data.remote.dto.RequestDTO
import com.jkangangi.en_dictionary.app.navigation.Route
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update

class SearchRoute(
    buildContext: BuildContext,
    private val backStack: BackStack<Route>, //navController
) : Node(buildContext = buildContext) {

    val isDarkTheme = MutableStateFlow(true) //TODO SETTINGS VM

    @Composable
    override fun View(modifier: Modifier) {
        SearchScreenView(modifier = modifier)
    }

    @Composable
    fun SearchScreenView(
        modifier: Modifier,
        viewModel: SearchViewModel = hiltViewModel(),
    ) {
        val switch = isDarkTheme.collectAsState().value

        val state by viewModel.searchState.collectAsState()

        val toWordClick  = remember {
            { word: Dictionary ->
                backStack.singleTop(Route.SearchDetail(viewModel.setBook(word)))
               // backStack.singleTop(Route.SearchDetail(word))
            }
        }
        val onSearchClicked = remember {
            {
                viewModel.doWordSearch()
            }
        }

        DisposableEffect(key1 = true, effect = {
            onDispose { viewModel.closeClient() }
        } )


        SearchScreen(
            modifier = modifier.fillMaxWidth() ,
            isDarkTheme = switch,
            toggleTheme = this::updateTheme,
            state = state,
            updateQuery = viewModel::updateQuery,
            onSearchClick = onSearchClicked,
            onWordClick = toWordClick
        )
    }


    private fun updateTheme(darkTheme: Boolean) {
        isDarkTheme.update { !darkTheme }
    }
}
