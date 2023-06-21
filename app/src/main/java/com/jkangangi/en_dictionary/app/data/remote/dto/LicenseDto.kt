package com.jkangangi.en_dictionary.app.data.remote.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class LicenseDto(
    @SerialName("name")
    val name: String = "",
    @SerialName("url")
    val url: String = ""
)