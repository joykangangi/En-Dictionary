package com.jkangangi.en_dictionary.game

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.bumble.appyx.core.modality.BuildContext
import com.bumble.appyx.core.node.Node
import com.bumble.appyx.navmodel.backstack.BackStack
import com.jkangangi.en_dictionary.app.navigation.Route
import com.jkangangi.en_dictionary.app.util.DictionaryViewModelFactory

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
        viewModel: GameViewModel = viewModel(factory = DictionaryViewModelFactory)
    ) {

        val gameState by viewModel.gameUIState.collectAsState()


        if (gameState.isGameOver) {
            viewModel.resetGame()
        }
        val gameSize = gameState.wordItemsSize

        if (gameState.wordCount == 0) {
            LaunchedEffect(
                key1 = gameSize,
                block = {
                    if (gameSize > 5) {
                        viewModel.getWordItem()
                    }
                })
        }


        GameScreen(
            modifier = modifier,
            state = gameState,
            guess = viewModel.guessedWord.value,
            onGuessChanged = viewModel::updateInput,
            onNextClicked = viewModel::onNextClicked,
            onSkipClicked = viewModel::onSkipClicked,
            onHintClicked = viewModel::onHintClicked,
        )


    }

}