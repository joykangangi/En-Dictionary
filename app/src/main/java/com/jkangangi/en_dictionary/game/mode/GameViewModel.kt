package com.jkangangi.en_dictionary.game.mode

import android.util.Log
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
import com.jkangangi.en_dictionary.game.mode.model.GameMode
import com.jkangangi.en_dictionary.game.mode.model.GameSummaryStats
import com.jkangangi.en_dictionary.game.util.GameConstants.EXCELLENT_SCORE
import com.jkangangi.en_dictionary.game.util.GameConstants.HINT_DECREASE
import com.jkangangi.en_dictionary.game.util.GameConstants.LETTER_INCREASE
import com.jkangangi.en_dictionary.game.util.GameConstants.MAX_WORDS
import com.jkangangi.en_dictionary.game.util.GameConstants.SCORE_INCREASE
import com.jkangangi.en_dictionary.game.util.GameConstants.SKIP_DECREASE
import com.jkangangi.en_dictionary.game.util.GameConstants.TOTAL_WORD_TIME
import kotlinx.collections.immutable.PersistentList
import kotlinx.collections.immutable.toPersistentList
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlin.math.roundToInt

/**
 * The app will use searched words (cached in db) to play.
 *
 */

class GameViewModel(private val repository: DictionaryRepository) : ViewModel() {
    private val _guessedWord = mutableStateOf("")
    val guessedWord: State<String> = _guessedWord

    private var _hintClicked by mutableStateOf(false)
    private val _gameInputState = MutableStateFlow(GameInputState())
    private val allWordItems = mutableSetOf<DictionaryEntity>()
    private val playedWords = mutableSetOf<DictionaryEntity>()
    private val _gameStatsState = MutableStateFlow(
        GameSummaryStats(
            isExcellent = false,
            percentageScore = 0,
            totalGameTime = 0,
            totalPoints = 0
        )
    )
    val gameSummaryStats = _gameStatsState.asStateFlow()

    val gameTimeTotal = mutableIntStateOf(0)
    var percent = mutableIntStateOf(0)


    private fun getAllWordItems() = repository.getAllHistory().map { items ->
        items.filter { it.sentence.isWord() && it.sentence.length > 1 }
    }

    private fun resolveGameModeItems(gameMode: GameMode): Flow<PersistentList<DictionaryEntity>> {
        return getAllWordItems().map { wordItems ->
            wordItems.filter { it.sentence.length in gameMode.wordLength }.toPersistentList()
        }
    }

    private val _currentGameMode = MutableStateFlow<GameMode?>(null)
    val currentMode = _currentGameMode.asStateFlow()

    fun setGameMode(gameMode: GameMode) {
        _currentGameMode.value = gameMode
    }

    fun calculateFinalResults() {
        viewModelScope.launch {
            val totalScore = _gameInputState.value.score
            val totalGameTime = _gameStatsState.value.totalGameTime
            val passMark = _currentGameMode.value?.passMark

            val numerator = totalScore + (passMark ?: 0)
            var perc = ((numerator.toDouble() / totalGameTime) * 100).roundToInt()

            Log.i(
                "GameVM",
                "score = $totalScore, time=${totalGameTime}, pass = $perc"
            )

            if (perc > 100) perc = 100

            gameTimeTotal.intValue = totalGameTime
            percent.intValue = perc

            _gameStatsState.update {
                it.copy(
                    totalGameTime = totalGameTime,
                    totalPoints = _gameInputState.value.score,
                    percentageScore = perc,
                    isExcellent = perc >= EXCELLENT_SCORE
                )
            }
        }
    }

    val gameInputState = combine(
        flow = resolveGameModeItems(_currentGameMode.value ?: GameMode.Easy),
        flow2 = _gameInputState,
        transform = { modeWordItems, state ->

            if (allWordItems.isEmpty()) {
                allWordItems.addAll(modeWordItems)
            }

            GameInputState(
                wordItemsSize = modeWordItems.size,
                wordItem = state.wordItem,
                scrambledWord = state.scrambledWord,
                hint = state.hint,
                wordCount = state.wordCount,
                btnEnabled = state.btnEnabled,
                score = state.score,
                showHint = state.showHint,
                timeLeft = state.timeLeft,
                isGameOver = playedWords.size == MAX_WORDS,
                isGuessCorrect = state.isGuessCorrect
            )
        }
    ).stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = GameInputState()
    )

    private val _gameUIState = MutableStateFlow<GameUIState>(GameUIState.WordLoading)

    fun gameUIState(): StateFlow<GameUIState> {
        when {
            gameInputState.value.wordItemsSize < MAX_WORDS -> {
                _gameUIState.update { GameUIState.SmallWordCount }
            }

            gameInputState.value.wordItemsSize >= MAX_WORDS && allWordItems.isEmpty() -> {
                _gameUIState.update { GameUIState.WordsNotFound }
            }

            gameInputState.value.wordItemsSize >= MAX_WORDS && allWordItems.isNotEmpty() -> {
                _gameUIState.update { GameUIState.WordsFound }
            }

            else -> {
                _gameUIState.update { GameUIState.WordLoading }
            }
        }
        return _gameUIState
    }

    fun getWordItem() {

        val wordItem = allWordItems.random()
        allWordItems.remove(wordItem)
        playedWords.add(wordItem)

        _gameInputState.update {
            it.copy(
                wordItem = wordItem,
                scrambledWord = wordItem.sentence.scramble(),
                hint = wordItem.items[0].definitions[0].definition,
                wordCount = playedWords.size
            )
        }
        startTimer()
    }

    private fun startTimer() {
        viewModelScope.launch {

            while (_gameInputState.value.timeLeft > 0) {
                delay(1000)
                _gameInputState.update { it.copy(timeLeft = _gameInputState.value.timeLeft - 1) }
            }
        }
    }

    fun autoSkip() {
        onSkipClicked()
        _gameStatsState.value = _gameStatsState.value.copy(
            totalGameTime = _gameStatsState.value.totalGameTime + TOTAL_WORD_TIME
        )
    }

    fun resetWord() {
        _guessedWord.value = ""
        val timeOnWord = TOTAL_WORD_TIME - _gameInputState.value.timeLeft
        _gameStatsState.value = _gameStatsState.value.copy(
            totalGameTime = _gameStatsState.value.totalGameTime + timeOnWord
        )
        _gameInputState.update {
            it.copy(
                showHint = false,
                btnEnabled = false,
                timeLeft = TOTAL_WORD_TIME,
                isGuessCorrect = false
            )
        }
        _hintClicked = false
        getWordItem()
    }

    fun updateInput(userInput: String) {
        _guessedWord.value = userInput
        _gameInputState.update { it.copy(btnEnabled = _guessedWord.value.length == _gameInputState.value.wordItem?.sentence?.length) }

    }


    /**
     * Correct placement - Easy mode
     * 2 points for correct letter placement
     */
    private fun getCorrectPlacements(guess: String): Int {
        val correctWord = _gameInputState.value.wordItem?.sentence
        var correctPlacement = 0

        correctWord?.forEachIndexed { index, char ->
            if (guess[index] == char) correctPlacement++
        }
        return correctPlacement
    }


    fun onSubmitClicked(mode: GameMode) {
        _gameInputState.update {
            it.copy(
                isGuessCorrect = _guessedWord.value.trim()
                    .equals(_gameInputState.value.wordItem?.sentence, ignoreCase = true)
            )
        }

        viewModelScope.launch {

            _gameInputState.update {
                it.copy(
                    score =
                    when (mode) {
                        GameMode.Hard -> TODO()
                        GameMode.Medium -> {
                            if (_gameInputState.value.isGuessCorrect) _gameInputState.value.score + SCORE_INCREASE else _gameInputState.value.score - SCORE_INCREASE

                        }

                        GameMode.Easy -> {
                            _gameInputState.value.score + getCorrectPlacements(_guessedWord.value.trim()) * LETTER_INCREASE
                        }
                    }
                )
            }
        }
    }

    fun onSkipClicked() {
        _gameInputState.update { it.copy(score = _gameInputState.value.score - SKIP_DECREASE) }
    }

    fun onHintClicked() {
        if (!_hintClicked) {
            _gameInputState.update { it.copy(score = _gameInputState.value.score - HINT_DECREASE) }
            _hintClicked = true
        }

        _gameInputState.update { it.copy(showHint = !_gameInputState.value.showHint) }


    }

    fun resetGame() {
        playedWords.removeAll(playedWords)
        _gameInputState.update { GameInputState() }

    }

}