package com.jkangangi.en_dictionary.definitions.tabs

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
import com.jkangangi.en_dictionary.app.widgets.HtmlParser

@Composable
fun WordDfnComponent(
    wordDefinitions: List<Definition?>
) {
    Column {
        wordDefinitions.forEachIndexed { index, definition ->
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
                        append("${index+1}. Definition: \n")
                    }

                    withStyle(
                        style = SpanStyle(
                            fontFamily = FontFamily.SansSerif,
                            fontStyle = MaterialTheme.typography.bodyMedium.fontStyle
                        )
                    ) {
                        val parsedDefinition = HtmlParser.htmlToString(definition?.definition ?: "null")
                        append(parsedDefinition)
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
                        append("Example: \n")
                    }

                    withStyle(
                        style = SpanStyle(
                            fontFamily = FontFamily.SansSerif,
                            fontStyle = MaterialTheme.typography.bodyMedium.fontStyle
                        )
                    ) {
                        val parsedExample = HtmlParser.htmlToString(definition?.examples?.getOrNull(0) ?: "null")
                        append(parsedExample) //just the 1st example if its not null
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
        phraseDefinitions.forEachIndexed { index, phrase ->
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
                        append("${index +1}. Phrase: \n")
                    }

                    withStyle(
                        style = SpanStyle(
                            fontFamily = FontFamily.SansSerif,
                            fontStyle = MaterialTheme.typography.bodyMedium.fontStyle
                        )
                    ) {
                        append(phrase?.phrase ?: "null")
                    }
                }
            )
            WordDfnComponent(wordDefinitions = phrase?.definitions ?: emptyList())
        }
    }
}