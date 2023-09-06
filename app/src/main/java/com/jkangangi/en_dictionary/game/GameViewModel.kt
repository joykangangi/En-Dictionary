package com.jkangangi.en_dictionary.game

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jkangangi.en_dictionary.app.data.local.DictionaryEntity
import com.jkangangi.en_dictionary.app.data.repository.DictionaryRepositoryImpl
import com.jkangangi.en_dictionary.app.util.scramble
import com.jkangangi.en_dictionary.game.GameConstants.HINT_DECREASE
import com.jkangangi.en_dictionary.game.GameConstants.MAX_WORDS
import com.jkangangi.en_dictionary.game.GameConstants.SCORE_INCREASE
import com.jkangangi.en_dictionary.game.GameConstants.SKIP_DECREASE
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.collections.immutable.minus
import kotlinx.collections.immutable.persistentSetOf
import kotlinx.collections.immutable.toPersistentSet
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import javax.inject.Inject

/**
 * The app will use searched words (cached in db).
 *
 */

/*@HiltViewModel
class GameViewModel @Inject constructor(repositoryImpl: DictionaryRepositoryImpl) : ViewModel() {

    val history = repositoryImpl.getAllHistory()
        .map { entities -> entities.toPersistentSet() }
        .stateIn( //pipe flow->stateflow
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = persistentSetOf()
        )


    private val _gameState = MutableStateFlow(GameUIState())
    private val playedWords = persistentSetOf<DictionaryEntity>()

    // val gameState = _gameState.asStateFlow()
    private val allWords = history.value
    private var currentWord: String? = null


    private fun getWord() {
        val allWords = history.value
        val currentDictionary = allWords.random()
        currentWord = currentDictionary.sentence
        _gameState.update { it.copy(scrambledWord = currentWord.scramble()) }
        _gameState.update { it.copy(hint = currentDictionary.items[0].definitions[0].definition) }
        playedWords.add(currentDictionary)
        allWords.minus(playedWords)
        _gameState.update { it.copy(wordCount = playedWords.size) }
        //return currentWord.scramble()
    }

    /*private fun hasError() {
        val wordSize = _gameState.value.scrambledWord.length
        val answerWordSize = _gameState.value.guessedAns.length

        if (wordSize != answerWordSize) {
            _gameState.update { it.copy(btnEnabled = false) }
        } else _gameState.update { it.copy(btnEnabled = true) }

    }

    private fun updateAnswer(input: String) {
        _gameState.update { it.copy(guessedAns = input) }
        hasError()
    }

    fun checkAnswer() {
        if (_gameState.value.guessedAns == currentWord) {
            _gameState.update { it.copy(score = it.score.plus(SCORE_INCREASE)) }
        } else {
            _gameState.update { it.copy(score = it.score.minus(SCORE_INCREASE)) }
        }
    }*/

    fun skipQuestion() {
        _gameState.update { it.copy(score = it.score.minus(SKIP_DECREASE)) }
    }

    fun checkHint() {
        _gameState.update { it.copy(score = it.score.minus(HINT_DECREASE)) }
    }
    fun isGameOver() {
        if (_gameState.value.wordCount == MAX_WORDS) {
            _gameState.update { it.copy(isGameOver = true) }
        } else _gameState.update { it.copy(isGameOver = false) }
    }

    private fun resetGame() {
        playedWords.clear()
        _gameState.value = GameUIState()
        getWord()
    }


//    //Go to the next word
//    private fun resetWord() {
//        _gameState.update { it.copy(scrambledWord = "") }
//        _gameState.update { it.copy(hint = "") }
//        getWord()
//    }

}

 */
