package com.jkangangi.en_dictionary.search

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
import com.bumble.appyx.navmodel.backstack.operation.singleTop
import com.jkangangi.en_dictionary.app.data.model.Dictionary
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
        val searchTextT by viewModel.targetQuery.collectAsState()
        //val searchTextA by viewModel.queryAfterTarget.collectAsState()
      //  val searchTextB by viewModel.queryBeforeTarget.collectAsState()

        val state by viewModel.searchState.collectAsState()

        val toWordClick  = remember {
            { word: Dictionary ->
                backStack.singleTop(Route.SearchDetail(viewModel.setBook(word)))
            }
        }

        DisposableEffect(key1 = true, effect = {
            onDispose { viewModel.closeClient() }
        } )

        SearchScreen(
            modifier = modifier,
            isDarkTheme = switch,
            toggleTheme = this::updateTheme,
            queryT = searchTextT,
            updateQueryT = viewModel::onSearchTarget,
//            queryA = searchTextA,
//            updateQueryA = viewModel::onSearchAfTarget,
//            queryB = searchTextB,
//            updateQueryB = viewModel::onSearchBfTarget,
            state = state,
            onWordClick = toWordClick,
            onClearInputT = viewModel::clearInputT,
//            onClearInputA = viewModel::clearInputA,
//            onClearInputB = viewModel::clearInputB,
            searchWord = viewModel::doWordSearch

        )
    }


    private fun updateTheme(darkTheme: Boolean) {
        isDarkTheme.update { !darkTheme }
    }
}
