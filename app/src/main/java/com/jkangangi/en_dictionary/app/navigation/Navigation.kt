package com.jkangangi.en_dictionary.app.navigation

import android.os.Parcelable
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.History
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import com.bumble.appyx.core.composable.Children
import com.bumble.appyx.core.modality.BuildContext
import com.bumble.appyx.core.node.Node
import com.bumble.appyx.core.node.ParentNode
import com.bumble.appyx.navmodel.backstack.BackStack
import com.bumble.appyx.navmodel.backstack.transitionhandler.rememberBackstackSlider
import com.jkangangi.en_dictionary.data.local.Word
import kotlinx.parcelize.Parcelize

private data class BottomAppItem(
    val icon: ImageVector,
    val title: String,
    val route: String,
)

private val screens = listOf(
    BottomAppItem(
        Icons.Default.Search,
        "Search",
        "" //Todo
    ),
    BottomAppItem(
        Icons.Default.History,
        "History",
        "" //Todo
    ),
    BottomAppItem(
        Icons.Default.Bookmark,
        "Saved",
        "" //Todo
    )
)
//navigation Destinations
class Navigation(
    buildContext: BuildContext,
    startingRoute: Route = Route.Search ,
    private val backStack: BackStack<Route> = BackStack(
        initialElement = startingRoute,
        savedStateMap = buildContext.savedStateMap,
    ),
): ParentNode<Navigation.Route>(
    buildContext = buildContext,
    navModel = backStack,
){
    @Composable
    override fun View(modifier: Modifier) {
        Children(
            navModel = backStack,
            transitionHandler = rememberBackstackSlider(),
        )
    }

    override fun resolve(navTarget: Navigation.Route, buildContext: BuildContext): Node {
        return when(navTarget) {
            Route.Search -> SearchRoute(
                buildContext = buildContext,
                backStack = backStack,
            )

            is Route.Definition -> DefinitionRoute(
                buildContext = buildContext,
                backStack = backStack,
            )
        }
    }

    sealed class Route : Parcelable {

        @Parcelize
        object Search: Route()

        @Parcelize
        data class Definition(val word: Word) : Route()
    }


}