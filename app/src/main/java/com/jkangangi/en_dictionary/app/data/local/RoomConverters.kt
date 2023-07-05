package com.jkangangi.en_dictionary.app.data.local

import androidx.room.TypeConverter
import com.jkangangi.en_dictionary.app.data.model.Meaning
import com.jkangangi.en_dictionary.app.data.model.Phonetic
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

    @TypeConverter
    fun phoneticsToJson(phonetics: List<Phonetic>): String {
        return Json.encodeToString(phonetics)
    }

    @TypeConverter
    fun jsonToPhonetics( json: String): List<Phonetic> {
        return Json.decodeFromString(json)
    }

    @TypeConverter
    fun urlsToJson(sourceUrls: List<String>): String {
        return Json.encodeToString(sourceUrls)
    }

    @TypeConverter
    fun jsonToUrls(json: String): List<String> {
        return Json.decodeFromString(json)
    }


}