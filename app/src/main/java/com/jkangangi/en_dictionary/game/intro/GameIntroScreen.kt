package com.jkangangi.en_dictionary.game.intro

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.jkangangi.en_dictionary.R
import com.jkangangi.en_dictionary.game.mode.model.GameMode

@Composable
fun GameIntroScreen(
    onGameModeClick: (GameMode) -> Unit,
    modifier: Modifier = Modifier
) {

    Scaffold(
        content = { scaffoldPadding ->
            Column(
                modifier = modifier
                    .fillMaxSize()
                    .padding(scaffoldPadding)
                    .padding(12.dp)
                    .verticalScroll(rememberScrollState()),
                verticalArrangement = Arrangement.spacedBy(20.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                content = {
                    Spacer(modifier = Modifier.height(20.dp))

                    Icon(
                        imageVector = ImageVector.vectorResource(id = R.drawable.game_intro_bg),
                        contentDescription = stringResource(
                            id = R.string.game_txt_label
                        )
                    )
                    Spacer(modifier = Modifier.height(20.dp))

                    GameLevelCard(gameMode = GameMode.Easy, onGameModeClick = onGameModeClick)
                    GameLevelCard(gameMode = GameMode.Medium, onGameModeClick = onGameModeClick)
                    GameLevelCard(gameMode = GameMode.Hard, onGameModeClick = onGameModeClick)
                }

            )
        }
    )


}

@Composable
private fun GameLevelCard(
    gameMode: GameMode,
    onGameModeClick: (GameMode) -> Unit,
    modifier: Modifier = Modifier,
) {

    val onClick = remember {
        {
            onGameModeClick(gameMode)
        }
    }

    Card(
        onClick = onClick,
        content = {
            Row(
                modifier = modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
                content = {
                    Text(
                        text = stringResource(id = gameMode.levelId).uppercase(),
                        style = MaterialTheme.typography.bodyLarge
                    )
                    Icon(
                        modifier = Modifier.size(40.dp),
                        imageVector = gameMode.levelIcon,
                        contentDescription = gameMode.levelIcon.name
                    )
                }
            )
        }
    )
}

@Preview
@Composable
private fun PrevGameIntro() {
    GameIntroScreen(onGameModeClick = {})

}