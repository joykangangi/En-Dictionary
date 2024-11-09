package com.jkangangi.en_dictionary.app.data.local.room

import androidx.room.TypeConverter
import com.jkangangi.en_dictionary.app.data.remote.dto.Item
import com.jkangangi.en_dictionary.app.data.remote.dto.Pronunciation
import com.jkangangi.en_dictionary.app.data.remote.dto.WordFrequency
import kotlinx.datetime.LocalDate
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

class RoomConverters {

    //Item Converter
    @TypeConverter
    fun itemToJson(items: List<Item>): String {
        return Json.encodeToString(items)
    }

    @TypeConverter
    fun jsonToItem(json: String): List<Item> {
        return Json.decodeFromString(json)
    }

    //Pronunciation Converter
    @TypeConverter
    fun pronunciationToJson(pronunciation: List<Pronunciation>): String {
        return Json.encodeToString(pronunciation)
    }

    @TypeConverter
    fun jsonToPronunciation(json: String): List<Pronunciation> {
        return Json.decodeFromString(json)
    }

    //WordFrequency Converter

    @TypeConverter
    fun wordFrequencyToJson(wordFrequency: List<WordFrequency>): String {
        return Json.encodeToString(wordFrequency)
    }

    @TypeConverter
    fun jsonToWordFrequency(json: String): List<WordFrequency> {
        return Json.decodeFromString(json)
    }

    //LocalDate

    @TypeConverter
    fun dateToJson(date: LocalDate): String {
        return Json.encodeToString(date)
    }

    @TypeConverter
    fun jsonToDate(json: String): LocalDate {
        return Json.decodeFromString(json)
    }
}