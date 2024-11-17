package com.jkangangi.en_dictionary.game.mode.sharedwidgets

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.jkangangi.en_dictionary.app.theme.dimens
import com.jkangangi.en_dictionary.app.theme.padding16
import com.jkangangi.en_dictionary.game.util.GameConstants.MAX_WORD_LENGTH

/**
whatever is being typed is treated as one text input(like in any other input field)
but broken into chars, accessed using indices
[input] - the user's guess as whole word
[onInputChanged] - update input change, if we wanted to auto-validate after all boxes are filled,
 this function will be : onInputChanged: (String, Boolean) -> Unit,
onInputChanged(it.text,it.text.length == wordSize)
 [wordSize] - size of the full word
 */

@Composable
fun GameBoxInput(
    input: String,
    onInputChanged: (String) -> Unit,
    wordSize: Int,
    modifier: Modifier = Modifier,
) {

    val keyboardController = LocalSoftwareKeyboardController.current

    BasicTextField(
        modifier = modifier,
        value = TextFieldValue(text = input, selection = TextRange(input.length)),
        onValueChange = {
            if (it.text.length <= wordSize) {
                onInputChanged(it.text)
            }
            if (it.text.length == wordSize)  keyboardController?.hide()
        },
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
        decorationBox = {
            Row(
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                repeat(wordSize) { index ->
                    GameLetterBox(
                        input = input,
                        letterIndex = index
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                }
            }
        }
    )
}


@Composable
fun GameLetterBox(
    input: String,
    letterIndex: Int,
    modifier: Modifier = Modifier
) {

    val currentLetter = if (input.length > letterIndex) input[letterIndex].toString() else ""
    val isFocused = input.length == letterIndex

    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
            .size(adjustedBoxSize(word = input))
            .background(Color.Transparent)
            .border(
                width = if (isFocused) 2.dp else 1.dp,
                color = MaterialTheme.colorScheme.primary,
                shape = RoundedCornerShape(padding16())
            )
    ) {
        Text(
            modifier = Modifier.fillMaxWidth(),
            text = currentLetter,
            fontSize = adjustedFontSize(word = input),
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