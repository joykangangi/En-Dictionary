package com.jkangangi.en_dictionary.app.widgets

import android.content.res.Configuration
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.jkangangi.en_dictionary.R
import com.jkangangi.en_dictionary.app.data.model.DictionaryTabOptions
import com.jkangangi.en_dictionary.app.theme.largePadding
import com.jkangangi.en_dictionary.app.theme.smallPadding


@Composable
fun DynamicTabSelector(
    tabs: List<DictionaryTabOptions>,
    selectedTab: Int,
    onTabSelected: (Int) -> Unit,
    modifier: Modifier = Modifier,
    containerColor: Color = MaterialTheme.colorScheme.primaryContainer,
    tabColor: Color = MaterialTheme.colorScheme.surface,
    selectedTabColor: Color = MaterialTheme.colorScheme.primary,
    containerCornerRadius: Dp = largePadding(),
    tabCornerRadius: Dp = 12.dp,
    selectorHeight: Dp = 48.dp,
    tabHeight: Dp = 40.dp,
    spacing: Dp = smallPadding(),
    textStyle: TextStyle = TextStyle(
        color = MaterialTheme.colorScheme.onBackground.copy(0.5f),
        textAlign = TextAlign.Center,
        fontWeight = FontWeight.Medium,
        fontSize = 12.sp
    ),
    selectedTabTextStyle: TextStyle = TextStyle(
        color = selectedTabColor,
        textAlign = TextAlign.Center,
        fontWeight = FontWeight.Bold,
        fontSize = 12.sp
    ),
) {

    val interactionSource = remember {
        MutableInteractionSource()
    }

    BoxWithConstraints(
        modifier = modifier
            .clip(RoundedCornerShape(containerCornerRadius))
            .height(selectorHeight)
            .fillMaxSize()
            .background(containerColor),
        content = {

            val segmentWidth = maxWidth / tabs.size

            val boxWidth = segmentWidth - spacing * 2
            val positions = tabs.indices.map { index ->
                segmentWidth * index + (segmentWidth - boxWidth) / 2
            }
            val animatedOffsetX by animateDpAsState(
                targetValue = positions[selectedTab],
                label = ""
            )

            val containerHeight = maxHeight

            val verticalOffset = (containerHeight - tabHeight) / 2


            Row(
                modifier = Modifier.fillMaxHeight(),
                horizontalArrangement = Arrangement.spacedBy(spacing),
                verticalAlignment = Alignment.CenterVertically,
                content = {
                    tabs.forEachIndexed { index, tabName ->
                        Text(
                            text = stringResource(id = tabName.nameId),
                            style = textStyle,
                            modifier = Modifier
                                .width(segmentWidth)
                                .noRippleClickable(interactionSource) {
                                    onTabSelected(index)

                                }
                        )
                    }
                }
            )

            Box(
                modifier = Modifier
                    .offset {
                        IntOffset(
                            x = animatedOffsetX.roundToPx(),
                            y = verticalOffset.roundToPx()
                        )
                    }
                    .clip(RoundedCornerShape(tabCornerRadius))
                    .width(boxWidth)
                    .height(tabHeight)
                    .background(tabColor),
                content = {
                    Text(
                        text = stringResource(id = tabs[selectedTab].nameId),
                        modifier = Modifier.align(Alignment.Center),
                        style = selectedTabTextStyle
                    )
                }
            )
        }
    )


}


@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun PreviewDynamicTabSelector() {
    val optionTexts = listOf(
        DictionaryTabOptions(
            nameId = R.string.simple_search_btn,
            searchOptionView = { Text(text = "Joy to the world") }),
        DictionaryTabOptions(
            nameId = R.string.adv_search_btn,
            searchOptionView = { Text(text = "Shix to the world") }),
    )

    Column {
        DynamicTabSelector(
            tabs = optionTexts,
            selectedTab = 0,
            onTabSelected = { },
        )
    }
}