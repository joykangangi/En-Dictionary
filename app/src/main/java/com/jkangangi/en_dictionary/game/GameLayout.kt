package com.jkangangi.en_dictionary.game

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Lightbulb
import androidx.compose.material3.Button
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.jkangangi.en_dictionary.R
import com.jkangangi.en_dictionary.app.theme.En_DictionaryTheme
import com.jkangangi.en_dictionary.app.util.HtmlParser


@Composable
fun GameLayout(
    state: GameUIState,
    guess: String,
    onGuessChanged: (String) -> Unit,
    onNextClicked: () -> Unit,
    onSkipClicked: () -> Unit,
    onHintClicked: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier.padding(12.dp),
        content = {

            GameCard(
                modifier = Modifier.padding(8.dp),
                scrambledWord = state.scrambledWord,
                hint = state.hint,
                guess = guess,
                onGuessChanged = onGuessChanged,
                onHintClicked = onHintClicked,
                showHint = state.showHint,
            )

            ButtonSection(
                onSkipClicked = onSkipClicked,
                onNextClicked = onNextClicked,
                btnEnabled = state.nextEnabled,
                isFinalWord = state.showSubmit
            )
        }
    )
}

@Composable
private fun GameCard(
    modifier: Modifier,
    scrambledWord: String,
    hint: String,
    guess: String,
    onGuessChanged: (String) -> Unit,
    onHintClicked: () -> Unit,
    showHint: Boolean,
) {

    val keyboard = LocalSoftwareKeyboardController.current
    val shutKeyBoard = remember {
        { keyboard?.hide() }
    }


    val brushColors = Brush.linearGradient(
        colors = listOf(
            MaterialTheme.colorScheme.primary,
            MaterialTheme.colorScheme.secondary,
            MaterialTheme.colorScheme.onSurface
        )
    )
    val txtStyle = MaterialTheme.typography.bodyMedium

    ElevatedCard(
        content = {
            Column(
                modifier = modifier,
                verticalArrangement = Arrangement.spacedBy(10.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                content = {
                    Text(
                        text = scrambledWord,
                        style = txtStyle.merge(TextStyle(brush = brushColors)),
                    )
                    Text(
                        text = stringResource(id = R.string.game_instructions),
                        style = MaterialTheme.typography.bodyMedium
                    )

                    OutlinedTextField(
                        value = guess,
                        onValueChange = onGuessChanged,
                        singleLine = true,
                        label = {
                            Text(
                                text = stringResource(id = R.string.game_txt_label),
                                style = MaterialTheme.typography.bodyMedium
                            )
                        },
                        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
                        keyboardActions = KeyboardActions(onDone = { shutKeyBoard() }),
                    )
                    HintSection(hint = hint, onHintClicked = onHintClicked, showHint = showHint)
                },
            )
        }
    )
}

@Composable
private fun HintSection(
    hint: String,
    onHintClicked: () -> Unit,
    showHint: Boolean,
    modifier: Modifier = Modifier,
) {

    Column(
        verticalArrangement = Arrangement.SpaceEvenly,
        content = {
            Row(
                modifier = modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                content = {
                    Icon(
                        imageVector = Icons.Default.Lightbulb,
                        contentDescription = null,
                        modifier = Modifier.size(35.dp),
                        tint = MaterialTheme.colorScheme.primary,
                    )

                    TextButton(
                        onClick = onHintClicked,
                        content = {
                            Text(text = "Show Hint", style = MaterialTheme.typography.bodyMedium)
                        }
                    )
                },
            )

            AnimatedVisibility(visible = showHint) {
                Box(
                    modifier = modifier.padding(6.dp),
                    contentAlignment = Alignment.Center,
                    content = {
                        Column(
                            content = {
                                Text(
                                    text = "Meaning: ${HtmlParser.htmlToString(hint)}",
                                    style = MaterialTheme.typography.bodySmall
                                )
                            }
                        )
                    }
                )
            }
        }
    )
}

@Composable
private fun ButtonSection(
    onSkipClicked: () -> Unit,
    onNextClicked: () -> Unit,
    btnEnabled: Boolean,
    isFinalWord: Boolean,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(8.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        content = {
            OutlinedButton(
                onClick = onSkipClicked,
                modifier = modifier.weight(.1f),
                content = {
                    Text(text = "Skip")
                }
            )
            Spacer(modifier = modifier.width(4.dp))

            Button(
                onClick = onNextClicked,
                modifier = modifier.weight(.1f),
                enabled = btnEnabled,
                content = {
                    Text(text = if (isFinalWord) "Finish" else "Next")
                }
            )
        }
    )
}


@Preview
@Composable
fun PrevGameCard() {
    En_DictionaryTheme {
        GameCard(
            modifier = Modifier,
            scrambledWord = "RungeKutta",
            hint = "Fourth Order Formula",
            guess = "",
            onGuessChanged = { },
            onHintClicked = { },
            showHint = true
        )
    }
}