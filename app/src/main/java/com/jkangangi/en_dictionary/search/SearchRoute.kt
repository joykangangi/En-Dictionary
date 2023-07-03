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
import com.bumble.appyx.navmodel.backstack.operation.newRoot
import com.bumble.appyx.navmodel.backstack.operation.singleTop
import com.jkangangi.en_dictionary.app.navigation.Route

class SearchRoute(
    buildContext: BuildContext,
    private val backStack: BackStack<Route>, //navController
    //you can add an onclick which can help in moving to next screen + push a screen to backstack
) : Node(buildContext = buildContext) {

    @Composable
    override fun View(modifier: Modifier) {
        Search(modifier = modifier)
    }


    @Composable
    fun Search(
        modifier: Modifier,
        viewModel: SearchViewModel = hiltViewModel(),
    ) {
        val search by viewModel.searchQuery.collectAsState()
        val searchClick: () -> Unit = remember {
           // { backStack.newRoot(Route.Definition(search)) }
            {
                backStack.run {
                    elements.value.first().key.navTarget.let {
                        singleTop(it)
                    }
                }
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