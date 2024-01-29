package com.jkangangi.en_dictionary.game

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jkangangi.en_dictionary.app.data.local.room.DictionaryEntity
import com.jkangangi.en_dictionary.app.data.repository.DictionaryRepository
import com.jkangangi.en_dictionary.app.util.isWord
import com.jkangangi.en_dictionary.app.util.scramble
import com.jkangangi.en_dictionary.game.GameConstants.HINT_DECREASE
import com.jkangangi.en_dictionary.game.GameConstants.MAX_WORDS
import com.jkangangi.en_dictionary.game.GameConstants.SCORE_INCREASE
import com.jkangangi.en_dictionary.game.GameConstants.SKIP_DECREASE
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.collections.immutable.toPersistentList
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * The app will use searched words (cached in db).
 *
 */

@HiltViewModel
class GameViewModel @Inject constructor(private val repository: DictionaryRepository) :
    ViewModel() {
        private val _guessedWord = MutableStateFlow("")
        val guessedWord = _guessedWord.asStateFlow()

        private val _hintClicks = MutableStateFlow(0)
        private val allWords = repository.getAllHistory()
        private val _gameUIState = MutableStateFlow(GameUIState())
        val gameUIState = _gameUIState.asStateFlow()
        private val allWordItems = mutableListOf<DictionaryEntity>()
        private val playedWords = mutableListOf<DictionaryEntity>()

        init {
            getWordItem()
        }

        private fun getWordItem() {
            Log.i("GameVM","first = ${playedWords.size}")
            viewModelScope.launch {
                _gameUIState.update { state ->
                    state.copy(wordItems = allWords.firstOrNull()?.filter { it.sentence.isWord() }?.toPersistentList())
                }
                _gameUIState.first().wordItems?.let { allWordItems.addAll(it) }
                delay(1000L)

                val wordItem = _gameUIState.value.wordItems?.random()
                allWordItems.remove(wordItem)
                if (wordItem != null) {
                    playedWords.add(wordItem)
                }
                Log.i("GameVM","${playedWords.size}, ${wordItem?.sentence}")

                if (wordItem != null) {
                    _gameUIState.update {
                        it.copy(
                            wordItem = wordItem,
                            scrambledWord = wordItem.sentence.scramble(),
                            hint = wordItem.items[0].definitions[0].definition,
                            wordCount = playedWords.size,
                            isGameOver = playedWords.size == MAX_WORDS + 1,
                            showSubmit = playedWords.size == MAX_WORDS
                        )
                    }
                }

            }
        }


    private fun resetWord() {
        _guessedWord.update { "" }
        _gameUIState.update { it.copy(showHint = false, nextEnabled = false) }
        _hintClicks.update { 0 }
        getWordItem()
    }

    fun updateInput(userInput: String) {
        _guessedWord.update { userInput }
        _gameUIState.update { it.copy(nextEnabled = _guessedWord.value.length == _gameUIState.value.wordItem?.sentence?.length) }
        Log.i("GAMEVM", "STATE  triggered")
    }

    fun onNextClicked() {

        if (_guessedWord.value.trim() == _gameUIState.value.wordItem?.sentence) {
            _gameUIState.update { it.copy(score = _gameUIState.value.score + SCORE_INCREASE) }
        } else {
            _gameUIState.update { it.copy(score = _gameUIState.value.score - SCORE_INCREASE) }
        }
        resetWord()
    }

    fun onSkipClicked() {
        _gameUIState.update { it.copy(score = _gameUIState.value.score - SKIP_DECREASE) }
        resetWord()
    }

    fun onHintClicked() {
        _hintClicks.update { it + 1 }
        _gameUIState.update { it.copy(showHint = !_gameUIState.value.showHint) }
        if (_hintClicks.value == 1) {
            _gameUIState.update { it.copy(score = _gameUIState.value.score - HINT_DECREASE) }
        }
    }

    fun resetGame() {
        playedWords.removeAll(playedWords)
        _gameUIState.update { GameUIState() }
        getWordItem()
    }

}