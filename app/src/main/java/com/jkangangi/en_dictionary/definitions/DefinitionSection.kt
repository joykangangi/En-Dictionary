package com.jkangangi.en_dictionary.definitions

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.jkangangi.en_dictionary.app.data.model.Dictionary
import com.jkangangi.en_dictionary.app.data.remote.dto.Phrase
import com.jkangangi.en_dictionary.app.util.HtmlParser
import com.jkangangi.en_dictionary.app.util.isWord

@Composable
fun DefinitionSection(
    word: Dictionary,
    modifier: Modifier,
) {
    val isWord by rememberSaveable { mutableStateOf(word.sentence.isWord()) } 
    Column(
        modifier = modifier
            .fillMaxWidth()
            .verticalScroll(rememberScrollState())
            .background(
                color = MaterialTheme.colorScheme.surface,
                shape = RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp)
            )
            .clip(shape = RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp))

    ) {
        if (isWord) {
            word.items.forEach { item ->
                DefinitionHeader(modifier = Modifier, headerTxt = item.partOfSpeech)
                DefinitionBody(wordDefinitions = item.definitions)
                item.synonyms?.let { Thesaurus(titleText = "Synonyms", bodyText = it) }
                item.antonyms?.let { Thesaurus(titleText = "Antonyms", bodyText = it) }
            }
        } else {
            word.items.forEach { item ->
                item.phrases?.let { phrases ->
                    PhraseDefinition(modifier = modifier, phrases = phrases)
                    item.synonyms?.let { Thesaurus(titleText = "Synonyms", bodyText = it) }
                    item.antonyms?.let { Thesaurus(titleText = "Antonyms", bodyText = it) }
                }
            }
        }
    }
}

@Composable
private fun PhraseDefinition(
    modifier: Modifier,
    phrases: List<Phrase?>,
) {
    Column {
        phrases.forEach { phrases ->
            DefinitionHeader(modifier = Modifier, headerTxt = phrases?.phrase)
            DefinitionBody(wordDefinitions = phrases?.definitions)
        }
    }
}

//antonyms+synonyms
@Composable
fun Thesaurus(modifier: Modifier = Modifier, titleText: String, bodyText:List<String>) {
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

            bodyText.forEach { text ->
                withStyle(
                    style = SpanStyle(
                        fontFamily = FontFamily.SansSerif,
                        fontStyle = MaterialTheme.typography.bodyMedium.fontStyle
                    )
                ) {
                    val parsedDefinition = HtmlParser.htmlToString(text)
                    append(parsedDefinition)
                }
            }
        },
        modifier = modifier,
    )
}