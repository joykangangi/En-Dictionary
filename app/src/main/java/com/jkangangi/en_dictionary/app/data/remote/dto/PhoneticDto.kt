package com.jkangangi.en_dictionary.app.data.remote.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PhoneticDto(
    @SerialName("audio")
    val audio: String = "",
    @SerialName("licence")
    val license: LicenseDto = LicenseDto(),
    @SerialName("sourceUrl")
    val sourceUrl: String = "",
    @SerialName("text")
    val text: String = ""
)