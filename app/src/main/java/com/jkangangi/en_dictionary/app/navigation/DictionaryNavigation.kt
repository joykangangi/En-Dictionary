package com.jkangangi.en_dictionary.app.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.util.trace
import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navOptions
import com.jkangangi.en_dictionary.definitions.navigateToWordDetail
import com.jkangangi.en_dictionary.definitions.wordDetailScreen
import com.jkangangi.en_dictionary.game.gameHomeScreen
import com.jkangangi.en_dictionary.game.gameModeScreen
import com.jkangangi.en_dictionary.game.navigateToGameMode
import com.jkangangi.en_dictionary.game.navigateToPlay
import com.jkangangi.en_dictionary.history.historyScreen
import com.jkangangi.en_dictionary.history.navigateToHistory
import com.jkangangi.en_dictionary.search.MainSearchRoute
import com.jkangangi.en_dictionary.search.mainSearchScreen
import com.jkangangi.en_dictionary.search.navigateToMainSearch

@Composable
fun DictionaryNavigation(
    modifier: Modifier = Modifier
) {
    val navController = rememberNavController()

    Scaffold(
        content = { scaffoldPadding ->

            NavHost(
                modifier = modifier.padding(scaffoldPadding),
                navController = navController,
                startDestination = MainSearchRoute
            ) {
                mainSearchScreen(toWordDetailClick = navController::navigateToWordDetail)
                wordDetailScreen(onBack = navController::popBackStack)
                gameHomeScreen(onGameModeClicked = navController::navigateToGameMode)
                gameModeScreen()
                historyScreen(toWordDfn = navController::navigateToWordDetail)

            }
        },
        bottomBar = { AppBottomNavigator(navHostController = navController) }
    )


}



/**
 * UI logic for navigating to a main destination(bottom bar routes) in the app. Main destinations have
 * only one copy of the destination of the back stack, and save and restore state whenever you
 * navigate to and from it.
 */
fun navigateToMainAppRoutes(
    mainAppRoutes: MainAppRoutes,
    navController: NavController,
) {
    trace("Navigation: ${mainAppRoutes.name}") {
        val mainAppNavOptions = navOptions {
            popUpTo(navController.graph.findStartDestination().id) {
                saveState = true
            }
            launchSingleTop = true

            // Restore state when re-selecting a previously selected item
            restoreState = true
        }

        when (mainAppRoutes) {
            MainAppRoutes.SEARCH -> navController.navigateToMainSearch(mainAppNavOptions)
            MainAppRoutes.PLAY -> navController.navigateToPlay(mainAppNavOptions)
            MainAppRoutes.HISTORY -> navController.navigateToHistory(mainAppNavOptions)
        }

    }
}

