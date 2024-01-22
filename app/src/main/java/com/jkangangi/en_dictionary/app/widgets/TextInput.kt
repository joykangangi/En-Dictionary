package com.jkangangi.en_dictionary.app.widgets

import androidx.compose.foundation.layout.Column
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.withStyle
import com.jkangangi.en_dictionary.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TextInput(
    modifier: Modifier = Modifier,
    input: String,
    onInputChange: (String) -> Unit,
    txtLabel: String,
    isRequired: Boolean = false,
    onClearInput: () -> Unit,
    isValid: Boolean = false,
) {

    val optionalColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.5f)
    val optional = if (isRequired) "" else " (optional)"
    val transformedLabel = buildAnnotatedString {
        append(txtLabel)
        withStyle(style = SpanStyle(color = optionalColor, fontStyle = FontStyle.Italic)) {
            append(optional)
        }
    }

    Column(
        content = {
            OutlinedTextField(
                modifier = modifier,
                value = input,
                onValueChange = onInputChange,
                singleLine = true,
                textStyle = MaterialTheme.typography.bodyMedium,
                label = { Text(text = transformedLabel, style = MaterialTheme.typography.bodySmall) },
                trailingIcon = {
                    if (input.isNotEmpty())
                        IconButton(onClick = onClearInput) {
                            Icon(
                                imageVector = Icons.Default.Close,
                                contentDescription = null
                            )
                        }
                },
            )

            if (!isValid) Text(
                text = stringResource(id = R.string.inputError),
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.5f)
            )
        }
    )
}