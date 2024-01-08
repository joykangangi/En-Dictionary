package com.jkangangi.en_dictionary.game

import android.util.Log
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.bumble.appyx.core.modality.BuildContext
import com.bumble.appyx.core.node.Node
import com.bumble.appyx.navmodel.backstack.BackStack
import com.jkangangi.en_dictionary.app.navigation.Route

class GameRoute(
    buildContext: BuildContext,
    private val backStack: BackStack<Route>
) : Node(buildContext = buildContext) {

    @Composable
    override fun View(
        modifier: Modifier
    ) {
        GameScreenView(modifier = modifier)

    }

    @Composable
    fun GameScreenView(
        modifier: Modifier,
        viewModel2: GameViewModel2 = hiltViewModel()
    ) {

        val gameState by viewModel2.gameState.collectAsState()

        Log.i("GameRoute", "IsEmpty = ${gameState.isEmpty}")

        GameScreen(
            modifier = modifier,
            state = gameState,
            guess = viewModel2.guess.collectAsState().value,
            onGuessChanged = viewModel2::updateInput,
            onNextClicked = viewModel2::onNextClicked,
            onSkipClicked = viewModel2::skipQuestion,
            onHintClicked = viewModel2::checkHint
        )

    }

}