package com.jkangangi.en_dictionary.app.data.model

import androidx.compose.runtime.Composable

data class DictionaryTabOptions(
    val nameId: Int,
    val searchOptionView: @Composable () -> Unit,
)