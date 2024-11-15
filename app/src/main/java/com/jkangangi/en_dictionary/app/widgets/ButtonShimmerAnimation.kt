package com.jkangangi.en_dictionary.app.widgets

import androidx.compose.animation.core.InfiniteRepeatableSpec
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush

const val BUTTON_ANIM = "BUTTON_ANIM"

@Composable
fun Modifier.buttonShimmer(
    widthOfShadowBrush: Int = 500,
    angleOfAxisY: Float = 270f,
    durationMillis: Int = 1000
): Modifier {

    return composed {
        val color =MaterialTheme.colorScheme.primary

        val shimmerColors =   listOf(
            color.copy(alpha = 0.3f),
            color.copy(alpha = 0.5f),
            color.copy(alpha = 1.0f),
            color.copy(alpha = 0.5f),
            color.copy(alpha = 0.3f),
        )

        val transition = rememberInfiniteTransition(label = BUTTON_ANIM)
        val animationSpec: InfiniteRepeatableSpec<Float> = infiniteRepeatable(
            animation = tween(durationMillis),
            repeatMode = RepeatMode.Restart
        )

        val translateAnimation = transition.animateFloat(
            initialValue = 0f,
            targetValue = (durationMillis + widthOfShadowBrush).toFloat(),
            animationSpec = animationSpec,
            label = BUTTON_ANIM
        )

        this.background(
            brush = Brush.linearGradient(
                colors = shimmerColors,
                start = Offset(
                    x = translateAnimation.value - widthOfShadowBrush,
                    y = 0.0f
                ),
                end = Offset(
                    x = translateAnimation.value,
                    y = angleOfAxisY
                )
            )
        )
    }
}