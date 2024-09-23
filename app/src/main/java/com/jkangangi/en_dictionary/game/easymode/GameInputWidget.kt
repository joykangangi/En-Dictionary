package com.jkangangi.en_dictionary.game.easymode

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp

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

//TODO Start 1. Create the text field (otp-like), if the word placement is right,show a happy-face dialog, else show-you are learning-dialog with the answer