package com.jkangangi.en_dictionary.game.sharedwidgets

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
import com.jkangangi.en_dictionary.R
import com.jkangangi.en_dictionary.app.theme.largePadding
import com.jkangangi.en_dictionary.app.theme.mediumPadding
import com.jkangangi.en_dictionary.app.widgets.EmptyListView
import com.jkangangi.en_dictionary.game.GameConstants
import com.jkangangi.en_dictionary.game.GameTopBar
import com.jkangangi.en_dictionary.game.GameUIState

@Composable
fun GeneralGameView(
    state: GameUIState,
    modifier: Modifier = Modifier,
    gameLayout: @Composable (Modifier) -> Unit,
) {
    Scaffold(
        modifier = modifier,
        topBar = {
            GameTopBar(currentScore = state.score, currentWord = state.wordCount)
        },
        content = { scaffoldPadding ->
            if (state.wordItemsSize < GameConstants.MAX_WORDS - 1) {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally,
                    content = {
                        EmptyListView(stringId = R.string.empty_saves)
                        Text(
                            text = "Current Word Count: ${state.wordItemsSize}",
                            style = MaterialTheme.typography.headlineSmall.copy(
                                color = MaterialTheme.colorScheme.onBackground.copy(
                                    alpha = 0.5f
                                )
                            )
                        )
                    }
                )
            } else {
                Column(
                    modifier = Modifier
                        .padding(scaffoldPadding)
                        .padding(start = largePadding(), end = largePadding()),
                    content = {
                        gameLayout(Modifier.padding(mediumPadding()))
                    }
                )

            }

        }
    )
}