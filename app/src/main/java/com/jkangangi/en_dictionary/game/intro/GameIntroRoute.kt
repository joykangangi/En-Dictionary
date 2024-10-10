package com.jkangangi.en_dictionary.game.intro

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.jkangangi.en_dictionary.game.util.GameMode
import kotlinx.serialization.Serializable

/**
 * I could have merged the mode and landing page here..,
 * but the class will be multipurpose(spaghetti)
 */
@Serializable
data object GameIntroRoute

fun NavController.navigateToGameIntro(
    navOptions: NavOptions
) = navigate(route = GameIntroRoute, navOptions)

fun NavGraphBuilder.gameIntroScreen(
    onGameModeClicked: (GameMode) -> Unit,
) {
    composable<GameIntroRoute> {

        GameIntroScreen(onGameModeClick = onGameModeClicked)
    }
}