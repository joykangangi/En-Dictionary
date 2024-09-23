package com.jkangangi.en_dictionary.game

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.bumble.appyx.core.modality.BuildContext
import com.bumble.appyx.core.node.Node
import com.bumble.appyx.navmodel.backstack.BackStack
import com.bumble.appyx.navmodel.backstack.operation.push
import com.jkangangi.en_dictionary.app.navigation.Route
import com.jkangangi.en_dictionary.game.easymode.EasyGameView
import com.jkangangi.en_dictionary.game.hard.HardGameView
import com.jkangangi.en_dictionary.game.medium.MediumGameView

class GameRoute(
    buildContext: BuildContext,
    private val backStack: BackStack<Route>,
    private val gameMode: GameMode? = null,
) : Node(buildContext = buildContext) {


    @Composable
    override fun View(
        modifier: Modifier
    ) {
        GameIntroScreen(
            onGameModeClick = { gameMode ->
                backStack.push(Route.Play(gameMode))
            }
        )

        if (gameMode != null) {
            ResolveGameModeView(gameMode = gameMode)
        }
    }

    @Composable
    fun ResolveGameModeView(gameMode: GameMode) {

        when(gameMode) {
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