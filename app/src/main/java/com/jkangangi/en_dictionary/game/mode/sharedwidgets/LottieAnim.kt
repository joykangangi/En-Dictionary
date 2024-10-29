package com.jkangangi.en_dictionary.game.mode.sharedwidgets

import androidx.annotation.RawRes
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.rememberLottieComposition
import com.jkangangi.en_dictionary.R
import com.jkangangi.en_dictionary.app.theme.largePadding
import com.jkangangi.en_dictionary.app.theme.largeSpacer
import com.jkangangi.en_dictionary.app.theme.mediumSpacer
import com.jkangangi.en_dictionary.game.util.GameConstants.ANIM_WITH_NO_INFO_MILLIS
import kotlinx.coroutines.delay

/**
 * To be used when user gets wrong answer
 */
@Composable
fun AnimWithAnswer(
    @RawRes animResId: Int,
    modifier: Modifier = Modifier,
    goToNextWord: () -> Unit,
    playAgain: () -> Unit,
    header: String,
    title: String,
    description: String,
    isFinalWord: Boolean,
) {

    val lottieComposition by rememberLottieComposition(
        spec = LottieCompositionSpec.RawRes(animResId)
    )

    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center,
        content = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(largePadding()),
                verticalArrangement = Arrangement.SpaceAround,
                horizontalAlignment = Alignment.CenterHorizontally,
                content = {
                    LottieAnimation(
                        composition = lottieComposition,
                        iterations = LottieConstants.IterateForever,
                    )

                    ElevatedCard(
                        onClick = { },
                        content = {

                            Text(
                                text = header,
                                style = MaterialTheme.typography.bodyMedium
                            )

                            Spacer(modifier = Modifier.size(mediumSpacer()))

                            ScrambledWordBox(scrambledWord = title)

                            Spacer(modifier = Modifier.size(mediumSpacer()))

                            Text(
                                text = description,
                                style = MaterialTheme.typography.bodyLarge
                            )

                            Spacer(modifier = Modifier.size(largeSpacer()))

                            Button(
                                modifier = Modifier.fillMaxWidth(),
                                onClick = goToNextWord,
                                content = {
                                    Text(
                                        text = if (isFinalWord) stringResource(id = R.string.finish)
                                        else stringResource(id = R.string.next)
                                    )
                                }
                            )
                        }
                    )
                }
            )
        }
    )

}

/**
 * usage- when user gets a correct answer
 */
@Composable
fun AnimWithoutText(
    @RawRes animResId: Int,
    goToNextWord: () -> Unit,
    modifier: Modifier = Modifier,
) {

    val lottieComposition by rememberLottieComposition(
        spec = LottieCompositionSpec.RawRes(animResId)
    )

    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center,
        content = {
            LottieAnimation(
                composition = lottieComposition,
                iterations = LottieConstants.IterateForever,
            )

            LaunchedEffect(key1 = true) {
                delay(ANIM_WITH_NO_INFO_MILLIS.toLong())
                goToNextWord()
            }
        }
    )
}

//todo impl dialog for game over on its own