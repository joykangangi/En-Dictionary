package com.jkangangi.en_dictionary.game

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.jkangangi.en_dictionary.app.theme.En_DictionaryTheme
import com.jkangangi.en_dictionary.game.sharedwidgets.GeneralGameView


@Composable
fun MediumGameScreen(
    modifier: Modifier,
    state: GameUIState,
    guess: String,
    onGuessChanged: (String) -> Unit,
    onNextClicked: () -> Unit,
    onSkipClicked: () -> Unit,
    onHintClicked: () -> Unit,
) {

    GeneralGameView(
        modifier = modifier,
        state = state,
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
            state = GameUIState(),
            guess = "",
            onGuessChanged = { },
            onNextClicked = { },
            onSkipClicked = { },
            onHintClicked = { },
        )
    }
}