package com.jkangangi.en_dictionary.game

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
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
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

/**
 * The app will use searched words (cached in db) to play.
 *
 */

class GameViewModel(private val repository: DictionaryRepository) : ViewModel() {
    private val _guessedWord = mutableStateOf("")
    val guessedWord: State<String> = _guessedWord

    private var _hintClicked by mutableStateOf(false)
    private val _gameUIState = MutableStateFlow(GameUIState())
    private val allWordItems = mutableSetOf<DictionaryEntity>()
    private val playedWords = mutableSetOf<DictionaryEntity>()
    var time by mutableLongStateOf(_gameUIState.value.timeLeft)

    private fun getAllWordItems() = repository.getAllHistory().map { items ->
        items.filter { it.sentence.isWord() }
    }

    val gameUIState = combine(
        flow = getAllWordItems(),
        flow2 = _gameUIState,
        transform = { wordItems, state ->

            if (allWordItems.isEmpty()) {
                allWordItems.addAll(wordItems)
            }

            GameUIState(
                wordItemsSize = wordItems.size,
                wordItem = state.wordItem,
                scrambledWord = state.scrambledWord,
                hint = state.hint,
                wordCount = state.wordCount,
                btnEnabled = state.btnEnabled,
                score = state.score,
                showHint = state.showHint
            )
        }
    ).stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(2500),
        initialValue = GameUIState()
    )

    fun getWordItem() {
        viewModelScope.launch {

            val wordItem = allWordItems.random()
            allWordItems.remove(wordItem)
            playedWords.add(wordItem)

            _gameUIState.update {
                it.copy(
                    wordItem = wordItem,
                    scrambledWord = wordItem.sentence.scramble(),
                    hint = wordItem.items[0].definitions[0].definition,
                    wordCount = playedWords.size
                )
            }
            //startTimer()
        }
    }

     fun startTimer() {
        viewModelScope.launch {
            while (_gameUIState.value.timeLeft > 0) {
                delay(1000)
                time -= 1000
                _gameUIState.update { it.copy(timeLeft = time) }
                Log.i("game view model","${_gameUIState.value.timeLeft}")
            }
            onSkipClicked() //can also be onSkipClicked;
        }
    }


    private fun resetWord() {
        _guessedWord.value = ""
        _gameUIState.update { it.copy(showHint = false, btnEnabled = false) }
        _hintClicked = false
        _gameUIState.update { it.copy(timeLeft = 60_000L) }
        getWordItem()
    }

    fun updateInput(userInput: String) {
        _guessedWord.value = userInput
        _gameUIState.update { it.copy(btnEnabled = _guessedWord.value.length == _gameUIState.value.wordItem?.sentence?.length) }

    }

    fun onNextClicked() {
        viewModelScope.launch {
            val isCorrectGuess = _guessedWord.value.trim().equals(_gameUIState.value.wordItem?.sentence,ignoreCase = true)
            val newScore = if (isCorrectGuess) _gameUIState.value.score + SCORE_INCREASE else _gameUIState.value.score - SCORE_INCREASE

            _gameUIState.update { it.copy(score = newScore) }

            if (playedWords.size == MAX_WORDS) {
                resetGame()
            } else {
                resetWord()
            }

        }
    }

    fun onSkipClicked() {
        _gameUIState.update { it.copy(score = _gameUIState.value.score - SKIP_DECREASE) }
        if (playedWords.size == MAX_WORDS) {
            resetGame()
        } else {
            resetWord()
        }
    }

    fun onHintClicked() {
        if (!_hintClicked) {
            _gameUIState.update { it.copy(score = _gameUIState.value.score - HINT_DECREASE) }
            _hintClicked = true
        }

        _gameUIState.update { it.copy(showHint = !_gameUIState.value.showHint) }


    }

    private fun resetGame() {
        playedWords.removeAll(playedWords)
        _gameUIState.update { GameUIState() }
    }

}