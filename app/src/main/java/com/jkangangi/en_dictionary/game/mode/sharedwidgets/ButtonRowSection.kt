package com.jkangangi.en_dictionary.game.mode.sharedwidgets

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.jkangangi.en_dictionary.R

@Composable
fun ButtonRowSection(
    onSkipClicked: () -> Unit,
    onNextClicked: () -> Unit,
    btnEnabled: Boolean,
    isFinalWord: Boolean,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(8.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        content = {
            OutlinedButton(
                onClick = onSkipClicked,
                // modifier = Modifier.weight(0.4f),
                content = {
                    Text(text = stringResource(id = R.string.skip_btn).uppercase())
                }
            )
            //Spacer(modifier = Modifier.width(4.dp))

            Button(
                onClick = onNextClicked,
                //modifier = Modifier.weight(0.4f),
                enabled = btnEnabled,
                content = {
                    Text(
                        text = stringResource(id = R.string.submit).uppercase()
                    )
                }
            )
        }
    )
}