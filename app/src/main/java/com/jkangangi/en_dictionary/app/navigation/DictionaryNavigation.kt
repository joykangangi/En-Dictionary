package com.jkangangi.en_dictionary.app.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.jkangangi.en_dictionary.app.navigation.bottomnav.AppBottomNavigator
import com.jkangangi.en_dictionary.definitions.navigateToWordDetail
import com.jkangangi.en_dictionary.definitions.wordDetailGraph
import com.jkangangi.en_dictionary.game.GameRoute
import com.jkangangi.en_dictionary.game.gameGraph
import com.jkangangi.en_dictionary.game.navigateToGameMode
import com.jkangangi.en_dictionary.game.navigateToGameSummary
import com.jkangangi.en_dictionary.history.historyGraph
import com.jkangangi.en_dictionary.search.MainSearchRoute
import com.jkangangi.en_dictionary.search.mainSearchGraph

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
                mainSearchGraph(toWordDetailClick = navController::navigateToWordDetail)
                wordDetailGraph(onBack = navController::popBackStack)
                gameGraph(
                    navigateToMode = navController::navigateToGameMode,
                    navigateToIntro = {
                        navController.popBackStack(GameRoute.GameIntroRoute,false)
                    },
                    navigateToGameSummary = navController::navigateToGameSummary
                )
                historyGraph(toWordDfn = navController::navigateToWordDetail)
            }
        },
        bottomBar = { AppBottomNavigator(navHostController = navController) }
    )


}
