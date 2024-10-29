package com.jkangangi.en_dictionary.game

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import androidx.navigation.compose.dialog
import androidx.navigation.navigation
import com.jkangangi.en_dictionary.R
import com.jkangangi.en_dictionary.game.intro.GameIntroScreen
import com.jkangangi.en_dictionary.game.mode.easy.EasyGameView
import com.jkangangi.en_dictionary.game.mode.hard.HardGameView
import com.jkangangi.en_dictionary.game.mode.medium.MediumGameView
import com.jkangangi.en_dictionary.game.mode.sharedwidgets.AnimWithAnswer
import com.jkangangi.en_dictionary.game.mode.sharedwidgets.AnimWithoutText

/**
 * 2 NavControllers
 * 1 Graph
 */

fun NavController.navigateToGameIntro(
    navOptions: NavOptions? = null
) = navigate(route = GameRoute.GameIntro, navOptions = navOptions)

fun NavController.navigateToGameMode(
   mode: GameRoute,
) = navigate(route = mode)

fun NavGraphBuilder.gameGraph(
    navigateToMode: (GameRoute) -> Unit,
) {

    navigation<GameRoute.Root>(startDestination = GameRoute.GameIntro) {

        composable<GameRoute.GameIntro> {

            GameIntroScreen(
                onGameModeClick = {
                    navigateToMode(it.route)
                }
            )
        }

        dialog<GameRoute.WrongAnsDialog> {

        }

        dialog<GameRoute.CorrectAnsDialog> {

            AnimWithoutText(
                animResId = R.raw.correct_ans_animation,
                goToNextWord = { /*TODO*/ }
            )
        }

        composable<GameRoute.EasyGameMode> {
            EasyGameView()
        }

        composable<GameRoute.MediumGameMode> {
            MediumGameView()
        }

        composable<GameRoute.HardGameMode> {
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