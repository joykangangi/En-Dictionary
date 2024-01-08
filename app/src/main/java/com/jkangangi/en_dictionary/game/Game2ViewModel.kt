package com.jkangangi.en_dictionary.game

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jkangangi.en_dictionary.app.data.local.DictionaryEntity
import com.jkangangi.en_dictionary.app.data.repository.DictionaryRepositoryImpl
import com.jkangangi.en_dictionary.app.util.scramble
import com.jkangangi.en_dictionary.game.GameConstants.MAX_WORDS
import com.jkangangi.en_dictionary.game.GameConstants.SCORE_INCREASE
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.persistentSetOf
import kotlinx.collections.immutable.toPersistentList
import kotlinx.collections.immutable.toPersistentSet
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class GameViewModel2 @Inject constructor(
    repositoryImpl: DictionaryRepositoryImpl
) : ViewModel() {

    private var unscrambledWord: DictionaryEntity? = null

    private val _guess = MutableStateFlow("")
    val guess = _guess.asStateFlow()
    private val _score = MutableStateFlow(0)
    private val _hintClick = MutableStateFlow(0)
    private val playedWords = MutableStateFlow(persistentSetOf<DictionaryEntity>())


    val gameState = combine(
        flow = _score,
        flow2 = _hintClick,
        flow3 = playedWords,
        flow4 = repositoryImpl.getAllHistory(),
        transform = { score, hints, played, allHistoryItems->
            unscrambledWord = allHistoryItems.random()
            played.add(unscrambledWord!!)
            allHistoryItems.minus(unscrambledWord!!) //diff btwn minus & remove

            GameUIState(
                isEmpty = allHistoryItems.isEmpty(),
                hint = unscrambledWord?.items?.get(0)?.definitions?.get(0)?.definition ?: "",
                scrambledWord = unscrambledWord?.sentence?.scramble()?:"",
                wordCount = played.size,
                score = score,
                hintClick = hints,
                btnEnabled = unscrambledWord?.sentence?.length == _guess.value.length,
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
        Log.i("GAME VM", "GameState = ${playedWords.value.size}")
    }

    fun onNextClicked() {
        if (_guess.value == unscrambledWord?.sentence) {
            _score.update { it.plus(SCORE_INCREASE) }
        }
        else{
            _score.update { it.minus(SCORE_INCREASE) }
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