package com.jkangangi.en_dictionary.app.theme

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.runtime.remember
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.jkangangi.en_dictionary.R

@ReadOnlyComposable
@Composable
fun largePadding() = dimensionResource(id = R.dimen.large_padding)

@ReadOnlyComposable
@Composable
fun mediumPadding() = dimensionResource(id = R.dimen.large_padding) / 2

@ReadOnlyComposable
@Composable
fun smallPadding() = dimensionResource(id = R.dimen.large_padding) / 4

/**Spacers*/
@ReadOnlyComposable
@Composable
fun largeSpacer() = dimensionResource(id = R.dimen.large_spacer)

@ReadOnlyComposable
@Composable
fun mediumSpacer() = dimensionResource(id = R.dimen.large_spacer) / 2

@ReadOnlyComposable
@Composable
fun smallSpacer() = dimensionResource(id = R.dimen.large_spacer) / 4

data class Dimensions(
    /**Images*/
    val largeImage: Dp = 100.dp,
    val mediumImage: Dp = largeImage / 2,
    val smallImage: Dp = largeImage / 4,

    /**Game Dimensions*/
    val mediumGameBox: Dp = 55.dp,
    val smallGameBox: Dp = 25.dp,
    val smallGameText: TextUnit = 12.sp,
    val mediumGameText: TextUnit = 24.sp,
)

val CompactDimens = Dimensions()

val LocalAppDimens = compositionLocalOf { CompactDimens }

@Composable
fun AppDimenUtils(
    appDimensions: Dimensions = CompactDimens,
    content: @Composable () -> Unit,
) {
    val dimens = remember {
        appDimensions
    }

    CompositionLocalProvider(value = LocalAppDimens provides appDimensions) {
        content()
    }

}