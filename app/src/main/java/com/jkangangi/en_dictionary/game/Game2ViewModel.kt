package com.jkangangi.en_dictionary.game

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jkangangi.en_dictionary.app.data.local.DictionaryEntity
import com.jkangangi.en_dictionary.app.data.repository.DictionaryRepositoryImpl
import com.jkangangi.en_dictionary.app.util.scramble
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.collections.immutable.minus
import kotlinx.collections.immutable.persistentSetOf
import kotlinx.collections.immutable.toPersistentSet
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class GameViewModel2 @Inject constructor(repositoryImpl: DictionaryRepositoryImpl) : ViewModel() {

   private val history = repositoryImpl.getAllHistory()
        .map { entities -> entities.toPersistentSet() }
        .stateIn( //pipe flow->stateflow
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = persistentSetOf()
        )
    private var correctWord: String? = null
    private val _guess = MutableStateFlow("")
    val guess = _guess.asStateFlow()
    val allWords = history.value
    private val playedWords = persistentSetOf<DictionaryEntity>()
    private val _gameState = MutableStateFlow(GameUIState())

    fun updateInput(input: String) {
        _guess.update { input }
    }

    val gameState = _gameState
        .map { state ->
            correctWord = state.dictionary.sentence
            val correctWordSize = correctWord?.length
            val guessWordSize = _guess.value.length

            GameUIState(
                dictionary = allWords.random(),
                wordCount = playedWords.size,
                btnEnabled = correctWordSize != guessWordSize,
                isGameOver = state.wordCount == GameConstants.MAX_WORDS,
            )
            playedWords.add(state.dictionary)
            allWords.minus(playedWords)
            Log.i("GAME VM", "GameState = ${_gameState.value}")
        }
        .flowOn(Dispatchers.Default)
        .stateIn(
            viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = GameUIState()
        )

     fun onNextClicked() {
        if ( _guess.value == correctWord) {
            _gameState.update { it.copy(score = it.score.plus(GameConstants.SCORE_INCREASE)) }
        } else {
            _gameState.update { it.copy(score = it.score.minus(GameConstants.SCORE_INCREASE)) }
        }
    }

    fun skipQuestion() {
        _gameState.update { it.copy(score = it.score.minus(GameConstants.SKIP_DECREASE)) }
    }

    fun checkHint() {
        _gameState.update { it.copy(hintClick = _gameState.value.hintClick+1) }
        if (_gameState.value.hintClick == 1) {
            _gameState.update { it.copy(score = it.score.minus(GameConstants.HINT_DECREASE)) }
        }
    }

    fun resetGame() {
        playedWords.clear()
        _gameState.value = GameUIState()
    }

}