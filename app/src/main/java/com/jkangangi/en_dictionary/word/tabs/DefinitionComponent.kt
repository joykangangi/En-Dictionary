package com.jkangangi.en_dictionary.word.tabs

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle
import com.jkangangi.en_dictionary.app.data.remote.dto.Definition
import com.jkangangi.en_dictionary.app.data.remote.dto.Phrase

@Composable
fun WordDfnComponent(
    wordDefinitions: List<Definition?>
) {
    Column {
        wordDefinitions.forEach { definition ->
            Text(
                buildAnnotatedString {
                    withStyle(
                        style = SpanStyle(
                            fontFamily = FontFamily.SansSerif,
                            fontStyle = MaterialTheme.typography.bodyMedium.fontStyle,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.primary,
                            textDecoration = TextDecoration.Underline,
                        )
                    ) {
                        append("Definition: \n")//Todo number using for loop
                    }

                    withStyle(
                        style = SpanStyle(
                            fontFamily = FontFamily.SansSerif,
                            fontStyle = MaterialTheme.typography.bodyMedium.fontStyle
                        )
                    ) {
                        append(definition?.definition ?: "null")
                    }
                }
            )
            Text(
                buildAnnotatedString {
                    withStyle(
                        style = SpanStyle(
                            fontFamily = FontFamily.SansSerif,
                            fontStyle = MaterialTheme.typography.bodyMedium.fontStyle,
                            color = MaterialTheme.colorScheme.primary,
                            fontWeight = FontWeight.Bold,
                            textDecoration = TextDecoration.Underline
                        )
                    ) {
                        append("Example: \n")//Todo number
                    }

                    withStyle(
                        style = SpanStyle(
                            fontFamily = FontFamily.SansSerif,
                            fontStyle = MaterialTheme.typography.bodyMedium.fontStyle
                        )
                    ) {
                        append(
                            definition?.examples?.getOrNull(0) ?: "null"
                        ) //just the 1st example if its not null
                    }
                }
            )
        }
    }
}


@Composable
fun PhraseDfnComponent(
    phraseDefinitions: List<Phrase?>
) {
    Column {
        phraseDefinitions.forEach { definition ->
            Text(
                buildAnnotatedString {
                    withStyle(
                        style = SpanStyle(
                            fontFamily = FontFamily.SansSerif,
                            fontStyle = MaterialTheme.typography.bodyMedium.fontStyle,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.primary,
                            textDecoration = TextDecoration.Underline,
                        )
                    ) {
                        append("Phrase: \n")//Todo number using for loop
                    }

                    withStyle(
                        style = SpanStyle(
                            fontFamily = FontFamily.SansSerif,
                            fontStyle = MaterialTheme.typography.bodyMedium.fontStyle
                        )
                    ) {
                        append(definition?.phrase ?: "null")
                    }
                }
            )
            WordDfnComponent(wordDefinitions = definition?.definitions ?: emptyList())
        }
    }
}
