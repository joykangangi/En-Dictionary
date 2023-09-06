package com.jkangangi.en_dictionary.game

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LightbulbCircle
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.text.ExperimentalTextApi
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.jkangangi.en_dictionary.app.theme.En_DictionaryTheme

@OptIn(ExperimentalMaterial3Api::class, ExperimentalTextApi::class)
@Composable
fun GameCard(
    modifier: Modifier,
    scrambledWord: String,
    hint: String,
    guess: String,
    onGuessChanged: (String) -> Unit,
    onHintClicked: () -> Unit,
) {

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
                modifier = modifier.padding(8.dp),
                verticalArrangement = Arrangement.SpaceAround,
                horizontalAlignment = Alignment.CenterHorizontally,
                content = {
                    Text(
                        text = scrambledWord,
                        style = txtStyle.merge(TextStyle(brush = brushColors)),
                    )
                    //Text(text = stringResource(id = R.string.game_instructions))
                    Text(text = "Maagizo")
                    OutlinedTextField(value = guess, onValueChange = onGuessChanged)
                    HintSection(hint = hint, modifier = modifier, onHintClicked = onHintClicked)
                },
            )
        }
    )
}

@Composable
private fun HintSection(
    hint: String,
    modifier: Modifier,
    onHintClicked: () -> Unit,
) {
    val showHint = remember { mutableStateOf(false) }

    Column(
        verticalArrangement = Arrangement.SpaceEvenly,
        content = {
            Row(
                modifier = modifier,
                verticalAlignment = Alignment.CenterVertically,
                content = {
                    IconButton(
                        onClick = {
                            onHintClicked()
                            showHint.value = true
                        },
                        content = { Icons.Default.LightbulbCircle }
                    )
                    Text(text = "Show Hint")
                },
            )

            if (showHint.value) {
                Box(
                    modifier = modifier,
                    contentAlignment = Alignment.Center,
                    content = {
                        Column(content = {
                            Text(
                                text = "Meaning: $hint",
                                style = MaterialTheme.typography.bodySmall
                            )
                        })
                    })
            }
        }
    )
}

@Preview
@Composable
fun PrevGameCard() {
    En_DictionaryTheme {
        GameCard(
            modifier = Modifier,
            scrambledWord = "Runge Kutta",
            hint = "Fourth Order Formula",
            guess = "",
            onGuessChanged = { },
            onHintClicked = { }
        )
    }
}