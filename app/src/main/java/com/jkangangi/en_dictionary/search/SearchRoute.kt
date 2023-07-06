package com.jkangangi.en_dictionary.search

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.bumble.appyx.core.modality.BuildContext
import com.bumble.appyx.core.node.Node
import com.bumble.appyx.navmodel.backstack.BackStack
import com.bumble.appyx.navmodel.backstack.operation.singleTop
import com.jkangangi.en_dictionary.app.navigation.Route

class SearchRoute(
    buildContext: BuildContext,
    private val backStack: BackStack<Route>, //navController
) : Node(buildContext = buildContext) {

    @Composable
    override fun View(modifier: Modifier) {
        SearchView(modifier = modifier)
    }


    @Composable
    private fun SearchView(
        modifier: Modifier,
        viewModel: SearchViewModel = hiltViewModel(),
    ) {
        val search by viewModel.searchQuery.collectAsState()
        val searchClick: () -> Unit = remember {
            {
                backStack.singleTop(Route.Definition(search))

            }
        }

        SearchScreen(
            isDarkTheme = true,
            toggleTheme = { /*TODO*/ },
            query = search,
            updateQuery = viewModel::onSearch,
            modifier = modifier,
            onSearchClick = searchClick,
        )
    }
}