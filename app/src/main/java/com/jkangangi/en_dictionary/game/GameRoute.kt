package com.jkangangi.en_dictionary.game

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.bumble.appyx.core.modality.BuildContext
import com.bumble.appyx.core.node.Node
import com.jkangangi.en_dictionary.app.util.DictionaryViewModelFactory

class GameRoute(
    buildContext: BuildContext,
) : Node(buildContext = buildContext) {

    @Composable
    override fun View(
        modifier: Modifier
    ) {
        val gameMode: MutableState<GameMode?> = remember {
            mutableStateOf(
                null
            )
        }
        GameIntroScreen(
            onGameModeClick = { gameMode.value = it }
        )

        gameMode.value?.let { SelectGameMode(gameMode = it, modifier = modifier) }

    }

    @Composable
    fun SelectGameMode(
        gameMode: GameMode,
        modifier: Modifier,
    ) {
        when (gameMode) {
            GameMode.Hard -> {
                HardGameView(modifier = modifier)
            }

            GameMode.Medium -> {
                MediumGameView(
                    modifier = modifier,
                )
            }

            GameMode.Easy -> {
                EasyGameView(
                    modifier = modifier,
                )
            }
        }
    }

    @Composable
    fun EasyGameView(
        modifier: Modifier,
        viewModel: GameViewModel = viewModel(factory = DictionaryViewModelFactory)
    ) {
        Box(modifier = modifier.fillMaxSize()) {

        }
    }


    @Composable
    fun MediumGameView(
        modifier: Modifier,
        viewModel: GameViewModel = viewModel(factory = DictionaryViewModelFactory)
    ) {

        val gameState by viewModel.gameUIState.collectAsState()


        LaunchedEffect(
            key1 = gameState.wordItemsSize > GameConstants.MAX_WORDS - 1,
            block = {
                if (gameState.wordCount == 0 && gameState.wordItemsSize > GameConstants.MAX_WORDS - 1) {
                    viewModel.getWordItem()
                }
            }
        )

        GameScreen(
            modifier = modifier,
            state = gameState,
            guess = viewModel.guessedWord.value,
            onGuessChanged = viewModel::updateInput,
            onNextClicked = viewModel::onNextClicked,
            onSkipClicked = viewModel::onSkipClicked,
            onHintClicked = viewModel::onHintClicked,
        )
    }

    @Composable
    fun HardGameView(
        modifier: Modifier,
        viewModel: GameViewModel = viewModel(factory = DictionaryViewModelFactory)
    ) {
        Box(modifier = modifier.fillMaxSize()) {

        }
    }

}