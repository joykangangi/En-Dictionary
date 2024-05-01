package com.jkangangi.en_dictionary.app.navigation

import android.os.Parcelable
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Games
import androidx.compose.material.icons.filled.History
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.outlined.Games
import androidx.compose.material.icons.outlined.History
import androidx.compose.material.icons.outlined.Search
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
import com.bumble.appyx.navmodel.backstack.operation.pop
import com.bumble.appyx.navmodel.backstack.transitionhandler.rememberBackstackFader
import com.jkangangi.en_dictionary.R
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
                transitionHandler = rememberBackstackFader(),
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
                    onBack = { backStack.pop() },
                    sentence = navTarget.sentence,
                    modifier = it,
                )
            }

            is Route.Play -> GameRoute(
                buildContext = buildContext,
            )

            is Route.History -> HistoryRoute(
                buildContext = buildContext,
                backStack = backStack
            )
        }
    }
}

sealed class Route(
    val selectedIcon: ImageVector? = null,
    val unselectedIcon: ImageVector? = null,
    val titleId: Int? = null,
    ) : Parcelable {

    @Parcelize
    data object Search : Route(
        selectedIcon = Icons.Default.Search,
        unselectedIcon = Icons.Outlined.Search,
        titleId = R.string.search_btn
    )

    @Parcelize
    data class SearchDetail(val sentence: String) : Route(titleId = R.string.search_detail)

    @Parcelize
    data object Play : Route(
        selectedIcon = Icons.Default.Games,
        unselectedIcon = Icons.Outlined.Games,
        titleId = R.string.play
    )

    @Parcelize
    data object History : Route(
        selectedIcon = Icons.Default.History,
        unselectedIcon = Icons.Outlined.History,
        titleId = R.string.history
    )

}
