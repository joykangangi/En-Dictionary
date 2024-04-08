package com.jkangangi.en_dictionary.search

import com.jkangangi.en_dictionary.app.data.remote.dto.RequestDTO

data class SearchScreenState(
    val requests: RequestDTO = RequestDTO(),
    val beforeError: Boolean = false,
    val targetError: Boolean = false,
    val afterError: Boolean = false
)