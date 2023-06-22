package com.jkangangi.en_dictionary.app.data.remote.mappers

import com.jkangangi.en_dictionary.app.data.model.License
import com.jkangangi.en_dictionary.app.data.remote.dto.LicenseDto

fun LicenseDto.toLicense(): License {
    return License(
        name = name,
        url = url
    )
}