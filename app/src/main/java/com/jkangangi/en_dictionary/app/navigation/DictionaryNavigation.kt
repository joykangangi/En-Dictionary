package com.jkangangi.en_dictionary.app.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.jkangangi.en_dictionary.app.navigation.bottomnav.AppBottomNavigator
import com.jkangangi.en_dictionary.definitions.navigateToWordDetail
import com.jkangangi.en_dictionary.definitions.wordDetailScreen
import com.jkangangi.en_dictionary.game.intro.gameIntroScreen
import com.jkangangi.en_dictionary.game.mode.gameModeScreen
import com.jkangangi.en_dictionary.game.mode.navigateToGameMode
import com.jkangangi.en_dictionary.history.historyScreen
import com.jkangangi.en_dictionary.search.MainSearchRoute
import com.jkangangi.en_dictionary.search.mainSearchScreen

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
                gameIntroScreen(onGameModeClicked = navController::navigateToGameMode)
                gameModeScreen()
                historyScreen(toWordDfn = navController::navigateToWordDetail)
            }
        },
        bottomBar = { AppBottomNavigator(navHostController = navController) }
    )


}
