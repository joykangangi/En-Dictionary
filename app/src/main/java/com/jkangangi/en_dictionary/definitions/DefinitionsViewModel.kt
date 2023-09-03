package com.jkangangi.en_dictionary.definitions

import android.content.Context
import android.media.AudioAttributes
import android.media.MediaPlayer
import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jkangangi.en_dictionary.app.data.local.DictionaryEntity
import com.jkangangi.en_dictionary.app.data.repository.DictionaryRepositoryImpl
import com.jkangangi.en_dictionary.app.util.isWord
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import java.io.IOException
import javax.inject.Inject


private const val AUDIO_BASE_URL = "https://download.xfd.plus/xfed/audio/"

@HiltViewModel
class DefinitionsViewModel @Inject constructor(repositoryImpl: DictionaryRepositoryImpl): ViewModel() {

    private val sentence = MutableStateFlow("")


    val dictionary = sentence.map { words->
        when(words) {
            "" -> DictionaryEntity()
            else -> repositoryImpl.getDictionaryItem(words)?: DictionaryEntity()
        }
    }.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5_000),
        initialValue = DictionaryEntity()
    )

    fun getDictionary(phrase: String) {
        this.sentence.update { phrase }
    }

    private val mediaPlayer = MediaPlayer()
    /**
     * Handle sound clicks for a word and words
     */
    fun onSpeakerClick(context: Context, dictionary: DictionaryEntity?) {
        val audioURL = dictionary?.let { getAudioLink(word = it) }
        mediaPlayer.setAudioAttributes(
            AudioAttributes.Builder()
                .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                .build()
        )
        try {
            mediaPlayer.setDataSource(context, Uri.parse(audioURL))
            mediaPlayer.prepareAsync()
            mediaPlayer.setOnPreparedListener { mPlayer ->
                mPlayer.start()
            }
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }



    private fun getAudioLink(word: DictionaryEntity): String {
        val entries = word.pronunciations[0].entries
        val isPhrase = entries.any { entry -> !entry.entry.isWord() }
        val phraseIndex = entries.indexOfFirst { entry -> !entry.entry.isWord() }
        val entry = if (isPhrase) entries[phraseIndex] else entries[0]
        val audioFile = entry.audioFiles[0].link
        return AUDIO_BASE_URL + audioFile
    }

}