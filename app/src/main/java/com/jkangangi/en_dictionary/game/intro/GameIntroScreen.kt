package com.jkangangi.en_dictionary.game.intro

import androidx.compose.animation.core.InfiniteRepeatableSpec
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.jkangangi.en_dictionary.R
import com.jkangangi.en_dictionary.app.theme.dimens
import com.jkangangi.en_dictionary.app.theme.largePadding
import com.jkangangi.en_dictionary.app.theme.mediumSpacer
import com.jkangangi.en_dictionary.app.theme.smallPadding
import com.jkangangi.en_dictionary.app.theme.smallSpacer
import com.jkangangi.en_dictionary.game.mode.model.GameMode
import com.jkangangi.en_dictionary.game.mode.sharedwidgets.GameRoundButton

private const val BUTTON_ANIM = "BUTTON_ANIM"
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

    val infiniteTransition = rememberInfiniteTransition(label = BUTTON_ANIM)
    val animationSpec: InfiniteRepeatableSpec<Float> = infiniteRepeatable(
        animation = tween(1000),//todo use gloss animation
        repeatMode = RepeatMode.Reverse
    )

    val pulsingBoxSize by infiniteTransition.animateFloat(
        initialValue = MaterialTheme.dimens.largeObject.value,
        targetValue = MaterialTheme.dimens.xLObjects.value,
        animationSpec = animationSpec,
        label = BUTTON_ANIM
    )

    val pulsingTextSize by infiniteTransition.animateFloat(
        initialValue = MaterialTheme.dimens.midMediumGameTextUnit.value,
        targetValue = MaterialTheme.dimens.mediumGameText.value,
        animationSpec = animationSpec,
        label = BUTTON_ANIM
    )


    Scaffold(
        content = { scaffoldPadding ->
            Column(
                modifier = modifier
                    .fillMaxSize()
                    .padding(scaffoldPadding)
                    .padding(largePadding())
                    .verticalScroll(rememberScrollState()),
                verticalArrangement = Arrangement.spacedBy(mediumSpacer()),
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

                    Spacer(modifier = Modifier.height(smallSpacer()))
                    Text(
                        text = stringResource(id = R.string.select_level),
                        fontSize = 18.sp,
                    )

                    Spacer(modifier = Modifier.height(mediumSpacer()))

                    GameMode.entries.forEach { mode ->
                        GameRoundButton(
                            modifier = Modifier.padding(smallPadding()),
                            text = stringResource(id = mode.levelId),
                            shape = CircleShape,
                            borderStrokeWidth = 2.dp,
                            fontSize = pulsingTextSize.sp,
                            outerBoxSize = pulsingBoxSize.dp,
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


@Preview
@Composable
private fun PrevGameIntro() {
    GameIntroScreen(onGameModeClick = {})

}