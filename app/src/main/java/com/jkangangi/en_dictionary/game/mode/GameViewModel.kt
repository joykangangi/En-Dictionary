package com.jkangangi.en_dictionary.game.mode

import android.content.Context
import android.media.AudioAttributes
import android.media.MediaPlayer
import android.net.Uri
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
import com.jkangangi.en_dictionary.definitions.AUDIO_BASE_URL
import com.jkangangi.en_dictionary.game.mode.model.GameMode
import com.jkangangi.en_dictionary.game.mode.model.GameModeDetails
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
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.IOException
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

    private val _currentGameMode: MutableStateFlow<GameMode> = MutableStateFlow(GameMode.Easy)

    private val _gameModeDetails = _currentGameMode.map { mode ->
        when (mode) {
            GameMode.Easy -> GameModeDetails.Easy
            GameMode.Medium -> GameModeDetails.Medium
            GameMode.Hard -> GameModeDetails.Hard
        }
    }

    fun getGameMode(mode: GameMode) {
        _currentGameMode.update { mode }
    }


    private fun getAllWordItems() = repository.getAllHistory().map { items ->
        items.filter { it.sentence.isWord() && it.sentence.length > 1 }
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    private fun resolveGameModeItems(): Flow<PersistentList<DictionaryEntity>> {

        return _gameModeDetails.flatMapLatest { modeDetails ->
            getAllWordItems().map { wordItems ->
                when (modeDetails) {
                    GameModeDetails.Easy -> {
                        wordItems.filter { it.sentence.length in GameModeDetails.Easy.wordLength }
                    }

                    GameModeDetails.Medium -> {
                        wordItems.filter { it.sentence.length in GameModeDetails.Medium.wordLength }
                    }

                    GameModeDetails.Hard -> {
                        wordItems.filter { dictionary ->
                            val entries = dictionary.pronunciations.getOrNull(0)?.entries
                            val hasAudio = entries?.any { entry -> entry.audioFiles.isNotEmpty() }
                            dictionary.sentence.length in GameModeDetails.Hard.wordLength && hasAudio == true
                        }
                    }
                }.toPersistentList()
            }
        }
    }

    fun calculateFinalResults() {
        viewModelScope.launch {
            val totalScore = _gameInputState.value.score
            val totalGameTime = _gameStatsState.value.totalGameTime
            val passMark = _gameModeDetails.first().passMark

            val numerator = totalScore + passMark
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
        flow = resolveGameModeItems(),
        flow2 = _gameInputState,
        transform = { modeWordItems, state ->

            if (allWordItems.isEmpty()) {
                allWordItems.addAll(modeWordItems)
                Log.i("Game vm", "items=$modeWordItems")
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
            Log.i("Game VM", "TIME")

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


    fun onSubmitAnsClicked() {
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
                    when (_currentGameMode.value) {
                        GameMode.Hard -> {
                            if (_gameInputState.value.isGuessCorrect) _gameInputState.value.score + SCORE_INCREASE else _gameInputState.value.score - SCORE_INCREASE

                        }

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

    private var mediaPlayer: MediaPlayer? = null

    /**
     * Handle sound clicks for a word
     */
    fun onSpeakerClick(context: Context, dictionary: DictionaryEntity?) {
        if (mediaPlayer == null) {
            mediaPlayer = MediaPlayer()
        }
        mediaPlayer?.let { player ->
            viewModelScope.launch {
                withContext(Dispatchers.IO) {
                    val audioURL = dictionary?.let { getAudioLink(word = it) }

                    player.reset() //Reset MediaPlayer to idle state

                    player.setAudioAttributes(
                        AudioAttributes.Builder()
                            .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                            .setUsage(AudioAttributes.USAGE_MEDIA)
                            .build()
                    )

                    try {
                        player.setDataSource(context, Uri.parse(audioURL))
                        player.prepareAsync()
                        player.setOnPreparedListener { mPlayer ->
                            mPlayer.start()
                        }
                    } catch (e: IOException) {
                        e.printStackTrace()
                    }
                }
            }
        }
    }


    private fun getAudioLink(word: DictionaryEntity): String {
        val entries = word.pronunciations[0].entries
        val audioFile = entries[0].audioFiles[0].link
        return AUDIO_BASE_URL + audioFile
    }

    fun clearSoundResources() {
        Log.i("DefinitionVM", "ClearedSoundResources")
        mediaPlayer?.release()
        mediaPlayer = null
    }

}
