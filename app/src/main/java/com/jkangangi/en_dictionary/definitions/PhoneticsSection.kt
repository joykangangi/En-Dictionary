package com.jkangangi.en_dictionary.definitions

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.jkangangi.en_dictionary.app.data.local.room.DictionaryEntity
import com.jkangangi.en_dictionary.app.data.remote.dto.AudioFile
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
    dictionary: DictionaryEntity?,
    onSpeakerClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val entries = dictionary?.pronunciations?.getOrNull(0)?.entries
    val hasAudio = entries?.any { entry -> entry.audioFiles.isNotEmpty() }
    val isPhrase = entries?.any { entry -> !entry.entry.isWord() }

    val isSpeakerOn = if (isPhrase == true) hasAudio else hasAudio

    ElevatedCard(
        modifier = modifier.padding(12.dp).shadow(elevation = 8.dp, shape = CardDefaults.shape),
        elevation = CardDefaults.elevatedCardElevation(defaultElevation = 8.dp),
        content = {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(12.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
                content = {
                    Column(
                        modifier = Modifier
                            .weight(1f)
                            .padding(8.dp),
                        content = {
                            Text(
                                text = dictionary?.sentence ?: "",
                                style = MaterialTheme.typography.headlineMedium,
                                fontWeight = FontWeight.Bold
                            )

                            if (isPhrase == false) { //a word
                                Text(
                                    text = HtmlParser.htmlToString(entries[0].textual[0].pronunciation.phonetics()),
                                    style = MaterialTheme.typography.bodyMedium,
                                    color = MaterialTheme.colorScheme.onSurface,
                                    maxLines = 2
                                )
                            }
                        }
                    )
                    if (isSpeakerOn == true) {
                        SpeakerIcon(onSpeakerClick = onSpeakerClick)
                    }
                }

            )
        }
    )
}

@Preview(showBackground = true, apiLevel = 33)
@Composable
fun PreviewPhoneticsSection() {
    En_DictionaryTheme {
        PhoneticsSection(
            modifier = Modifier,
            dictionary = DictionaryEntity(
                sentence = "Hippopotamus", pronunciations = listOf(
                    Pronunciation(
                        entries = listOf(
                            Entry(
                                textual = listOf(Textual(pronunciation = "/haepopotamas/")),
                                audioFiles = listOf(AudioFile(link = ""))
                            )
                        )
                    )
                )
            ),
            onSpeakerClick = { }
        )
    }

}