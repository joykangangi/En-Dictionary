package com.jkangangi.en_dictionary.definitions

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.jkangangi.en_dictionary.app.data.local.room.DictionaryEntity
import com.jkangangi.en_dictionary.app.data.remote.dto.Entry
import com.jkangangi.en_dictionary.app.data.remote.dto.Pronunciation
import com.jkangangi.en_dictionary.app.data.remote.dto.Textual
import com.jkangangi.en_dictionary.app.theme.En_DictionaryTheme
import com.jkangangi.en_dictionary.app.util.HtmlParser
import com.jkangangi.en_dictionary.app.util.isWord
import com.jkangangi.en_dictionary.app.util.phonetics
import com.jkangangi.en_dictionary.app.widgets.SpeakerIcon


/**
 * Phonetics for one word and words;
 * Phrases don't have pronunciations;ONLY sound
 */
@Composable
fun PhoneticsSection(
    modifier: Modifier,
    dictionary: DictionaryEntity?,
    onSpeakerClick: () -> Unit
) {
    val entries = dictionary?.pronunciations?.getOrNull(0)?.entries
    val hasAudio = entries?.any { entry -> entry.audioFiles.isNotEmpty() }
    val isPhrase = entries?.any { entry -> !entry.entry.isWord() }

    val isSpeakerOn = if (isPhrase == true) hasAudio else hasAudio

    Row(
        modifier = modifier.fillMaxWidth().padding(8.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        content = {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                // verticalArrangement = Arrangement.Bottom,
                modifier = modifier.padding(8.dp),
                content = {
                    Text(
                       // modifier = Modifier.weight(0.75f),
                        text = dictionary?.sentence ?: "word",
                        style = MaterialTheme.typography.headlineMedium,
                        fontWeight = FontWeight.Bold
                    )

                    if (isPhrase == false) { //a word
                        Text(
                            text = HtmlParser.htmlToString(entries[0].textual[0].pronunciation.phonetics()),
                            fontFamily = FontFamily.SansSerif,
                            style = MaterialTheme.typography.bodyMedium,
                            color = Color.DarkGray
                        )
                    }
                }
            )
            SpeakerIcon(
                modifier = Modifier.size(55.dp),
                onSpeakerClick = onSpeakerClick,
                isSpeakerOn = isSpeakerOn == true
            )
        }

    )
    /*Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Bottom,
        modifier = modifier.padding(8.dp),
        content = {
            Row(
                modifier = modifier,
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceEvenly,
                content = {

                    SpeakerIcon(
                        modifier = modifier.size(35.dp),
                        onSpeakerClick = onSpeakerClick,
                        isSpeakerOn = isSpeakerOn == true
                    )

                    Text(
                        text = dictionary?.sentence ?: "",
                        style = MaterialTheme.typography.headlineMedium,
                        fontWeight = FontWeight.Bold
                    )
                }
            )

            if (isPhrase == false) { //a word
                Text(
                    text = HtmlParser.htmlToString(entries[0].textual[0].pronunciation.phonetics()),
                    fontFamily = FontFamily.SansSerif,
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        }
    )*/
}

@Preview(showBackground = true)
@Composable
fun PreviewPhoneticsSection() {
    En_DictionaryTheme {
        PhoneticsSection(
            modifier = Modifier,
            dictionary = DictionaryEntity(
                sentence = "Tether", pronunciations = listOf(
                    Pronunciation(
                        entries = listOf(
                            Entry(textual = listOf(Textual(pronunciation = "/tidha/")))
                        )
                    )
                )
            ),
            onSpeakerClick = { })
    }

}