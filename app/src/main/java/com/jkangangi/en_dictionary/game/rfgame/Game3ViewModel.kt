/*
package com.jkangangi.en_dictionary.game.rfgame

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jkangangi.en_dictionary.app.data.local.DictionaryEntity
import com.jkangangi.en_dictionary.app.data.repository.DictionaryRepositoryImpl
import com.jkangangi.en_dictionary.app.util.scramble
import com.jkangangi.en_dictionary.game.GameConstants
import com.jkangangi.en_dictionary.game.GameUIState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.persistentSetOf
import kotlinx.collections.immutable.toPersistentList
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class Game3ViewModel @Inject constructor(repositoryImpl: DictionaryRepositoryImpl): ViewModel() {

    private var unscrambledWord: DictionaryEntity? = null
    private val allHistoryItems = repositoryImpl.getAllHistory()
        .map { entities -> entities.toPersistentList() }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = persistentListOf()
        )

    //val guess = _guess.asStateFlow()
    private val _score = MutableStateFlow(0)

    // val score = _score.asStateFlow()
    private val _hintClick = MutableStateFlow(0)
    //val hintClick = _hintClick.asStateFlow()


    private val playedWords = MutableStateFlow(persistentSetOf<DictionaryEntity>())
    private val _guess = MutableStateFlow("")

    private val _gameUIState = MutableStateFlow(GameUIState())
    val gameUIState = _gameUIState.asStateFlow()

    init {
        _gameUIState.update { it.copy(isEmpty = allHistoryItems.value.isEmpty()) }
        getWord()
    }

    */
/**
     * find a random word
     * add the random word to PlayedWords
     * remove random word from the 'AllWords'
     * scramble the word and assign it to a new var
     * return scrambled word
     *//*

    private fun getWord() {
        unscrambledWord = allHistoryItems.value.random() //correctWord
        playedWords.update { it.add(unscrambledWord!!) }
        allHistoryItems.value.remove(unscrambledWord!!)
        _gameUIState.update { it.copy(scrambledWord = unscrambledWord?.sentence?.scramble() ?: "") }
    }

    private fun resetWord() {
        _gameUIState.update { it.copy(scrambledWord = "", guessedAns = "") }
    }

    fun resetGame() {
        playedWords.update { it.removeAll(playedWords.value) }
        _gameUIState.update { GameUIState() }
        getWord()
        hasError()
        wordCount()
    }


    val gameState = combine(
        flow = repositoryImpl.getAllHistory(),
        flow2 = _score,
        flow3 = _hintClick,
        flow4 = playedWords,
        transform = { allWords, score, hints, played ->
            val entity = allWords.random()
            played.add(entity)
            allWords.minus(playedWords)

            GameUIState(
                isEmpty = allWords.isEmpty(),
                hint = entity.items[0].definitions[0].definition,
                scrambledWord = entity.sentence.scramble(),
                wordCount = played.size,
                score = score,
                hintClick = hints,
                btnEnabled = entity.sentence.length == _guess.value.length,
                isGameOver = played.size == GameConstants.MAX_WORDS
            )
        }
    ).stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5_000),
        initialValue = GameUIState()
    )

    private fun isGameOver() {
        if (playedWords.value.size == GameConstants.MAX_WORDS) {
            _gameUIState.update { it.copy(isGameOver = true) }
        }
        else {
            resetWord()
            hasError()
            wordCount()
        }
    }

    fun onNextClicked() {
        if ()
    }

    fun updateInput(input: String) {
        _guess.update { input }
        Log.i("GAME VM", "GameState = ${gameState.value.wordCount}")
    }

    fun onNextClicked() {
        if (_guess.value == gameState.value.correctWord) {
            _score.update { it.plus(GameConstants.SCORE_INCREASE) }
        }
    }

    fun skipQuestion() {
        _score.update { it.minus(GameConstants.SKIP_DECREASE) }
    }

    fun checkHint() {
        _hintClick.update { it + 1 }
        if (gameState.value.hintClick == 1) {
            _score.update { it.minus(GameConstants.HINT_DECREASE) }
        }
    }

    fun resetGame() {
        playedWords.value.clear()
        // gameState.value = GameUIState()
    }


}*/
