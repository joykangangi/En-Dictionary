package com.jkangangi.en_dictionary.game.mode.sharedwidgets

import androidx.annotation.DrawableRes
import androidx.annotation.RawRes
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.rememberLottieComposition
import com.jkangangi.en_dictionary.R
import com.jkangangi.en_dictionary.app.theme.largePadding
import com.jkangangi.en_dictionary.app.theme.mediumSpacer
import com.jkangangi.en_dictionary.app.theme.smallSpacer
import com.jkangangi.en_dictionary.game.util.GameConstants.ANIM_WITH_NO_INFO_MILLIS
import com.jkangangi.en_dictionary.game.util.formatTimeInMinAndSeconds
import kotlinx.coroutines.delay


@Composable
private fun DialogWithImageUtil(
    @DrawableRes imageResId: Int,
    onDismiss: () -> Unit,
    dialogColumContent: @Composable () -> Unit,
    modifier: Modifier = Modifier
) {

    Dialog(
        onDismissRequest = { onDismiss() },
        content = {

            Card(
                shape = RoundedCornerShape(largePadding()),
                content = {

                    Column(
                        modifier = modifier
                            .fillMaxWidth()
                            .padding(largePadding()),
                        verticalArrangement = Arrangement.SpaceAround,
                        horizontalAlignment = Alignment.CenterHorizontally,
                        content = {
                            Icon(
                                modifier = Modifier.size(150.dp),
                                imageVector = ImageVector.vectorResource(id = imageResId),
                                contentDescription = null
                            )

                            dialogColumContent()

                        }
                    )
                }
            )
        }
    )
}


/**
 * Dialogs with animation
 */

@Composable
fun DialogWithAnimationUtil(
    @RawRes animResId: Int,
    onDismiss: () -> Unit,
    dialogColumContent: @Composable () -> Unit,
    modifier: Modifier = Modifier
) {

    val lottieComposition by rememberLottieComposition(
        spec = LottieCompositionSpec.RawRes(animResId)
    )

    Dialog(
        onDismissRequest = { onDismiss() },
        content = {

            Card(
                shape = RoundedCornerShape(largePadding()),
                content = {

                    Column(
                        modifier = modifier
                            .fillMaxWidth()
                            .padding(largePadding()),
                        verticalArrangement = Arrangement.SpaceAround,
                        horizontalAlignment = Alignment.CenterHorizontally,
                        content = {

                            Box(
                                modifier = Modifier.size(150.dp),
                                contentAlignment = Alignment.Center,
                                content = {
                                    LottieAnimation(
                                        composition = lottieComposition,
                                        iterations = LottieConstants.IterateForever,
                                    )
                                }
                            )

                            dialogColumContent()

                        }
                    )
                }
            )
        }
    )
}

/**
 * To be used when user gets wrong answer
 * has action
 */

@Composable
fun WrongAnsDialog(
    correctWord: String,
    meaning: String,
    isFinalWord: Boolean,
    isGameOver: Boolean,
    viewGameResults: () -> Unit,
    goToNextWord: () -> Unit,
) {

    DialogWithAnimationUtil(
        animResId = R.raw.wrong_ans_anim,
        onDismiss = { if (isGameOver) viewGameResults() else goToNextWord() },
        dialogColumContent = {
            Text(
                text = stringResource(id = R.string.feedback_on_fail),
                style = MaterialTheme.typography.bodyMedium
            )

            Spacer(modifier = Modifier.size(mediumSpacer()))

            ScrambledWordBox(scrambledWord = correctWord)

            Spacer(modifier = Modifier.size(mediumSpacer()))

            Text(
                text = stringResource(id = R.string.meaning),
                textDecoration = TextDecoration.Underline
            )

            Spacer(modifier = Modifier.size(smallSpacer()))

            Text(
                text = meaning,
                style = MaterialTheme.typography.bodyMedium
            )

            Spacer(modifier = Modifier.size(mediumSpacer()))

            if (isGameOver) {
                Button(
                    modifier = Modifier.fillMaxWidth(),
                    onClick = viewGameResults,
                    content = {
                        Text(
                            text = stringResource(id = R.string.see_game_results).uppercase()
                        )
                    }
                )
            } else {

                Button(
                    modifier = Modifier.fillMaxWidth(),
                    onClick = goToNextWord,
                    content = {
                        Text(
                            text = if (isFinalWord) stringResource(id = R.string.finish).uppercase()
                            else stringResource(id = R.string.next).uppercase()
                        )
                    }
                )
            }
        }
    )
}


/**
 * usage- when user gets a correct answer
 * no action
 */
@Composable
fun CorrectAnsDialog(
    isGameOver: Boolean,
    goToNextWord: () -> Unit,
    viewGameResults: () -> Unit,
) {

    DialogWithAnimationUtil(
        animResId = R.raw.correct_ans_animation,
        onDismiss = { if (isGameOver) viewGameResults() else goToNextWord() },
        dialogColumContent = {

            LaunchedEffect(key1 = true) {
                delay(ANIM_WITH_NO_INFO_MILLIS.toLong())
                if (isGameOver) viewGameResults() else goToNextWord()
            }
        }
    )
}

/**
 * usage - view game results
 * has action
 */
@Composable
fun GameResultsDialog(
    percentageScore: Int,
    totalTimeUsed: Int,
    totalPoints: Int,
    isExcellent: Boolean,
    playAgain: () -> Unit,
) {


    DialogWithImageUtil(
        imageResId = if (isExcellent) R.drawable.game_win else R.drawable.game_lost,
        onDismiss = playAgain,
        dialogColumContent = {

            Text(text = stringResource(id = R.string.total_points, totalPoints))
            Text(
                text = stringResource(
                    id = R.string.time_taken,
                    formatTimeInMinAndSeconds(totalTimeUsed)
                )
            )
            Text(
                text = stringResource(id = R.string.percentage, percentageScore),
                style = MaterialTheme.typography.bodyLarge,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary
            )

            Spacer(modifier = Modifier.size(mediumSpacer()))

            Button(
                modifier = Modifier.fillMaxWidth(),
                onClick = playAgain,
                content = {
                    Text(
                        text = stringResource(id = R.string.finish_game).uppercase()
                    )
                }
            )

        }
    )

}