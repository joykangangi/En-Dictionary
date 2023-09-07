package com.jkangangi.en_dictionary.game

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jkangangi.en_dictionary.app.data.local.DictionaryEntity
import com.jkangangi.en_dictionary.app.data.repository.DictionaryRepositoryImpl
import com.jkangangi.en_dictionary.app.util.scramble
import com.jkangangi.en_dictionary.game.GameConstants.MAX_WORDS
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.collections.immutable.persistentSetOf
import kotlinx.collections.immutable.toPersistentSet
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class GameViewModel2 @Inject constructor(repositoryImpl: DictionaryRepositoryImpl) : ViewModel() {

    private val playedWords = MutableStateFlow(persistentSetOf<DictionaryEntity>())
    private val _guess = MutableStateFlow("")
    //val guess = _guess.asStateFlow()
    private val _score = MutableStateFlow(0)
   // val score = _score.asStateFlow()
    private val _hintClick = MutableStateFlow(0)
    //val hintClick = _hintClick.asStateFlow()

    val gameState = combine(
        flow = repositoryImpl.getAllHistory(),
        flow2 = _guess,
        flow3 = _score,
        flow4 = _hintClick,
        flow5 = playedWords,
        transform = { allWords, guessedAns, score, hints, played ->
            val entity = allWords.random()
            played.add(entity)
            allWords.minus(playedWords)

            GameUIState(
                dictionaries = allWords.toPersistentSet(),
                correctWord = entity.sentence,
                hint = entity.items[0].definitions[0].definition,
                scrambledWord = entity.sentence.scramble(),
                guess = guessedAns,
                wordCount = played.size,
                score = score,
                hintClick = hints,
                btnEnabled = entity.sentence.length == _guess.value.length,
                isGameOver = played.size == MAX_WORDS
            )
        }
    ).stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5_000),
        initialValue = GameUIState()
    )

    fun updateInput(input: String) {
        _guess.update { input }
        Log.i("GAME VM", "GameState = ${gameState.value.wordCount}")
    }

    fun onNextClicked() {
        if ( _guess.value == gameState.value.correctWord) {
            _score.update {  it.plus(GameConstants.SCORE_INCREASE) }
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


}