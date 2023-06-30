package com.jkangangi.en_dictionary.app.data.remote.dto

import com.jkangangi.en_dictionary.app.data.model.Word
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class WordDto(
    @SerialName("licence")
    val license: LicenseDto = LicenseDto(),
    @SerialName("meanings")
    val meanings: List<MeaningDto> = listOf(),
    @SerialName("phonetic")
    val phonetic: String = "",
    @SerialName("phonetics")
    val phonetics: List<PhoneticDto> = listOf(),
    @SerialName("sourceUrls")
    val sourceUrls: List<String> = listOf(),
    @SerialName("word")
    val word: String = ""
) {
    fun WordDto.toWordEntity
}