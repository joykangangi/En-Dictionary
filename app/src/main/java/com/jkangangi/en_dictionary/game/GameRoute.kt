package com.jkangangi.en_dictionary.game

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.bumble.appyx.core.modality.BuildContext
import com.bumble.appyx.core.node.Node
import com.bumble.appyx.navmodel.backstack.BackStack
import com.jkangangi.en_dictionary.app.navigation.Route

class GameRoute(
    buildContext: BuildContext,
    private val backStack: BackStack<Route>
) : Node(buildContext = buildContext) {

    @Composable
    override fun View(
        modifier: Modifier
    ) {
        GameScreenView(modifier = modifier)

    }

    @Composable
    fun GameScreenView(
        modifier: Modifier,
        viewModel: GameViewModel = hiltViewModel()
    ) {

        val gameState by viewModel.gameUIState.collectAsState()

        Log.i("GameRoute", "IsGameOn = ${!gameState.wordItems.isNullOrEmpty()}")


        if (gameState.isGameOver) {
            viewModel.resetGame()
        }
        GameScreen(
            modifier = modifier,
            state = gameState,
            guess = viewModel.guessedWord.collectAsState().value,
            onGuessChanged = viewModel::updateInput,
            onNextClicked = viewModel::onNextClicked,
            onSkipClicked = viewModel::onSkipClicked,
            onHintClicked = viewModel::onHintClicked
        )


    }

}