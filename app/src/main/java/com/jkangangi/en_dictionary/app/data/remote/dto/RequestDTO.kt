package com.jkangangi.en_dictionary.app.data.remote.dto


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RequestDTO(
    @SerialName("selection")
    val selection: String = "",
    @SerialName("textAfterSelection")
    val textAfterSelection: String = "",
    @SerialName("textBeforeSelection")
    val textBeforeSelection: String = ""
)

fun RequestDTO.trimRequest(): RequestDTO {
    return RequestDTO(
        selection = this.selection.trim().lowercase(),
        textAfterSelection = this.textAfterSelection.trim().lowercase(),
        textBeforeSelection = this.textBeforeSelection.trim().lowercase()
    )
}