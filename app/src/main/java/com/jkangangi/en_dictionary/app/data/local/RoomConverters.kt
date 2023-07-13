package com.jkangangi.en_dictionary.app.data.local

import androidx.room.TypeConverter
import com.jkangangi.en_dictionary.app.data.remote.dto.Item
import com.jkangangi.en_dictionary.app.data.remote.dto.Pronunciation
import com.jkangangi.en_dictionary.app.data.remote.dto.WordFrequency
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

class RoomConverters {
/*
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
    }*/


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
}