package com.jkangangi.en_dictionary.game.mode.sharedwidgets

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.ContentTransform
import androidx.compose.animation.SizeTransform
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.jkangangi.en_dictionary.game.util.GameConstants.ANIM_WITH_INFO_MILLIS

private const val DRAG_LETTER = "DRAG_LETTER"

@Composable
fun ScrambledWord(
    scrambledWord: String,
    isNewWord: Boolean,
) {

    AnimatedContent(
        targetState = isNewWord,
        label = "",
        transitionSpec = {
            ContentTransform(
                targetContentEnter = fadeIn(animationSpec = tween(ANIM_WITH_INFO_MILLIS)),
                initialContentExit = fadeOut(animationSpec = tween(ANIM_WITH_INFO_MILLIS)),
                sizeTransform = SizeTransform(sizeAnimationSpec = { _, _ ->
                    tween(
                        ANIM_WITH_INFO_MILLIS
                    )
                })
            )
        },
        content = { show ->
            if (show) {
                ScrambledWordBox(scrambledWord = scrambledWord)
            }
        }
    )

}

@Composable
fun ScrambledWordBox(
    scrambledWord: String,
    modifier: Modifier = Modifier,
) {

    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically,
        content = {

            scrambledWord.forEachIndexed { index, _ ->
                    GameLetterBox(
                        input = scrambledWord,
                        letterIndex = index
                    )
            }
        }
    )

}
