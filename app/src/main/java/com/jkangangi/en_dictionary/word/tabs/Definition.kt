package com.jkangangi.en_dictionary.word.tabs

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.jkangangi.en_dictionary.app.data.model.Definition
import com.jkangangi.en_dictionary.app.data.model.Meaning
import com.jkangangi.en_dictionary.app.data.model.Word
import com.jkangangi.en_dictionary.app.theme.En_DictionaryTheme

@Composable
fun DefinitionScreen(modifier: Modifier = Modifier, meaning: Meaning) {
    Column(modifier = modifier.fillMaxWidth()) {
        //use a loop/lazy to show all parts of speech
        WordType(
            wordType = meaning.partOfSpeech,
            definition = meaning.definitions,
        ) //TOdo VM

    }
}


@Composable
private fun WordType(
    modifier: Modifier = Modifier,
    wordType: String,
    definition: List<Definition>
) {
    Column {
        Row(
            modifier = modifier.padding(8.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(5.dp),
        ) {
            Text(
                text = wordType,
                style = MaterialTheme.typography.bodyLarge,
                fontFamily = FontFamily.Monospace,
                color = MaterialTheme.colorScheme.secondary,
            )
            Divider()
        }
        Text(
            buildAnnotatedString {
                withStyle(
                    style = SpanStyle(
                        fontStyle = MaterialTheme.typography.bodySmall.fontStyle,
                        color = MaterialTheme.colorScheme.primary,
                        fontSize = 24.sp
                    )
                ) {
                    append(".")//Todo number
                }

                withStyle(
                    style = SpanStyle(fontFamily = FontFamily.Default, fontSize = 24.sp)
                ) {
                    definition.forEach {
                        append(it.definition)
                    }
                }

                withStyle(
                    style = SpanStyle(
                        fontStyle = MaterialTheme.typography.bodySmall.fontStyle,
                        color = MaterialTheme.colorScheme.primary,
                        fontSize = 24.sp
                    )
                ) {
                    append(".")//Todo number
                }

                withStyle(
                    style = SpanStyle(fontFamily = FontFamily.Default, fontSize = 24.sp)
                ) {
                    definition.forEach {
                        append(it.example)
                    }
                }
            },
            modifier.padding(start = 16.dp)
        )

        Text(
            buildAnnotatedString {
                withStyle(
                    style = SpanStyle(fontSize = 26.sp, color = MaterialTheme.colorScheme.secondary)
                ) {
                    append(".")//Todo number
                }
            },
            modifier.padding(start = 16.dp)
        )
    }
}

@Preview
@Composable
private fun DefinitionPreview() {
    En_DictionaryTheme {
        Scaffold {
            val meaning = Meaning(
                antonyms = listOf(),
                definitions = listOf(),
                partOfSpeech = "noun",
                synonyms = listOf()
            )
            DefinitionScreen(modifier = Modifier, meaning = meaning)
        }
    }
}