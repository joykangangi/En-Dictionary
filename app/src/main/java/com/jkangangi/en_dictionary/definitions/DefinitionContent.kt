package com.jkangangi.en_dictionary.definitions

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.jkangangi.en_dictionary.app.data.remote.dto.Definition
import com.jkangangi.en_dictionary.app.theme.En_DictionaryTheme
import com.jkangangi.en_dictionary.app.util.HtmlParser


@Composable
fun DefinitionHeader(modifier: Modifier, headerTxt: String?) {
    Row(
        modifier = modifier.padding(8.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
        content = {
            Divider(modifier.weight(0.35f))
            headerTxt?.let {
                Text(
                    modifier = modifier.weight(0.3F),
                    textAlign = TextAlign.Center,
                    text = it,
                    style = MaterialTheme.typography.bodyLarge,
                    fontFamily = FontFamily.SansSerif,
                    color = MaterialTheme.colorScheme.secondary,
                )
            }
            Divider(modifier.weight(0.35f))
        }
    )
}

@Composable
fun DefinitionBody(
    wordDefinitions: List<Definition?>?
) {
    Column {
        wordDefinitions?.forEachIndexed { index, word ->
            DefinitionDetail(titleText = "${index + 1}.", bodyText = word?.definition)
            word?.examples?.let { examples->
                DefinitionDetail(titleText = "Example", bodyText = examples.getOrNull(0)) //just the 1st example if its not null
            }
        }
    }
}

@Composable
private fun DefinitionDetail(
    modifier: Modifier = Modifier,
    titleText: String? = null,
    bodyText: String? = null,
) {

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
                append(titleText)
            }

            withStyle(
                style = SpanStyle(
                    fontFamily = FontFamily.SansSerif,
                    fontStyle = MaterialTheme.typography.bodyMedium.fontStyle
                )
            ) {
                val parsedDefinition = HtmlParser.htmlToString(bodyText)
                append(parsedDefinition)
            }

        },
        modifier = modifier,
    )
}

@Preview
@Composable
fun PreviewDefHeader() {
    En_DictionaryTheme {
        Column {
            DefinitionHeader(modifier = Modifier, headerTxt = "Adjective")
            DefinitionHeader(modifier = Modifier, headerTxt = "noun")
            DefinitionHeader(modifier = Modifier, headerTxt = "put up with")
        }
    }
}