package com.jkangangi.en_dictionary.game.sharedwidgets

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.jkangangi.en_dictionary.app.theme.dimens
import com.jkangangi.en_dictionary.app.theme.largePadding
import com.jkangangi.en_dictionary.game.GameConstants.MAX_WORD_LENGTH

@Composable
fun BoxInput(
    input: String,
    onInputChanged: (String, Boolean) -> Unit,
    inputCount: Int,
    modifier: Modifier = Modifier,
) {

    BasicTextField(
        modifier = modifier,
        value = TextFieldValue(input, selection = TextRange(input.length)),
        onValueChange = {
            if (it.text.length <= inputCount) {
                onInputChanged.invoke(it.text, it.text.length == inputCount)
            }
        },
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
        decorationBox = {
            Row(
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                repeat(inputCount) { index ->
                    CharView(
                        index = index,
                        text = input
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                }
            }
        }
    )
}


@Composable
fun CharView(
    index: Int,
    text: String,
    modifier: Modifier = Modifier
) {
    val isFocused = text.length == index
    val char = when {
        index == text.length -> "0"
        index > text.length -> ""
        else -> text[index].toString()
    }

    Text(
        modifier = modifier
            .width(40.dp)
            .border(
                1.dp, when {
                    isFocused -> MaterialTheme.colorScheme.primary
                    else -> MaterialTheme.colorScheme.onPrimary
                }, RoundedCornerShape(8.dp)
            )
            .padding(2.dp),
        text = char,
        style = MaterialTheme.typography.bodyLarge,
        color = if (isFocused) MaterialTheme.colorScheme.onPrimary else MaterialTheme.colorScheme.primary,
        textAlign = TextAlign.Center
    )


}

@Composable
fun GameInputWidget(
    onLetterChange: (String) -> Unit,
    wordSize: Int,
) {
    val letterStates = remember {
        Array(wordSize) { mutableStateOf("") }
    }

    val focusRequester = remember { FocusRequester() }
    val focusManager = LocalFocusManager.current
    val keyboardController = LocalSoftwareKeyboardController.current
    val letter = remember { mutableStateOf("") }

    Column(
        content = {
            //this is hidden but helps to capture user input

            TextField(
                modifier = Modifier
                    .focusRequester(focusRequester)
                    .width(1.dp)
                    .height(0.dp),
                value = letter.value,
                maxLines = 1,
                onValueChange = { newLetter ->
                    letter.value = validateLetter(letter.value, newLetter, wordSize)
                    for (i in 0 until wordSize) {
                        letterStates[i].value = getLetter(i, newLetter)
                    }
                    onLetterChange(letter.value)
                    if (letter.value.length == wordSize) {
                        keyboardController?.hide()
                    }
                },
                keyboardOptions = KeyboardOptions(
                    imeAction = ImeAction.Done
                ),
                keyboardActions = KeyboardActions(
                    onDone = {
                        focusManager.clearFocus()
                    }
                )
            )

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable(
                        indication = null,
                        interactionSource = remember { MutableInteractionSource() }
                    ) {
                        focusRequester.requestFocus()
                        keyboardController?.show()
                    },
                horizontalArrangement = Arrangement.SpaceAround,
                content = {
                    for (i in 0 until wordSize) {
                        GameLetterBox(letter = letterStates[i].value)
                    }
                }
            )
        }
    )
}

private fun validateLetter(currentLetter: String, letter: String, wordSize: Int): String {
    return if (letter.length < currentLetter.length) {
        letter
    } else if (letter.length > wordSize) {
        currentLetter
    } else {
        letter
    }
}


private fun getLetter(position: Int, word: String): String {
    return if (word.length > position) {
        word[position].toString()
    } else {
        ""
    }
}

@Composable
fun GameLetterBox(
    letter: String,
    modifier: Modifier = Modifier
) {
    val borderColor = MaterialTheme.colorScheme.primary

    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
            .size(adjustedBoxSize(word = letter))
            .background(Color.Transparent)
            .border(
                width = 1.dp,
                color = borderColor,
                shape = RoundedCornerShape(largePadding())
            )
    ) {
        Text(
            modifier = Modifier.fillMaxWidth(),
            text = letter,
            fontSize = adjustedFontSize(word = letter),
            textAlign = TextAlign.Center,
        )

    }
}


@Composable
fun adjustedBoxSize(
    word: String,
    maxWordSize: Int = MAX_WORD_LENGTH,
): Dp {

    val medBoxSize = MaterialTheme.dimens.mediumGameBox.value
    val minBoxSize = MaterialTheme.dimens.smallGameBox.value

    val calculatedBoxSize = remember(word) {
        val scaleFactor = maxWordSize.toFloat() / word.length.coerceAtLeast(1)
        (medBoxSize * scaleFactor).coerceIn(minBoxSize, medBoxSize)
    }

    return Dp(calculatedBoxSize)
}

@Composable
fun adjustedFontSize(
    word: String,
    maxWordSize: Int = MAX_WORD_LENGTH,
): TextUnit {

    val medTextSize = MaterialTheme.dimens.mediumGameText.value
    val minTextSize = MaterialTheme.dimens.smallGameText.value

    val calculatedFontSize = remember(word) {
        val scaleFactor = maxWordSize.toFloat() / word.length.coerceAtLeast(1)
        (medTextSize * scaleFactor).coerceIn(minTextSize, medTextSize)
    }

    return calculatedFontSize.sp
}
//TODO Start 1. Create the text field (otp-like), if the word placement is right,show a happy-face dialog, else show-you are learning-dialog with the answer