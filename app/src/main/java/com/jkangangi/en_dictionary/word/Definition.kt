package com.jkangangi.en_dictionary.word

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
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
import com.jkangangi.en_dictionary.app.theme.En_DictionaryTheme

@Composable
fun Definition(modifier: Modifier = Modifier) {
    Column(modifier = modifier.fillMaxWidth()) {
        //use a loop/lazy to show all parts of speech
        WordType(
            wordType = "noun",
            definition = "sdfghjklllllllllllllllllllllllllllll",
            example = "this is an example"
        ) //TOdo VM


    }
}


@Composable
fun WordType(modifier: Modifier = Modifier, wordType: String, definition: String, example: String) {
    Column {
        Row(
            modifier = modifier.padding(8.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceAround,
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
                    style = SpanStyle(fontSize = 26.sp, color = MaterialTheme.colorScheme.secondary)
                ) {
                    append(".")//Todo number
                }

                withStyle(
                    style = SpanStyle(fontFamily = FontFamily.Default, fontSize = 24.sp)
                ) {
                    append(definition)
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

                withStyle(
                    style = SpanStyle( fontSize = 24.sp, color = Color.Gray)
                ) {
                    append(example)
                }
            },
            modifier.padding(start = 16.dp)
        )
    }
}

@Preview
@Composable
fun DefinitionPreview() {
    En_DictionaryTheme {
        Scaffold {
            Definition(modifier = Modifier)
        }
    }
}