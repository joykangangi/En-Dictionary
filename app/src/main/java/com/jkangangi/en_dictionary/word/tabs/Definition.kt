package com.jkangangi.en_dictionary.word.tabs

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.jkangangi.en_dictionary.app.data.remote.dto.Definition

@Composable
fun DefinitionScreen(modifier: Modifier = Modifier, meaning: Definition) {
    Column(modifier = modifier.fillMaxWidth()) {
        //use a loop/lazy to show all parts of speech
        WordType(
            wordType = meaning.definition,
            definition = meaning.examples,
        ) //TOdo VM

    }
}


@Composable
private fun WordType(
    modifier: Modifier = Modifier,
    wordType: String,
    definition: List<String>
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
                        append(it)
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
                        append(it)
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
    /*En_DictionaryTheme {
        Scaffold {
            val meaning = Definition(

            )
            DefinitionScreen(modifier = Modifier, meaning = meaning)
        }
    }*/
}