package com.jkangangi.en_dictionary.app.navigation

import android.os.Parcelable
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.History
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import com.bumble.appyx.core.composable.Children
import com.bumble.appyx.core.modality.BuildContext
import com.bumble.appyx.core.node.Node
import com.bumble.appyx.core.node.ParentNode
import com.bumble.appyx.core.node.node
import com.bumble.appyx.navmodel.backstack.BackStack
import com.bumble.appyx.navmodel.backstack.transitionhandler.rememberBackstackSlider
import com.jkangangi.en_dictionary.definitions.DefinitionView
import com.jkangangi.en_dictionary.game.GameRoute
import com.jkangangi.en_dictionary.history.HistoryRoute
import com.jkangangi.en_dictionary.search.SearchRoute
import kotlinx.parcelize.Parcelize

//navigation Destinations
class Navigation(
    rootBuildContext: BuildContext,
    startingRoute: Route = Route.Search,
    private val backStack: BackStack<Route> = BackStack(
        initialElement = startingRoute,
        savedStateMap = rootBuildContext.savedStateMap,
    ),
) : ParentNode<Route>(
    buildContext = rootBuildContext,
    navModel = backStack,
) {

    @Composable
    override fun View(modifier: Modifier) {

        //This will add the child nodes to the composition
        Scaffold(
            bottomBar = {
                BottomNavigator(backStackNavigator = backStack)
            }
        ) {
            Children(
                navModel = backStack,
                transitionHandler = rememberBackstackSlider(),
                modifier = modifier.padding(it),
            )
        }
    }

    override fun resolve(navTarget: Route, buildContext: BuildContext): Node {

        return when (navTarget) {
            is Route.Search ->
                SearchRoute(
                    buildContext = buildContext,
                    backStack = backStack,
                )

            is Route.SearchDetail -> node(buildContext) {
                DefinitionView(
                    onBack = { backStack.handleUpNavigation() },
                    sentence = navTarget.sentence,
                    modifier = it
                )
            }

            is Route.Play -> GameRoute(
                buildContext = buildContext,
                backStack = backStack,
            )

            is Route.History -> HistoryRoute(
                buildContext = buildContext,
                backStack = backStack
            )
        }
    }
}

sealed class Route(val icon: ImageVector? = null, val title: String? = null) : Parcelable {

    @Parcelize
    data object Search : Route(icon = Icons.Default.Search, title = "Search")

    @Parcelize
    data class SearchDetail(val sentence: String) : Route(title = "SearchDetail")

    @Parcelize
    data object Play : Route(icon = Icons.Default.Bookmark, title = "Play")

    @Parcelize
    data object History : Route(icon = Icons.Default.History, title = "History")

}
