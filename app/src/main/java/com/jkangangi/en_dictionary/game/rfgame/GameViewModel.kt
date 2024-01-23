package com.jkangangi.en_dictionary.game.rfgame

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jkangangi.en_dictionary.app.data.local.room.DictionaryEntity
import com.jkangangi.en_dictionary.app.data.repository.DictionaryRepository
import com.jkangangi.en_dictionary.app.util.isWord
import com.jkangangi.en_dictionary.app.util.scramble
import com.jkangangi.en_dictionary.game.GameConstants.HINT_DECREASE
import com.jkangangi.en_dictionary.game.GameConstants.SCORE_INCREASE
import com.jkangangi.en_dictionary.game.GameConstants.SKIP_DECREASE
import com.jkangangi.en_dictionary.game.GameUIState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * The app will use searched words (cached in db).
 *
 */

@HiltViewModel
class GameViewModel @Inject constructor(repository: DictionaryRepository) : ViewModel() {

    private val _guessedWord = MutableStateFlow("")
    val guessedWord = _guessedWord.asStateFlow()


    private val _hintClicks = MutableStateFlow(0)
    private val playedWords = mutableListOf<DictionaryEntity>()

    private val allWords = repository.getAllHistory()
    private val _gameUIState = MutableStateFlow(GameUIState())

    init {
        viewModelScope.launch {
            _gameUIState.update { state ->
                state.copy(wordItems = allWords.first().filter { it.sentence.isWord() }) }
        }
    }

    val gameUIState = _gameUIState.map { state ->
        //do this in a function
        val allItems = state.wordItems
        val playingWords = if (allItems?.isNotEmpty() == true) {
        allItems.subList(fromIndex = 1, toIndex = minOf(state.wordItems.size,5))
    } else {
        emptyList()
    }
        val wordItem = if (playingWords.isNotEmpty()) playingWords.random() else DictionaryEntity()

        GameUIState(
            wordItems = allItems,
            wordItem = wordItem,
            scrambledWord = wordItem.sentence.scramble(),
            hint = wordItem.items[0].definitions[0].definition,
            wordCount = playedWords.size,
            score = state.score,
            nextEnabled = _guessedWord.value.length == wordItem.sentence.length,
            isGameOver = playedWords.size == 6,
            isGameOn = playingWords.isNotEmpty(),
            showSubmit = playedWords.size == 5
        )
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(2500L),
        initialValue = GameUIState()
    )



    fun updateInput(userInput: String) {
        _guessedWord.update { userInput }
        Log.i("GAMEVM", "STATE = ${_gameUIState.value}")
    }

    fun onNextClicked() {

        if (_guessedWord.value.trim().length == _gameUIState.value.wordItem?.sentence?.length ) {
            _gameUIState.update { it.copy(score = _gameUIState.value.score + SCORE_INCREASE) }
        } else {
            _gameUIState.update { it.copy(score = _gameUIState.value.score - SCORE_INCREASE) }
        }
    }

    fun onSkipClicked() {
        _gameUIState.update { it.copy(score = _gameUIState.value.score - SKIP_DECREASE) }
    }

    fun onHintClicked() {
        _hintClicks.update { _hintClicks.value++ }
        if (_hintClicks.value == 1) {
            _gameUIState.update { it.copy(score = _gameUIState.value.score - HINT_DECREASE) }
        }
    }

    fun resetGame() {
        playedWords.removeAll(playedWords)
        _gameUIState.update { GameUIState() }
    }

}