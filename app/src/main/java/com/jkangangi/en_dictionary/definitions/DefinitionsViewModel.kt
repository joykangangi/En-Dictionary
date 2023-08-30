package com.jkangangi.en_dictionary.definitions

import android.content.Context
import android.media.AudioAttributes
import android.media.MediaPlayer
import android.net.Uri
import androidx.lifecycle.ViewModel
import com.jkangangi.en_dictionary.app.data.model.Dictionary
import com.jkangangi.en_dictionary.app.util.isWord
import dagger.hilt.android.lifecycle.HiltViewModel
import java.io.IOException
import javax.inject.Inject


private const val AUDIO_BASE_URL = "https://download.xfd.plus/xfed/audio/"

@HiltViewModel
class DefinitionsViewModel @Inject constructor(): ViewModel() {

    /*
    private val sentence = MutableStateFlow("")
    val definition = sentence.map { phrase ->
        when (phrase) {
            "" -> Dictionary()
            else -> repository.getDictionary(phrase).last()?:Dictionary()
        }
    }.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5_000),
        Dictionary(),
    )

    fun getDictionary(phrase: String) {
        this.sentence.update { phrase }
    }*/

    private val mediaPlayer = MediaPlayer()
    /**
     * Handle sound clicks for a word and words
     */
    fun onSpeakerClick(context: Context, word: Dictionary?) {
        val audioURL = word?.let { getAudioLink(word = it) }
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



    private fun getAudioLink(word: Dictionary): String {
        val entries = word.pronunciations[0].entries
        val isPhrase = entries.any { entry -> !entry.entry.isWord() }
        val phraseIndex = entries.indexOfFirst { entry -> !entry.entry.isWord() }
        val entry = if (isPhrase) entries[phraseIndex] else entries[0]
        val audioFile = entry.audioFiles[0].link
        return AUDIO_BASE_URL + audioFile
    }

}