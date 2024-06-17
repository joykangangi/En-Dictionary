package com.jkangangi.en_dictionary.game

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.bumble.appyx.core.modality.BuildContext
import com.bumble.appyx.core.node.Node
import com.jkangangi.en_dictionary.app.util.DictionaryViewModelFactory

class GameRoute(
    buildContext: BuildContext,
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


        LaunchedEffect(
            key1 = gameState.wordCount,
            key2 = gameState.timeLeft,
            block = {
                if (gameState.wordCount == 0 && gameState.wordItemsSize > GameConstants.MAX_WORDS -1) {
                    viewModel.getWordItem()
                }
                viewModel.startTimer()
            }
        )



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