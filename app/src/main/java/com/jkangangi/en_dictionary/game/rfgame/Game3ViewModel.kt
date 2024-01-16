package com.jkangangi.en_dictionary.game.rfgame/*
import android.util.Log
import androidx.lifecycle.viewModelScope
import com.jkangangi.en_dictionary.app.data.local.DictionaryEntity
import com.jkangangi.en_dictionary.app.util.scramble
import com.jkangangi.en_dictionary.game.GameConstants
import com.jkangangi.en_dictionary.game.GameUIState
import kotlinx.collections.immutable.persistentSetOf
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

private var unscrambledWord = MutableStateFlow<DictionaryEntity?>(null)

private val _guess = MutableStateFlow("")
val guess = _guess.asStateFlow()
private val _score = MutableStateFlow(0)
private val _hintClick = MutableStateFlow(0)
private val _wordCount = MutableStateFlow(0)
private val playedWords = MutableStateFlow(persistentSetOf<DictionaryEntity>())

private val allHistoryItems = repositoryImpl.getAllHistory().filter { dictionaryEntities -> dictionaryEntities.all { it.sentence.contains(" ") } }
//        .map { entities -> entities.toPersistentList() }
//        .stateIn(
//            scope = viewModelScope,
//            started = SharingStarted.WhileSubscribed(5_000),
//            initialValue = persistentListOf()
//        )

init {
    getWord()
}
private fun getWord() = viewModelScope.launch {
    unscrambledWord.update { allHistoryItems.map { it.random() }.first() }
    playedWords.map { it.add(unscrambledWord.first()!!) }
    allHistoryItems.map { it.minus(playedWords) }
    _wordCount.update { playedWords.first().size }
    Log.i("GAME VM", "size = ${playedWords.first().size}")
}

val gameState = combine(
    flow = _score,
    flow2 = _hintClick,
    flow3 = playedWords,
    flow4 = allHistoryItems,
    flow5 = _wordCount,
    transform = { score, hints, played, allHistoryItems, wordCount->
//            unscrambledWord = allHistoryItems.random()
//            played.add(unscrambledWord!!)
//            allHistoryItems.minus(unscrambledWord!!) //diff btwn minus & remove

        GameUIState(
            isEmpty = allHistoryItems.isEmpty(),
            hint = unscrambledWord.value?.items?.get(0)?.definitions?.get(0)?.definition ?:"",
            scrambledWord = unscrambledWord.value?.sentence?.scramble()?:"",
            wordCount = wordCount,
            score = score,
            hintClick = hints,
            nextEnabled = unscrambledWord.value?.sentence?.length == _guess.value.length,
            isGameOver = played.size == GameConstants.MAX_WORDS
        )
    }
).stateIn(
    viewModelScope,
    SharingStarted.WhileSubscribed(5_000),
    initialValue = GameUIState()
)


fun updateInput(input: String) {
    _guess.update { input }
    Log.i("GAME VM", "GameState1 = ${gameState.value.wordCount}")
    Log.i("GAME VM", "GameState2 = ${gameState.value}}")
    Log.i("GAME VM", "GameState3 = ${unscrambledWord.value}}")
}

fun onNextClicked() = viewModelScope.launch{
    if (_guess.value == (unscrambledWord.first()?.sentence ?: "")) {
        _score.update { it.plus(GameConstants.SCORE_INCREASE) }
    }
    else{
        _score.update { it.minus(GameConstants.SCORE_INCREASE) }
    }
    getWord()
    _guess.update { "" }
}

fun skipQuestion() {
    _score.update { it.minus(GameConstants.SKIP_DECREASE) }
    getWord()
    _guess.update { "" }
}

fun checkHint() {
    _hintClick.update { it + 1 }
    if (gameState.value.hintClick == 1) {
        _score.update { it.minus(GameConstants.HINT_DECREASE) }
    }
    Log.i("Game2VM", "checkHint = ${gameState.value}")
}

fun resetGame() {
    playedWords.value.clear()
    // gameState.value = GameUIState()
}*/
