package com.jkangangi.en_dictionary.game.mode.easy

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.lifecycle.viewmodel.compose.viewModel
import com.jkangangi.en_dictionary.R
import com.jkangangi.en_dictionary.app.theme.mediumPadding
import com.jkangangi.en_dictionary.app.util.DictionaryViewModelFactory
import com.jkangangi.en_dictionary.game.mode.GameInputState
import com.jkangangi.en_dictionary.game.mode.GameUIState
import com.jkangangi.en_dictionary.game.mode.GameViewModel
import com.jkangangi.en_dictionary.game.mode.medium.ButtonSection
import com.jkangangi.en_dictionary.game.mode.medium.HintSection
import com.jkangangi.en_dictionary.game.mode.sharedwidgets.GameInputWidget
import com.jkangangi.en_dictionary.game.mode.sharedwidgets.GameTimer
import com.jkangangi.en_dictionary.game.mode.sharedwidgets.GeneralGameView
import com.jkangangi.en_dictionary.game.mode.sharedwidgets.ScrambledWordBox
import com.jkangangi.en_dictionary.game.util.GameConstants.MAX_WORDS
import com.jkangangi.en_dictionary.game.util.GameMode


@Composable
fun EasyGameView(
    modifier: Modifier = Modifier,
    viewModel: GameViewModel = viewModel(factory = DictionaryViewModelFactory)
) {

    val gameState by viewModel.gameInputState.collectAsState()
    val gameUIState by viewModel.gameUIState().collectAsState()
    val currentMode by viewModel.currentMode.collectAsState()

    //only runs for the first word
    LaunchedEffect(
        key1 = gameState.wordItemsSize > MAX_WORDS - 1,
        block = {
            if (gameState.wordCount == 0 && gameState.wordItemsSize > MAX_WORDS - 1) {
                viewModel.setGameMode(GameMode.Easy)
                viewModel.getWordItem()
            }
        }
    )

    EasyGameScreen(
        modifier = modifier,
        state = gameState,
        onGuessChanged = viewModel::updateInput,
        onNextClicked = viewModel::onNextClicked,
        onSkipClicked = viewModel::onSkipClicked,
        onHintClicked = viewModel::onHintClicked,
        gameUIState = gameUIState,
        currentMode = currentMode
    )

}

@Composable
fun EasyGameScreen(
    modifier: Modifier,
    state: GameInputState,
    currentMode: GameMode?,
    gameUIState: GameUIState,
    onGuessChanged: (String) -> Unit,
    onNextClicked: () -> Unit,
    onSkipClicked: () -> Unit,
    onHintClicked: () -> Unit,
) {

    val isNewWord = remember { mutableStateOf(false) }

    LaunchedEffect(key1 = Unit) {
        isNewWord.value = true
    }

    GeneralGameView(
        modifier = modifier,
        gameInputState = state,
        currentMode = currentMode,
        gameUIState = gameUIState,
        gameLayout = {
            EasyGameCard(
                modifier = Modifier.padding(mediumPadding()),
                scrambledWord = state.scrambledWord,
                hint = state.hint,
                onGuessChanged = onGuessChanged,
                onHintClicked = onHintClicked,
                showHint = state.showHint,
                timeLeft = state.timeLeft,
                isNewWord = isNewWord.value
            )

            ButtonSection(
                onSkipClicked = {
                    onSkipClicked()
                    isNewWord.value = true
                },
                onNextClicked = {
                    onNextClicked()
                    isNewWord.value = true
                },
                btnEnabled = state.btnEnabled,
                isFinalWord = state.wordCount == MAX_WORDS
            )

        }
    )
}

@Composable
private fun EasyGameCard(
    modifier: Modifier,
    scrambledWord: String,
    hint: String,
    onGuessChanged: (String) -> Unit,
    onHintClicked: () -> Unit,
    showHint: Boolean,
    timeLeft: Int,
    isNewWord: Boolean,
) {

    ElevatedCard(
        content = {
            Column(
                modifier = modifier,
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
                content = {

                    Text(
                        text = stringResource(id = R.string.easy_game_instruction),
                        style = MaterialTheme.typography.bodyMedium,
                        textAlign = TextAlign.Center
                    )

                    GameTimer(
                        modifier = Modifier.align(Alignment.Start),
                        timeLeft = timeLeft
                    )

                    ScrambledWordBox(
                        scrambledWord = scrambledWord,
                    )

                    Text(
                        text = stringResource(id = R.string.game_txt_label),
                        style = MaterialTheme.typography.bodyMedium
                    )

                    GameInputWidget(
                        onLetterChange = onGuessChanged,
                        wordSize = scrambledWord.length
                    )

                    HintSection(hint = hint, onHintClicked = onHintClicked, showHint = showHint)
                },
            )
        }
    )


}
