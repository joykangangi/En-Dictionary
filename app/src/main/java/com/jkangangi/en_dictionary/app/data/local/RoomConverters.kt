package com.jkangangi.en_dictionary.app.data.local

import androidx.room.TypeConverter
import com.jkangangi.en_dictionary.app.data.model.Meaning
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

class RoomConverters {

    @TypeConverter
    fun meaningsToJson(meaning: Meaning?): String {
        return Json.encodeToString(meaning)
    }

    @TypeConverter
    fun jsonToMeaning(json: String): Meaning {
       return Json.decodeFromString(json)
    }


}