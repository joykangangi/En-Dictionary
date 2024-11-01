package com.jkangangi.en_dictionary.game

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import androidx.navigation.compose.dialog
import androidx.navigation.navigation
import androidx.navigation.toRoute
import com.jkangangi.en_dictionary.game.intro.GameIntroScreen
import com.jkangangi.en_dictionary.game.mode.easy.EasyGameView
import com.jkangangi.en_dictionary.game.mode.hard.HardGameView
import com.jkangangi.en_dictionary.game.mode.medium.MediumGameView
import com.jkangangi.en_dictionary.game.mode.model.GameSummaryStats
import com.jkangangi.en_dictionary.game.mode.sharedwidgets.GameResultsDialog
import com.jkangangi.en_dictionary.game.util.CustomNavType
import kotlin.reflect.typeOf

/**
 * 3 NavControllers
 * 1 Graph
 */

fun NavController.navigateToGameIntro(
    navOptions: NavOptions? = null
) = navigate(route = GameRoute.GameIntroRoute, navOptions = navOptions)

fun NavController.navigateToGameMode(
   mode: GameRoute,
) = navigate(route = mode)

fun NavController.navigateToGameSummary(
    gameSummary: GameSummaryStats,
    navOptions: NavOptions? = null,
) = navigate(route = GameRoute.GameSummaryDialogRoute(gameSummaryStats = gameSummary), navOptions = navOptions)


/////graph
fun NavGraphBuilder.gameGraph(
    navigateToMode: (GameRoute) -> Unit,
    navigateToGameSummary: (GameSummaryStats) -> Unit,
    navigateToIntro: () -> Unit,
) {

    navigation<GameRoute.Root>(startDestination = GameRoute.GameIntroRoute) {

        composable<GameRoute.GameIntroRoute> {

            GameIntroScreen(
                onGameModeClick = {
                    navigateToMode(it.route)
                }
            )
        }

        dialog<GameRoute.GameSummaryDialogRoute>(
            typeMap = mapOf(
                typeOf<GameSummaryStats>() to CustomNavType.GameSummaryType
            )
        ) {
            val args = it.toRoute<GameRoute.GameSummaryDialogRoute>()
            val stats = args.gameSummaryStats

            GameResultsDialog(
                percentageScore = stats.percentageScore,
                totalTimeUsed = stats.totalGameTime,
                totalPoints = stats.totalPoints,
                isExcellent = stats.isExcellent,
                playAgain = navigateToIntro
            )
        }

        composable<GameRoute.EasyGameModeRoute> {
            EasyGameView(
                viewResultsDialog = navigateToGameSummary
            )
        }

        composable<GameRoute.MediumGameModeRoute> {
            MediumGameView(viewResultsDialog = navigateToGameSummary)
        }

        composable<GameRoute.HardGameModeRoute> {
            HardGameView()
        }
    }
}

/**
 * alternative:
 * fun NavGraphBuilder.gameModeScreen() {
 *
 *     composable<GameModeRoute> {
 *         val args = it.toRoute<GameModeRoute>()
 *
 *
 *         when (args.gameMode) {
 *             GameMode.Hard -> {
 *                 HardGameView()
 *             }
 *
 *             GameMode.Medium -> {
 *                 MediumGameView()
 *             }
 *
 *             GameMode.Easy -> {
 *                 EasyGameView()
 *             }
 *         }
 *     }
 * }
 */