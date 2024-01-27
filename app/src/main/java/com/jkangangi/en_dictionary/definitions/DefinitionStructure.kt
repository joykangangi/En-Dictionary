package com.jkangangi.en_dictionary.definitions

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.jkangangi.en_dictionary.app.data.remote.dto.Definition
import com.jkangangi.en_dictionary.app.theme.En_DictionaryTheme
import com.jkangangi.en_dictionary.app.util.HtmlParser


@Composable
fun DefinitionHeader(modifier: Modifier = Modifier, headerTxt: String?) {
    Row(
        modifier = modifier.padding(horizontal = 8.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        content = {
            headerTxt?.let {
                Text(
                    textAlign = TextAlign.Center,
                    text = it,
                    style = MaterialTheme.typography.headlineSmall,
                    //fontFamily = FontFamily.SansSerif,
                    color = MaterialTheme.colorScheme.primary,
                    fontWeight = FontWeight.Bold
                )
            }
            Divider()
        }
    )
}

@Composable
fun DefinitionBody(
    wordDefinitions: List<Definition?>?,
    modifier: Modifier = Modifier,
) {

    wordDefinitions?.forEachIndexed { index, word ->
        DefinitionDetail(
            titleText = "${index + 1}.",
            bodyText = listOf(word?.definition),
            modifier = modifier
        )
        Spacer(modifier = modifier.height(4.dp))
        if (!word?.examples.isNullOrEmpty())
            DefinitionDetail(
                titleText = "Example ",
                bodyText = listOf(word?.examples?.getOrNull(0)),
                modifier = modifier
            ) //just the 1st example if its not null
    }
}

@Composable
fun DefinitionDetail(
    modifier: Modifier,
    bodyText: List<String?>,
    bodyColor: Color = Color.Unspecified,
    titleText: String? = null,
) {
    Text(
        text = buildAnnotatedString {
            withStyle(
                style = SpanStyle(
                    fontStyle = MaterialTheme.typography.bodyMedium.fontStyle,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.primary,
                )
            ) {
                append("$titleText")
            }
            bodyText.forEach { text ->
                val parsedText = HtmlParser.htmlToString(text)
                withStyle(
                    style = SpanStyle(
                        fontStyle = MaterialTheme.typography.bodyMedium.fontStyle,
                        color = bodyColor
                    )
                ) {
                    append(parsedText)
                }
            }
        },
        modifier = modifier
    )
}

@Preview
@Composable
fun PreviewDefHeader() {
    En_DictionaryTheme {
        Column {
            DefinitionHeader(headerTxt = "Adjective")
            DefinitionHeader(headerTxt = "noun")
            DefinitionHeader(headerTxt = "put up with")
            DefinitionHeader(headerTxt = "interjection")
        }
    }
}

@Preview
@Composable
fun PreviewDefDetail() {
    En_DictionaryTheme {
        Column {
            DefinitionHeader(modifier = Modifier, headerTxt = "noun")
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
                modifier = Modifier,
                titleText = "Synonyms",
                bodyText = listOf("nice", "good", "word", "word", "does")
            )
        }
    }
}