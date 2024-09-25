package com.jkangangi.en_dictionary.game

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.jkangangi.en_dictionary.R
import com.jkangangi.en_dictionary.app.theme.En_DictionaryTheme
import com.jkangangi.en_dictionary.app.util.HtmlParser
import com.jkangangi.en_dictionary.game.GameConstants.MAX_WORDS

// 5 </> 5 = false

@Composable
fun MediumGameLayout(
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
                timeLeft = state.timeLeft,
            )

            ButtonSection(
                onSkipClicked = onSkipClicked,
                onNextClicked = onNextClicked,
                btnEnabled = state.btnEnabled,
                isFinalWord = state.wordCount == MAX_WORDS
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
    timeLeft: Int,
) {

    val keyboard = LocalSoftwareKeyboardController.current
    val shutKeyBoard = remember {
        { keyboard?.hide() }
    }

    ElevatedCard(
        content = {
            Column(
                modifier = modifier,
                verticalArrangement = Arrangement.spacedBy(10.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                content = {

                    Text(
                        text = stringResource(id = R.string.medium_game_instructions),
                        style = MaterialTheme.typography.bodyMedium
                    )

                    GameTimer(
                        timeLeft = timeLeft,
                    )

                    ScrambledWord(
                        scrambledWord = scrambledWord,
                        guessedLetterCount = guess.length,
                        fullLetterCount = scrambledWord.length
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
private fun ScrambledWord(
    scrambledWord: String,
    guessedLetterCount: Int,
    fullLetterCount: Int,
    modifier: Modifier = Modifier
) {

    val brushColors = Brush.linearGradient(
        colors = listOf(
            MaterialTheme.colorScheme.primary,
            MaterialTheme.colorScheme.onSurface,
            MaterialTheme.colorScheme.secondary,
        )
    )
    val txtStyle = MaterialTheme.typography.bodyMedium

    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically,
        content = {
            Text(
                text = scrambledWord,
                style = txtStyle.merge(TextStyle(brush = brushColors))
                    .plus(MaterialTheme.typography.bodyLarge),
                fontWeight = FontWeight.Bold
            )
            Box(
                modifier = Modifier,
                content = {
                    Text(
                        text = stringResource(
                            id = R.string.letter_count,
                            guessedLetterCount,
                            fullLetterCount
                        ),
                        color = MaterialTheme.colorScheme.primary
                    )
                }
            )

        }
    )
}

@Composable
fun HintSection(
    hint: String,
    onHintClicked: () -> Unit,
    showHint: Boolean,
    modifier: Modifier = Modifier,
    rowModifier: Modifier = Modifier,
) {
    val styledMeaning = buildAnnotatedString {
        withStyle(style = SpanStyle(fontWeight = FontWeight.Bold, fontSize = 18.sp)) {
            append(stringResource(id = R.string.meaning))
        }
        withStyle(style = SpanStyle(fontSize = 16.sp)) {
            append(HtmlParser.htmlToString(hint))
        }
    }

    Column(
        verticalArrangement = Arrangement.SpaceEvenly,
        modifier = modifier,
    ) {
        Row(
            modifier = rowModifier.fillMaxWidth(),
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
                        Text(
                            text = stringResource(id = R.string.show_hint),
                            style = MaterialTheme.typography.bodyMedium
                        )
                    }
                )
            },
        )

        AnimatedVisibility(visible = showHint) {
            Box(
                modifier = modifier
                    .padding(6.dp)
                    .border(
                        1.dp,
                        shape = RoundedCornerShape(12),
                        color = MaterialTheme.colorScheme.secondary
                    ),
                contentAlignment = Alignment.Center,
                content = {
                    Text(
                        text = styledMeaning,
                        style = MaterialTheme.typography.bodySmall
                    )
                }
            )
        }
    }
}

@Composable
fun ButtonSection(
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
                // modifier = Modifier.weight(0.4f),
                content = {
                    Text(text = stringResource(id = R.string.skip_btn))
                }
            )
            //Spacer(modifier = Modifier.width(4.dp))

            Button(
                onClick = onNextClicked,
                //modifier = Modifier.weight(0.4f),
                enabled = btnEnabled,
                content = {
                    Text(
                        text = if (isFinalWord) stringResource(id = R.string.finish)
                        else stringResource(id = R.string.next)
                    )
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
            showHint = true,
            timeLeft = 20,
        )
    }
}