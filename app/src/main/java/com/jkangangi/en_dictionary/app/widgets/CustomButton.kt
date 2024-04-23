package com.jkangangi.en_dictionary.app.widgets

import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource

@Composable
fun CustomFilledButton(
    onBtnClicked: () -> Unit,
    buttonTextId: Int,
    modifier: Modifier = Modifier,
    isEnabled: Boolean = true,
) {
    val keyBoardController = LocalSoftwareKeyboardController.current
    Button(
        modifier = modifier,
        enabled = isEnabled,
        onClick = {
            keyBoardController?.hide()
            onBtnClicked()
        },
        content = {
            Text(
                text = stringResource(id = buttonTextId),
                style = MaterialTheme.typography.bodyMedium,
            )
        }
    )

}

@Composable
fun CustomOutlinedButton(
    onBtnClicked: () -> Unit,
    buttonTextId: Int,
    modifier: Modifier = Modifier,
) {
    OutlinedButton(
        modifier = modifier,
        onClick = onBtnClicked,
        content = {
            Text(text = stringResource(id = buttonTextId))
        }
    )

}