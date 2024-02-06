package com.jkangangi.en_dictionary.settings.fonts

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.width
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.jkangangi.en_dictionary.app.theme.En_DictionaryTheme


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FontDropDownMenu(
    font: AppFont,
    onFontChange: (AppFont) -> Unit,
    modifier: Modifier = Modifier,
) {

    var expanded by rememberSaveable {
        mutableStateOf(false)
    }


    //menu
    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = { expanded = !expanded },
        content = {

            OutlinedTextField(
                value = stringResource(id = font.nameId) ,
                onValueChange = { },
                modifier = modifier
                    .menuAnchor()
                    .width(180.dp),
                readOnly = true,
                trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
                colors = ExposedDropdownMenuDefaults.textFieldColors(),
//                TextFieldDefaults.outlinedTextFieldColors(
//                    focusedBorderColor = Color.Transparent,
//                    unfocusedBorderColor = Color.Transparent,
//                    backgroundColor = MaterialTheme.colors.surface,
//                )
            )

            ExposedDropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false },
                content = {
                    AppFont.entries.forEach { fontOption: AppFont ->
                        DropdownMenuItem(
                            text = { Text(text = stringResource(id = fontOption.nameId)) },
                            onClick = {
                                onFontChange(fontOption)
                                expanded = false
                            },
                            contentPadding = ExposedDropdownMenuDefaults.ItemContentPadding,
                        )
                    }
                }
            )
        }
    )
}

@Preview
@Composable
fun PreviewFontDropDown() {
    En_DictionaryTheme {
        FontDropDownMenu(font = AppFont.SansSerif, onFontChange = {

        })
    }

}

@Preview
@Composable
fun PreviewDropDownItems() {
    En_DictionaryTheme {
        Column {
            AppFont.entries.forEach {
                Text(text = it.name)
                Text(text = stringResource(id = it.nameId))
            }
        }
    }

}