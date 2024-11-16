package com.jkangangi.en_dictionary.app.widgets

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.jkangangi.en_dictionary.R

private const val MAX_WORD_LENGTH = 20

@Composable
fun TextInput(
    input: String,
    onInputChange: (String) -> Unit,
    txtLabel: String,
    txtPlaceholder: String,
    onClearInput: () -> Unit,
    modifier: Modifier = Modifier,
    isRequired: Boolean = false,
    isValid: Boolean = false,
    showWarningDescription: Boolean = true,
) {

    val optionalColor = MaterialTheme.colorScheme.primary
    val optional = if (isRequired) "*" else ""
    val transformedLabel = buildAnnotatedString {
        append(txtLabel)
        withStyle(style = SpanStyle(color = optionalColor)) {
            append(optional)
        }
    }

    Column(
        modifier = modifier,
        content = {
            OutlinedTextField(
                modifier = Modifier.fillMaxWidth(),
                value = input,
                onValueChange = {
                    if (it.length < MAX_WORD_LENGTH) onInputChange(it)
                },
                singleLine = true,
                textStyle = MaterialTheme.typography.bodyMedium,
                placeholder = {
                    Text(
                        text = txtPlaceholder,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Light,
                        color = MaterialTheme.colorScheme.onBackground.copy(0.5f)
                    )
                },
                label = {
                    Text(
                        fontWeight = FontWeight.Light,
                        fontSize = 12.sp,
                        text = transformedLabel,
                        style = MaterialTheme.typography.bodySmall
                    )
                },
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

            if (showWarningDescription) {

                if (!isValid) Text(
                    text = stringResource(id = R.string.inputError),
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.6f)
                )
                Spacer(modifier = Modifier.height(4.dp))
            }
        }
    )
}