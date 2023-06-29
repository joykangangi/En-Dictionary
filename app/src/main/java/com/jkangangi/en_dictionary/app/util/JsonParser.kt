package com.jkangangi.en_dictionary.app.util

import java.lang.reflect.Type

interface JsonParser {

    fun <T> toObject(json: String, type: Type): T?

    fun <T> toJson(obj: T, type: Type): String?
}