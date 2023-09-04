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
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
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
            Divider(modifier.weight(0.3f))
            headerTxt?.let {
                Text(
                    modifier = modifier.weight(0.4f),
                    textAlign = TextAlign.Center,
                    text = it,
                    style = MaterialTheme.typography.bodyLarge,
                    fontFamily = FontFamily.SansSerif,
                    color = MaterialTheme.colorScheme.secondary,
                )
            }
            Divider(modifier.weight(0.3f))
        }
    )
}

@Composable
fun DefinitionBody(
    wordDefinitions: List<Definition?>?,
    modifier: Modifier,
) {
    Column {
        wordDefinitions?.forEachIndexed { index, word ->
            DefinitionDetail(
                titleText = "${index + 1}.",
                bodyText = listOf(word?.definition),
                modifier = modifier
            )

            if (!word?.examples.isNullOrEmpty())
                DefinitionDetail(
                    titleText = "Example",
                    bodyText = listOf(word?.examples?.getOrNull(0)),
                    modifier = modifier
                ) //just the 1st example if its not null
            //Spacer(modifier = modifier.height(4.dp))
        }
    }
}

@Composable
fun DefinitionDetail(
    modifier: Modifier,
    titleText: String? = null,
    bodyText: List<String?>,
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(5.dp),
        content = {
            Text(
                text = "\n$titleText",
                fontFamily = FontFamily.SansSerif,
                fontStyle = MaterialTheme.typography.bodyMedium.fontStyle,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary,
            )

            bodyText.forEach { text -> //convert to list since coz of thesaurus section
                val parsedDefinition = HtmlParser.htmlToString(text)
                Text(
                    text = parsedDefinition,
                    fontFamily = FontFamily.SansSerif,
                    fontStyle = MaterialTheme.typography.bodyMedium.fontStyle,
                )
            }
        },
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
            DefinitionHeader(modifier = Modifier, headerTxt = "interjection")
        }
    }
}

@Preview
@Composable
fun PreviewDefDetail() {
    En_DictionaryTheme {
        Column {
            DefinitionDetail(
                modifier = Modifier,
                titleText = "1.",
                bodyText = listOf("A game played by foot")
            )
            DefinitionDetail(
                modifier = Modifier,
                titleText = "Example",
                bodyText = listOf("She is playing football despite the showers.")
            )
            DefinitionDetail(
                modifier = Modifier ,
                titleText = "Synonyms",
                bodyText = listOf("nice","good","word","word","does")
            )
        }
    }
}