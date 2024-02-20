package com.jkangangi.en_dictionary.game

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.jkangangi.en_dictionary.R
import com.jkangangi.en_dictionary.app.theme.En_DictionaryTheme
import com.jkangangi.en_dictionary.app.widgets.EmptyListView

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
            if (state.wordItems.isNullOrEmpty() || state.wordItems.size < 5) {
                Column(
                    modifier = modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally,
                    content = {
                        EmptyListView(stringId = R.string.empty_saves)
                        Text(
                            text = "Current Word Count: ${state.wordItems?.size}",
                            style = MaterialTheme.typography.headlineSmall.copy(
                                color = MaterialTheme.colorScheme.onBackground.copy(
                                    alpha = 0.5f
                                )
                            )
                        )
                    }
                )
            } else {
                GameLayout(
                    modifier = modifier.padding(contentPadding),
                    guess = guess,
                    onGuessChanged = onGuessChanged,
                    onNextClicked = onNextClicked,
                    onSkipClicked = onSkipClicked,
                    state = state,
                    onHintClicked = onHintClicked
                )
            }
        }
    )

}


@Preview(apiLevel = 33)
@Composable
fun PreviewSavedWords() {
    En_DictionaryTheme {
        GameScreen(
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