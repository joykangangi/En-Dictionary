package com.jkangangi.en_dictionary.game.mode.medium

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.jkangangi.en_dictionary.app.theme.En_DictionaryTheme
import com.jkangangi.en_dictionary.game.mode.GameInputState
import com.jkangangi.en_dictionary.game.mode.GameUIState
import com.jkangangi.en_dictionary.game.mode.sharedwidgets.GeneralGameView
import com.jkangangi.en_dictionary.game.util.GameMode


@Composable
fun MediumGameScreen(
    modifier: Modifier,
    state: GameInputState,
    gameUIState: GameUIState,
    currentMode: GameMode?,
    guess: String,
    onGuessChanged: (String) -> Unit,
    onNextClicked: () -> Unit,
    onSkipClicked: () -> Unit,
    onHintClicked: () -> Unit,
) {

    GeneralGameView(
        modifier = modifier,
        gameInputState = state,
        currentMode = currentMode,
        gameUIState = gameUIState,
        gameLayout = {
            MediumGameLayout(
                guess = guess,
                onGuessChanged = onGuessChanged,
                onNextClicked = onNextClicked,
                onSkipClicked = onSkipClicked,
                state = state,
                onHintClicked = onHintClicked,
            )
        }
    )
}


@Preview(apiLevel = 33)
@Composable
fun PreviewSavedWords() {
    En_DictionaryTheme {
        MediumGameScreen(
            modifier = Modifier,
            state = GameInputState(),
            guess = "",
            onGuessChanged = { },
            onNextClicked = { },
            onSkipClicked = { },
            onHintClicked = { },
            gameUIState = GameUIState.WordLoading,
            currentMode = GameMode.Easy
        )
    }
}