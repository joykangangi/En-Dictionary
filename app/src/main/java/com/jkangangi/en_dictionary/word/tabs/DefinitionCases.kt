package com.jkangangi.en_dictionary.word.tabs

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import com.jkangangi.en_dictionary.app.data.model.Dictionary
import com.jkangangi.en_dictionary.app.data.remote.dto.Item

@Composable
fun DefinitionPhrase(modifier: Modifier = Modifier, word: Dictionary) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(12.dp)
            .verticalScroll(rememberScrollState())
    ) {
        //use a loop/lazy to show all parts of speech
        WordType(
            modifier = modifier,
            wordType = word.items,
        )
    }
}


@Composable
private fun WordType(
    modifier: Modifier,
    wordType: List<Item?>,
) {
    Column {
        wordType.forEach { item ->
            item?.let {
                Row(
                    modifier = modifier.padding(8.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(5.dp),
                    content = {
                        Text(
                            text = it.partOfSpeech,
                            style = MaterialTheme.typography.bodyLarge,
                            fontFamily = FontFamily.SansSerif,
                            color = MaterialTheme.colorScheme.secondary,
                        )
                    }
                )

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
                            append(it.definitions.getOrNull(0)?.definition ?:"null")
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
                            append(it.definitions.getOrNull(0)?.examples?.getOrNull(0))
                        }
                    }
                )
            }
        }
    }
}