package com.jkangangi.en_dictionary.app.navigation

import android.os.Parcelable
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.bumble.appyx.core.composable.Children
import com.bumble.appyx.core.modality.BuildContext
import com.bumble.appyx.core.node.Node
import com.bumble.appyx.core.node.ParentNode
import com.bumble.appyx.navmodel.backstack.BackStack
import com.bumble.appyx.navmodel.backstack.transitionhandler.rememberBackstackSlider
import com.jkangangi.en_dictionary.data.local.Word
import com.jkangangi.en_dictionary.history.HistoryRoute
import com.jkangangi.en_dictionary.saved.SavedRoute
import com.jkangangi.en_dictionary.search.SearchRoute
import com.jkangangi.en_dictionary.word.DefinitionRoute
import kotlinx.parcelize.Parcelize

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

    var currentRoute by mutableStateOf(startingRoute)
        private set

    @Composable
    override fun View(modifier: Modifier) {
        //This will add the child nodes to the composition
        Children(
            navModel = backStack,
            transitionHandler = rememberBackstackSlider(),
        )
    }

    override fun resolve(navTarget: Route, buildContext: BuildContext): Node {
        currentRoute = navTarget

        return when(navTarget) {
            Route.Search -> SearchRoute(
                buildContext = buildContext,
                backStack = backStack,
            )
            is Route.Definition -> DefinitionRoute(
                buildContext = buildContext,
                backStack = backStack,
            )

           is Route.Saved -> SavedRoute(
                buildContext = buildContext,
                backStack = backStack,
            )

           is Route.History -> HistoryRoute(
               buildContext = buildContext,
           )
        }
    }

    sealed class Route : Parcelable {

        @Parcelize
        object Search: Route()

        @Parcelize
        data class Definition(val word: Word) : Route()

        @Parcelize
        object Saved: Route()

        @Parcelize
        object History: Route()

    }
}