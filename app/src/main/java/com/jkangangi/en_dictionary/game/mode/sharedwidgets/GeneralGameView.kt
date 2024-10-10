package com.jkangangi.en_dictionary.game.mode.sharedwidgets

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
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
import com.jkangangi.en_dictionary.game.mode.GameInputState
import com.jkangangi.en_dictionary.game.mode.GameUIState
import com.jkangangi.en_dictionary.game.util.GameMode

@Composable
fun GeneralGameView(
    gameInputState: GameInputState,
    gameUIState: GameUIState,
    currentMode: GameMode?,
    modifier: Modifier = Modifier,
    gameLayout: @Composable (Modifier) -> Unit,
) {
    Scaffold(
        modifier = modifier,
        topBar = {
            GameTopBar(currentScore = gameInputState.score, currentWord = gameInputState.wordCount)
        },
        content = { scaffoldPadding ->

            when(gameUIState) {
                GameUIState.SmallWordCount -> {
                    Column(
                        modifier = Modifier.fillMaxSize(),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally,
                        content = {
                            EmptyListView(stringId = R.string.empty_saves)
                            Text(
                                text = "Current Word Count: ${gameInputState.wordItemsSize}",
                                style = MaterialTheme.typography.headlineSmall.copy(
                                    color = MaterialTheme.colorScheme.onBackground.copy(
                                        alpha = 0.5f
                                    )
                                )
                            )
                        }
                    )
                }

                GameUIState.WordLoading -> {
                    Column(
                        modifier = Modifier.fillMaxSize(),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally,
                        content = {
                            CircularProgressIndicator()
                        }
                    )
                }

                GameUIState.WordsNotFound -> {
                    if (currentMode == GameMode.Easy) {
                        EmptyListView(
                            stringId = R.string.easy_words_not_found
                        )
                    }

                    if (currentMode == GameMode.Medium) {
                        EmptyListView(
                            stringId = R.string.med_words_not_found
                        )
                    }
                }

                GameUIState.WordsFound -> {
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
        }
    )
}