package com.jkangangi.en_dictionary.word.tabs

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
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
import com.jkangangi.en_dictionary.app.data.model.Dictionary
import com.jkangangi.en_dictionary.app.data.remote.dto.Definition
import com.jkangangi.en_dictionary.app.data.remote.dto.Item

@Composable
fun DefinitionScreen(modifier: Modifier = Modifier, word: Dictionary) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .verticalScroll(rememberScrollState())
    ) {
        //use a loop/lazy to show all parts of speech
        WordType(
            wordType = word.items,
        )

    }
}


@Composable
private fun WordType(
    modifier: Modifier = Modifier,
    wordType: List<Item>,
) {
    Column {
        wordType.forEach { item ->
            Row(
                modifier = modifier.padding(8.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(5.dp),
                content = {
                    Text(
                        text = item.partOfSpeech,
                        style = MaterialTheme.typography.bodyLarge,
                        fontFamily = FontFamily.Monospace,
                        color = MaterialTheme.colorScheme.secondary,
                    )
                    Divider()
                }
            )

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
                        append(item.definitions[0].definition)

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
                        append(item.definitions[1].definition)
                    }
                }
            )
        }
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