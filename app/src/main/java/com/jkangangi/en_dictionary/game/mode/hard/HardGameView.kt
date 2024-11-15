package com.jkangangi.en_dictionary.game.mode.hard

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.lifecycle.viewmodel.compose.viewModel
import com.jkangangi.en_dictionary.R
import com.jkangangi.en_dictionary.app.theme.dimens
import com.jkangangi.en_dictionary.app.theme.largeSpacer
import com.jkangangi.en_dictionary.app.theme.mediumPadding
import com.jkangangi.en_dictionary.app.theme.mediumSpacer
import com.jkangangi.en_dictionary.app.util.DictionaryViewModelFactory
import com.jkangangi.en_dictionary.app.util.HtmlParser
import com.jkangangi.en_dictionary.app.widgets.SpeakerIcon
import com.jkangangi.en_dictionary.game.mode.GameInputState
import com.jkangangi.en_dictionary.game.mode.GameUIState
import com.jkangangi.en_dictionary.game.mode.GameViewModel
import com.jkangangi.en_dictionary.game.mode.model.GameMode
import com.jkangangi.en_dictionary.game.mode.model.GameSummaryStats
import com.jkangangi.en_dictionary.game.mode.sharedwidgets.ButtonRowSection
import com.jkangangi.en_dictionary.game.mode.sharedwidgets.CorrectAnsDialog
import com.jkangangi.en_dictionary.game.mode.sharedwidgets.GameBoxInput
import com.jkangangi.en_dictionary.game.mode.sharedwidgets.GameTimer
import com.jkangangi.en_dictionary.game.mode.sharedwidgets.GeneralGameView
import com.jkangangi.en_dictionary.game.mode.sharedwidgets.HintSection
import com.jkangangi.en_dictionary.game.mode.sharedwidgets.WrongAnsDialog
import com.jkangangi.en_dictionary.game.util.GameConstants.EXCELLENT_SCORE
import com.jkangangi.en_dictionary.game.util.GameConstants.MAX_WORDS

@Composable
fun HardGameView(
    viewResultsDialog: (GameSummaryStats) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: GameViewModel = viewModel(factory = DictionaryViewModelFactory)
) {

    val gameState by viewModel.gameInputState.collectAsState()
    val gameUIState by viewModel.gameUIState().collectAsState()
    val guess by viewModel.guessedWord
    //val gameSummaryStats by viewModel.gameSummaryStats.collectAsState() //update issue

    val showDialog = remember {
        mutableStateOf(false)
    }

    val showSummaryStats =
        {
            val stats = GameSummaryStats(
                totalGameTime = viewModel.gameTimeTotal.intValue,
                totalPoints = gameState.score,
                percentageScore = viewModel.percent.intValue,
                isExcellent = viewModel.percent.intValue >= EXCELLENT_SCORE
            )
            //Log.i("Med","Stats = $gameSummaryStats")
            viewResultsDialog(stats)
        }


    //only runs for the first word
    LaunchedEffect(
        key1 = gameState.wordItemsSize > MAX_WORDS - 1,
        block = {
            if (gameState.wordCount == 0 && gameState.wordItemsSize > MAX_WORDS - 1) {
                viewModel.setGameMode(GameMode.Hard)
                viewModel.getWordItem()
            }
        }
    )

    LaunchedEffect(
        key1 = gameState.timeLeft,
        block = {
            if (gameState.timeLeft == 0) {
                viewModel.autoSkip()
                showDialog.value = true
            }
        }
    )

    DisposableEffect(key1 = Unit, effect = {
        onDispose {
            viewModel.clearSoundResources()
        }
    })

    if (gameState.isGuessCorrect && showDialog.value) {
        CorrectAnsDialog(
            isGameOver = gameState.isGameOver,
            goToNextWord = {
                showDialog.value = false
                viewModel.resetWord()
            },
            viewGameResults = {
                showDialog.value = false
                viewModel.calculateFinalResults()
                showSummaryStats()
            }
        )
    }

    if (!gameState.isGuessCorrect && showDialog.value) {
        WrongAnsDialog(
            correctWord = gameState.wordItem?.sentence ?: "",
            meaning = (HtmlParser.htmlToString(gameState.hint)).toString(),
            isFinalWord = gameState.wordCount == MAX_WORDS -1,
            isGameOver = gameState.isGameOver,
            viewGameResults = {
                showDialog.value = false
                viewModel.calculateFinalResults()
                showSummaryStats()
            },
            goToNextWord = {
                showDialog.value = false
                viewModel.resetWord()
            }
        )
    }

    val onSubmitClick = remember {
        {
            viewModel.onSubmitAnsClicked(GameMode.Hard)
            showDialog.value = true
        }
    }

    val context = LocalContext.current

    val onSpeakerClicked = remember {
        {
            viewModel.onSpeakerClick(context, dictionary = gameState.wordItem)
        }
    }

    HardGameScreen(
        modifier = modifier,
        state = gameState,
        guess = guess,
        onGuessChanged = viewModel::updateInput,
        onNextClicked = onSubmitClick,
        onSpeakerClicked = onSpeakerClicked,
        onSkipClicked = {
            viewModel.onSkipClicked()
            showDialog.value = true
        },
        onHintClicked = viewModel::onHintClicked,
        gameUIState = gameUIState,
    )
}


@Composable
fun HardGameScreen(
    modifier: Modifier,
    state: GameInputState,
    guess: String,
    gameUIState: GameUIState,
    onSpeakerClicked: () -> Unit,
    onGuessChanged: (String) -> Unit,
    onNextClicked: () -> Unit,
    onSkipClicked: () -> Unit,
    onHintClicked: () -> Unit,
) {

    GeneralGameView(
        modifier = modifier,
        gameInputState = state,
        currentMode = GameMode.Hard,
        gameUIState = gameUIState,
        gameLayout = {
            HardGameCard(
                modifier = Modifier.padding(mediumPadding()),
                quizWord = state.scrambledWord,
                hint = state.hint,
                guess = guess,
                onGuessChanged = onGuessChanged,
                onHintClicked = onHintClicked,
                showHint = state.showHint,
                timeLeft = state.timeLeft,
                onSpeakerClicked = onSpeakerClicked
            )

            ButtonRowSection(
                onSkipClicked = onSkipClicked,
                onNextClicked = onNextClicked,
                btnEnabled = state.btnEnabled,
            )

        }
    )
}

@Composable
private fun HardGameCard(
    modifier: Modifier,
    onSpeakerClicked: () -> Unit,
    quizWord: String,
    hint: String,
    guess: String,
    onGuessChanged: (String) -> Unit,
    onHintClicked: () -> Unit,
    showHint: Boolean,
    timeLeft: Int,
) {

    ElevatedCard(
        content = {
            Column(
                modifier = modifier,
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
                content = {

                    Text(
                        text = stringResource(id = R.string.hard_game_instructions),
                        style = MaterialTheme.typography.bodyMedium,
                        textAlign = TextAlign.Center
                    )

                    GameTimer(
                        timeLeft = timeLeft
                    )

                    SpeakerIcon(
                        onSpeakerClick = onSpeakerClicked,
                        speakerSize = MaterialTheme.dimens.xLObjects
                    )

                    Spacer(Modifier.size(mediumSpacer()))

                    Text(
                        text = stringResource(id = R.string.hard_game_txt_label),
                        style = MaterialTheme.typography.bodyLarge
                    )

                    Spacer(Modifier.size(mediumSpacer()))

                    GameBoxInput(
                        input = guess,
                        onInputChanged = onGuessChanged,
                        wordSize = quizWord.length
                    )


                    Spacer(modifier = Modifier.size(largeSpacer()))

                    HintSection(hint = hint, onHintClicked = onHintClicked, showHint = showHint)
                },
            )
        }
    )
}
