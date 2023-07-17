package com.jkangangi.en_dictionary.app.widgets

import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.withStyle

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun TextInput(
    modifier: Modifier = Modifier,
    input: String,
    onInputChange: (String) -> Unit,
    imeAction: ImeAction = ImeAction.Next,
    txtLabel: String,
    isRequired: Boolean,
) {

    val onClearInput = remember {
        {
            onInputChange("")
        }
    }

    val requiredColor = MaterialTheme.colorScheme.error
    val asterisk = if (isRequired) "*" else ""
    val transformedLabel = buildAnnotatedString {
        withStyle(style = SpanStyle(color = requiredColor)) {
            append(txtLabel)
            append(asterisk)
        }
    }
    val keyBoardController = LocalSoftwareKeyboardController.current

    OutlinedTextField(
        modifier = modifier,
        value = input,
        onValueChange = onInputChange,
        singleLine = true,
        placeholder = { Text(text = "Search...") },
        label = { Text(text = transformedLabel) },
        keyboardOptions = KeyboardOptions(imeAction = imeAction),
        keyboardActions = KeyboardActions(onDone = { keyBoardController?.hide() }),
        leadingIcon = {
            if (input.isNotBlank())
                IconButton(onClick = onClearInput) {
                    Icon(
                        imageVector = Icons.Default.Close,
                        contentDescription = null
                    )
                }
        }
    )
}