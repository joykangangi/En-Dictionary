package com.jkangangi.en_dictionary.game.medium

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.jkangangi.en_dictionary.app.util.DictionaryViewModelFactory
import com.jkangangi.en_dictionary.game.GameConstants
import com.jkangangi.en_dictionary.game.GameScreen
import com.jkangangi.en_dictionary.game.GameViewModel


@Composable
fun MediumGameView(
    modifier: Modifier = Modifier,
    viewModel: GameViewModel = viewModel(factory = DictionaryViewModelFactory)
) {

    val gameState by viewModel.gameUIState.collectAsState()


    LaunchedEffect(
        key1 = gameState.wordItemsSize > GameConstants.MAX_WORDS - 1,
        block = {
            if (gameState.wordCount == 0 && gameState.wordItemsSize > GameConstants.MAX_WORDS - 1) {
                viewModel.getWordItem()
            }
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