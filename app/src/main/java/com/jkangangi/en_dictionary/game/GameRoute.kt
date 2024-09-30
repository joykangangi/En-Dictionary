package com.jkangangi.en_dictionary.game

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import kotlinx.serialization.Serializable

/**
 * I could have merged the mode and landing page here..,
 * but the class will be multipurpose(spaghetti)
 */
@Serializable
data object PlayHomeRoute

fun NavController.navigateToPlay(
    navOptions: NavOptions
) = navigate(route = PlayHomeRoute, navOptions)

fun NavGraphBuilder.gameHomeScreen(
    onGameModeClicked: (GameMode) -> Unit,
) {
    composable<PlayHomeRoute> {

        GameHomeScreen(onGameModeClicked = onGameModeClicked)
    }
}

@Composable
internal fun GameHomeScreen(
    onGameModeClicked: (GameMode) -> Unit,
    modifier: Modifier = Modifier
) {
    GameIntroScreen(
        modifier = modifier,
        onGameModeClick = onGameModeClicked
    )
}