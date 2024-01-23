package com.jkangangi.en_dictionary.game

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.jkangangi.en_dictionary.R
import com.jkangangi.en_dictionary.app.theme.En_DictionaryTheme
import com.jkangangi.en_dictionary.app.widgets.EmptyListView

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GameScreen(
    modifier: Modifier,
    state: GameUIState,
    guess: String,
    onGuessChanged: (String) -> Unit,
    onNextClicked: () -> Unit,
    onSkipClicked: () -> Unit,
    onHintClicked: () -> Unit,
) {

    Scaffold(
        modifier = modifier
            .shadow(elevation = 3.dp),
        topBar = {
            GameTopBar(currentScore = state.score, currentWord = state.wordCount)
        },
        content = { contentPadding ->
            if (!state.isGameOn) {
                EmptyListView(stringId = R.string.empty_saves)

            } else {
                GameLayout(
                    modifier = modifier
                        .padding(contentPadding),
                    // .verticalScroll(rememberScrollState()),
                    guess = guess,
                    onGuessChanged = onGuessChanged,
                    onNextClicked = onNextClicked,
                    onSkipClicked = onSkipClicked,
                    state = state,
                    onHintClicked = onHintClicked
                )
            }
        })
}


@Preview
@Composable
fun PreviewSavedWords() {
    En_DictionaryTheme {
        GameScreen(
            modifier = Modifier,
            state = GameUIState(scrambledWord = "asftkreab", hint = "meal eaten in the early morning."),
            guess = "" ,
            onGuessChanged = { } ,
            onNextClicked = {  },
            onSkipClicked = { },
            onHintClicked = { }
        )
    }
}