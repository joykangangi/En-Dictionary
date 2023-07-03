package com.jkangangi.en_dictionary.app.data.local

import androidx.room.TypeConverter
import com.jkangangi.en_dictionary.app.data.model.Meaning
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

class RoomConverters {

    @TypeConverter
    fun meaningsToJson(meanings: List<Meaning>): String {
        return Json.encodeToString(meanings)
    }

    @TypeConverter
    fun jsonToMeaning(json: String): List<Meaning> {
       return Json.decodeFromString(json)
    }


}