package com.jkangangi.en_dictionary.game

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.jkangangi.en_dictionary.game.easymode.EasyGameView
import com.jkangangi.en_dictionary.game.hard.HardGameView
import com.jkangangi.en_dictionary.game.medium.MediumGameView
import kotlinx.serialization.Serializable

@Serializable
data class GameModeRoute(val gameMode: GameMode)

fun NavController.navigateToGameMode(
    gameMode: GameMode,
    navOptions: NavOptions? = null
) = navigate(route = GameModeRoute(gameMode),navOptions)

/**
 * Alternative 2 of displaying the mode
 * having each separate mode as it own:
 *   -KClass,
 *   -NavController
 *   -NavGraphBuilder
 * then passing navigating functions as params in the [gameModeScreen]
 *    GameMode.Easy -> { navigateToEasy() }
 *
 */
fun NavGraphBuilder.gameModeScreen() {

    composable<GameModeRoute> {
        val args = it.toRoute<GameModeRoute>()

        when (args.gameMode) {
            GameMode.Hard -> {
                HardGameView()
            }

            GameMode.Medium -> {
                MediumGameView()
            }

            GameMode.Easy -> {
                EasyGameView()
            }
        }
    }
}