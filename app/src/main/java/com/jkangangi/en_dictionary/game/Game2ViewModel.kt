package com.jkangangi.en_dictionary.game

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jkangangi.en_dictionary.app.data.local.room.DictionaryEntity
import com.jkangangi.en_dictionary.app.data.repository.DictionaryRepository
import com.jkangangi.en_dictionary.app.util.isWord
import com.jkangangi.en_dictionary.app.util.scramble
import com.jkangangi.en_dictionary.game.GameConstants.MAX_WORDS
import com.jkangangi.en_dictionary.game.GameConstants.SCORE_INCREASE
import com.jkangangi.en_dictionary.game.GameConstants.SKIP_DECREASE
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GameViewModel2 @Inject constructor(
    private val repository: DictionaryRepository
) : ViewModel() {
    private val _gameUIState = MutableStateFlow(GameUIState())
    val gameUIState = _gameUIState.asStateFlow()

    private var _currentWord: DictionaryEntity? = null
    private val _playedWords = mutableSetOf<DictionaryEntity>()
    private val _guessedAns = MutableStateFlow("")
    val guessedAns = _guessedAns.asStateFlow()
    private val _hintClick = MutableStateFlow(0)


    init {
        getWord()
        enableButton()
        wordCount()
    }

    private fun getWord() {
        viewModelScope.launch {
            val words = repository.getAllHistory().first().filter { it.sentence.isWord() }
            _gameUIState.update { it.copy(isGameOn = words.isNotEmpty()) }
            _currentWord = words.first()
            _gameUIState.update {
                it.copy(
                    scrambledWord = _currentWord?.sentence?.scramble() ?: "empty"
                )
            }
            _playedWords.add(_currentWord!!)
            words.minus(_currentWord)

        }
    }

    private fun resetWord() {
        _gameUIState.update { it.copy(scrambledWord = "", hint = "") }
        getWord()
    }

    fun resetGame() {
        _playedWords.removeAll(_playedWords)
        _gameUIState.update { GameUIState() }
        getWord()
        enableButton()
        wordCount()
    }

    private fun isGameOver() {
        when (_playedWords.size) {
            MAX_WORDS -> {
                _gameUIState.update { it.copy(isGameOver = true) }
            }

            MAX_WORDS - 1 -> {
                _gameUIState.update { it.copy(showSubmit = true) }
            }

            else -> {
                resetWord()
                enableButton()
                wordCount()
            }
        }
    }

    fun onNextClicked() {
        if (_guessedAns.value == _currentWord?.sentence) {
            _gameUIState.update { it.copy(score = _gameUIState.value.score.plus(SCORE_INCREASE)) }
        } else {
            _gameUIState.update { it.copy(score = _gameUIState.value.score.minus(SCORE_INCREASE)) }
        }
        isGameOver()
    }

    fun onSkipClicked() {
        _gameUIState.update { it.copy(score = _gameUIState.value.score.minus(SKIP_DECREASE)) }
        isGameOver()
    }

    fun onHintClicked() {
        _hintClick.update { it + 1 }
        if (_hintClick.value == 1) {
            _gameUIState.update { it.copy(score = it.score.minus(GameConstants.HINT_DECREASE)) }
        }
    }

    private fun wordCount() {
        _gameUIState.update { it.copy(wordCount = _playedWords.size) }
    }

    private fun enableButton() {
        _gameUIState.update { it.copy(nextEnabled = (_currentWord?.sentence?.length == _guessedAns.value.length)) }
    }

    fun updateInput(input: String) {
        _guessedAns.update { input }
        enableButton()
    }

}