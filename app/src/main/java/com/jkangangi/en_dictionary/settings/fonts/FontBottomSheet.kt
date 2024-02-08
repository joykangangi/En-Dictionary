package com.jkangangi.en_dictionary.settings.fonts

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.RadioButton
import androidx.compose.material3.SheetState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FontBottomSheet(
    sheetState: SheetState,
    onDismissSheet: () -> Unit,
    font: AppFont,
    onFontChange: (AppFont) -> Unit,
    modifier: Modifier = Modifier,
) {

    val interactionSource = remember { MutableInteractionSource() }

    ModalBottomSheet(
        sheetState = sheetState,
        onDismissRequest = onDismissSheet,
        content = {
            Column(
                modifier = modifier.padding(12.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp),
                content = {
                    AppFont.entries.forEach { fontOption ->
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable(
                                    onClick = { onFontChange(fontOption) },
                                    interactionSource = interactionSource,
                                    indication = null
                                ),
                            verticalAlignment = Alignment.CenterVertically,
                            content = {
                                RadioButton(
                                    selected = fontOption == font,
                                    onClick = { onFontChange(fontOption) },
                                    modifier = Modifier.padding(8.dp)
                                )

                                Text(text = stringResource(id = fontOption.nameId))


                            }
                        )
                    }


                }
            )

        }
    )

}