package com.jkangangi.en_dictionary.app.theme

import android.app.Activity
import android.os.Build
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.ColorScheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.text.font.FontFamily
import androidx.core.view.WindowCompat

@Composable
fun En_DictionaryTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    fontFamily: FontFamily = FontFamily.SansSerif,
    // Dynamic color is available on Android 12+
    dynamicColor: Boolean = true,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }

        darkTheme -> darkColorScheme
        else -> lightColorScheme
    }
    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            window.statusBarColor = colorScheme.primary.toArgb()
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = darkTheme
        }
    }
    val typography = getTypography(fontFamily)

    MaterialTheme(
        colorScheme = colorScheme.animateColor(),
        typography = typography,
        content = content
    )
}

@Composable
private fun animateColor(targetValue: Color): Color {
    return animateColorAsState(
        targetValue = targetValue,
        animationSpec = tween(durationMillis = 300),
        label = "theme"
    ).value

}

@Composable
fun ColorScheme.animateColor() = copy(
    primary = animateColor(targetValue = primary),
    onPrimary = animateColor(targetValue = onPrimary),
    error = animateColor(targetValue = error),
    background = animateColor(targetValue = background),
    onBackground = animateColor(targetValue = onBackground),
    surface = animateColor(targetValue = surface)
)