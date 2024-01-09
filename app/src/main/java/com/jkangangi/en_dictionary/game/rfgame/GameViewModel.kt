package com.jkangangi.en_dictionary.game.rfgame

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jkangangi.en_dictionary.app.data.local.DictionaryEntity
import com.jkangangi.en_dictionary.app.data.repository.DictionaryRepositoryImpl
import com.jkangangi.en_dictionary.app.util.scramble
import com.jkangangi.en_dictionary.game.GameConstants.MAX_WORDS
import com.jkangangi.en_dictionary.game.GameConstants.SCORE_INCREASE
import com.jkangangi.en_dictionary.game.GameConstants.SKIP_DECREASE
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.collections.immutable.minus
import kotlinx.collections.immutable.persistentSetOf
import kotlinx.collections.immutable.toPersistentSet
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * The app will use searched words (cached in db).
 *
 *//*

@HiltViewModel
class GameViewModel @Inject constructor(repositoryImpl: DictionaryRepositoryImpl) : ViewModel() {

    val history = repositoryImpl.getAllHistory()
        .map { entities -> entities.toPersistentSet() }
        .stateIn( //pipe flow->stateflow
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = persistentSetOf()
        )


    private val _gameState = MutableStateFlow(GameUIState())
    val gameState = _gameState.asStateFlow()
    private val playedWords = persistentSetOf<DictionaryEntity>()
    //private val allWords = history.map { it.toPersistentSet() }

    private var currentWord: String? = null
    private val allWords = MutableStateFlow(history.value)
    //private val currentDictionary = history.value.random()

    init {
        viewModelScope.launch {
            if (history.value.isNotEmpty()) getWord()
            else {
                history
            }
        }
    }


    private fun getWord(): String {
        viewModelScope.launch {
            val currentDictionary = history.value.random()
            currentWord = currentDictionary.sentence
            _gameState.update { it.copy(scrambledWord = currentWord.scramble()) }
            _gameState.update { it.copy(hint = currentDictionary.items[0].definitions[0].definition) }
            playedWords.add(currentDictionary)
            allWords.minus(currentDictionary)
            _gameState.update { it.copy(wordCount = playedWords.size) }
        }
        return _gameState.value.scrambledWord
    }

    private fun hasError() {
        val correctWordSize = currentWord?.length
        val guessWordSize = _gameState.value.guess.length
        val isEnabled = correctWordSize == guessWordSize
        _gameState.update { it.copy(btnEnabled = isEnabled) }
    }

    private val gameState2 = _gameState
        .map { state ->
            val allWords = history.value
            val dictionary = allWords.random()
            val correctWordSize = state.dictionary.sentence.length
            val guessWordSize = _gameState.value.guess.length
            playedWords.add(dictionary)
            allWords.minus(playedWords)

            GameUIState(
                dictionary = dictionary,
                scrambledWord = dictionary.sentence.scramble(),
                wordCount = playedWords.size,
                btnEnabled = correctWordSize != guessWordSize,
                isGameOver = state.wordCount == MAX_WORDS,
            )
        }
        .flowOn(Dispatchers.Default)
        .stateIn(
            viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = GameUIState()
        )

    fun updateInput(input: String) {
        _gameState.update { it.copy(guess = input) }
        hasError()
        Log.i("GAME VM", "GameState = ${_gameState.value}")//null
        Log.i("GAME VM", "GameHistoryValue = ${history.value}")//not null
        Log.i("GAME VM", "GameHistoryValueFirst = $currentWord")//null
        Log.i("GAME VM", "GameState2 = ${gameState2.value}")//null
    }

    private fun isGameOver() {
        val isGameOver = _gameState.value.wordCount == MAX_WORDS
        _gameState.update { it.copy(isGameOver = isGameOver) }
    }

    fun onNextClicked() {
        if (_gameState.value.guess == currentWord) {
            _gameState.update { it.copy(score = it.score.plus(SCORE_INCREASE)) }
        } else {
            _gameState.update { it.copy(score = it.score.minus(SCORE_INCREASE)) }
        }
        isGameOver()
    }

    fun skipQuestion() {
        _gameState.update { it.copy(score = it.score.minus(SKIP_DECREASE)) }
        isGameOver()
    }

    fun checkHint() {
        _gameState.update { it.copy(hintClick = _gameState.value.hintClick + 1) }
        if (_gameState.value.hintClick == 1) {
            _gameState.update { it.copy(score = it.score.minus(GameConstants.HINT_DECREASE)) }
        }
    }

    private fun resetGame() {
        playedWords.clear()
        _gameState.value = GameUIState()
    }

    val gameState = _gameState
        .map { state ->
            val allWords = history.value
            val correctWordSize = state.dictionary.sentence.length
            val guessWordSize = _guess.value.length
            val dictionary = allWords.random()
            playedWords.add(dictionary)
            allWords.minus(playedWords)

            GameUIState(
                dictionary = dictionary,
                scrambledWord = dictionary.sentence.scramble(),
                wordCount = playedWords.size,
                btnEnabled = correctWordSize != guessWordSize,
                isGameOver = state.wordCount == GameConstants.MAX_WORDS,
            )
        }
        .flowOn(Dispatchers.Default)
        .stateIn(
            viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = GameUIState()
        )
}*/