package com.jkangangi.en_dictionary.game.intro

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.jkangangi.en_dictionary.R
import com.jkangangi.en_dictionary.game.mode.model.GameMode
import com.jkangangi.en_dictionary.game.mode.sharedwidgets.GameRoundButton

private const val GRID_COLUMNS = 2

@Composable
fun GameIntroScreen(
    onGameModeClick: (GameMode) -> Unit,
    modifier: Modifier = Modifier
) {
    val colors = listOf(
        MaterialTheme.colorScheme.primary,
        MaterialTheme.colorScheme.primaryContainer,
        MaterialTheme.colorScheme.tertiary,
        MaterialTheme.colorScheme.tertiaryContainer,
        MaterialTheme.colorScheme.secondary
    )

    val onClick: (GameMode) -> Unit = remember {
        {
            onGameModeClick(it)
        }
    }

    Scaffold(
        content = { scaffoldPadding ->
            Column(
                modifier = modifier
                    .fillMaxSize()
                    .padding(scaffoldPadding)
                    .padding(12.dp),
                verticalArrangement = Arrangement.spacedBy(20.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                content = {
                    Spacer(modifier = Modifier.height(20.dp))

                    Image(
                        modifier = Modifier.size(100.dp),
                        painter = painterResource(id = R.drawable.game_intro_bg),
                        contentDescription = stringResource(
                            id = R.string.game_txt_label
                        )
                    )
                    Spacer(modifier = Modifier.height(20.dp))

                    LazyVerticalGrid(
                        columns = GridCells.Fixed(GRID_COLUMNS),
                        content = {
                            items(GameMode.entries.toTypedArray()) { mode ->
                                GameRoundButton(
                                    innerColor = colors.random(),
                                    outerColor = colors.random(),
                                    text = stringResource(id = mode.levelId),
                                    shape = CircleShape,
                                    onButtonClick = {
                                        onClick(mode)
                                    }
                                )
                            }
                        }
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