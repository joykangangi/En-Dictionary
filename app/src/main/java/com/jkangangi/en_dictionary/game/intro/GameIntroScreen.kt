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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.jkangangi.en_dictionary.R
import com.jkangangi.en_dictionary.app.theme.dimens
import com.jkangangi.en_dictionary.app.theme.padding16
import com.jkangangi.en_dictionary.app.theme.padding10
import com.jkangangi.en_dictionary.app.theme.padding4
import com.jkangangi.en_dictionary.app.theme.padding5
import com.jkangangi.en_dictionary.app.widgets.buttonShimmer
import com.jkangangi.en_dictionary.game.mode.model.GameMode
import com.jkangangi.en_dictionary.game.mode.sharedwidgets.GameRoundButton

@Composable
fun GameIntroScreen(
    onGameModeClick: (GameMode) -> Unit,
    modifier: Modifier = Modifier
) {

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
                    .padding(padding16()),
                verticalArrangement = Arrangement.spacedBy(padding10()),
                horizontalAlignment = Alignment.CenterHorizontally,
                content = {

                    Text(
                        text = stringResource(id = R.string.ready_for_game),
                        textAlign = TextAlign.Center,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold
                    )

                    Image(
                        modifier = Modifier.size(MaterialTheme.dimens.mediumObject),
                        painter = painterResource(id = R.drawable.game_intro_bg),
                        contentDescription = stringResource(
                            id = R.string.game_txt_label
                        )
                    )

                    Spacer(modifier = Modifier.height(padding5()))
                    Text(
                        text = stringResource(id = R.string.select_level),
                        fontSize = 18.sp,
                    )

                    Spacer(modifier = Modifier.height(padding10()))

                    LazyVerticalGrid(
                        columns = GridCells.Fixed(2),
                        horizontalArrangement = Arrangement.Center,
                    ) {
                        items(GameMode.entries) { mode ->
                            GameRoundButton(
                                modifier = Modifier
                                    .padding(padding4())
                                    .clip(RoundedCornerShape(50f))
                                    .buttonShimmer(),
                                text = stringResource(id = mode.levelId),
                                shape = RoundedCornerShape(50f),
                                borderStrokeWidth = 2.dp,
                                fontSize = MaterialTheme.dimens.mediumGameText,
                                outerBoxSize = MaterialTheme.dimens.xLObjects,
                                onButtonClick = {
                                    onClick(mode)
                                }
                            )

                        }
                    }
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