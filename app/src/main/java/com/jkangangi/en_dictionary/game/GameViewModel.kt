package com.jkangangi.en_dictionary.game

import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
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
import kotlinx.collections.immutable.toPersistentSet
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

/**
 * The app will use searched words (cached in db) to play.
 *
 */

class GameViewModel (private val repository: DictionaryRepository) : ViewModel() {
    private val _guessedWord = mutableStateOf("")
    val guessedWord: State<String> = _guessedWord

    //private val _hintClicks = mutableIntStateOf(0)
    private var _hintClicked by mutableStateOf(false)
    private val _gameUIState = MutableStateFlow(GameUIState())
    private val allItems = mutableSetOf<DictionaryEntity>()

    private fun getAllHistoryItems() = repository.getAllHistory().map { items ->
        items.filter { it.sentence.isWord() }
    }

    init {
        viewModelScope.launch {
            getAllHistoryItems().collect {
                if (allItems.isEmpty()) {
                    allItems.addAll(it)
                }
            }
        }
    }

    val gameUIState = combine(
        flow = getAllHistoryItems(),
        flow2 = _gameUIState,
        transform = { allHistoryItems, state ->

            GameUIState(
                wordItemsSize = allHistoryItems.size,
                wordItem = state.wordItem,
                scrambledWord = state.scrambledWord,
                hint = state.hint,
                wordCount = state.wordCount,
                isGameOver = state.isGameOver,
                showSubmit = state.showSubmit,
                nextEnabled = state.nextEnabled,
                score = state.score,
                showHint = state.showHint
            )
        }
    ).stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(2500),
        initialValue = GameUIState()
    )

    private val playedWords = mutableSetOf<DictionaryEntity>()


     fun getWordItem() {
        viewModelScope.launch {
            val wordItem = allItems.
            allItems.remove(wordItem)
            playedWords.add(wordItem)

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


    private fun resetWord() {
        _guessedWord.value = ""
        _gameUIState.update { it.copy(showHint = false, nextEnabled = false) }
        _hintClicked = false
        getWordItem()
    }

    fun updateInput(userInput: String) {
        _guessedWord.value = userInput
        _gameUIState.update { it.copy(nextEnabled = _guessedWord.value.length == _gameUIState.value.wordItem?.sentence?.length) }

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
        if (!_hintClicked) {
            _gameUIState.update { it.copy(score = _gameUIState.value.score - HINT_DECREASE) }
            _hintClicked = true
        }

        _gameUIState.update { it.copy(showHint = !_gameUIState.value.showHint) }


    }

    fun resetGame() {
        playedWords.removeAll(playedWords)
        _gameUIState.update { GameUIState() }
        getWordItem()
    }

}