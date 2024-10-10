package com.jkangangi.en_dictionary.game.mode.medium

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.jkangangi.en_dictionary.app.util.DictionaryViewModelFactory
import com.jkangangi.en_dictionary.game.mode.GameViewModel
import com.jkangangi.en_dictionary.game.util.GameConstants
import com.jkangangi.en_dictionary.game.util.GameMode


@Composable
fun MediumGameView(
    modifier: Modifier = Modifier,
    viewModel: GameViewModel = viewModel(factory = DictionaryViewModelFactory)
) {

    val gameState by viewModel.gameInputState.collectAsState()
    val gameUIState by viewModel.gameUIState().collectAsState()
    val currentMode by viewModel.currentMode.collectAsState()



    LaunchedEffect(
        key1 = gameState.wordItemsSize > GameConstants.MAX_WORDS - 1,
        block = {
            if (gameState.wordCount == 0 && gameState.wordItemsSize > GameConstants.MAX_WORDS - 1) {
                viewModel.setGameMode(GameMode.Medium)
                viewModel.getWordItem()
            }
        }
    )

    MediumGameScreen(
        modifier = modifier,
        state = gameState,
        guess = viewModel.guessedWord.value,
        onGuessChanged = viewModel::updateInput,
        onNextClicked = viewModel::onNextClicked,
        onSkipClicked = viewModel::onSkipClicked,
        onHintClicked = viewModel::onHintClicked,
        gameUIState = gameUIState,
        currentMode = currentMode
    )
}