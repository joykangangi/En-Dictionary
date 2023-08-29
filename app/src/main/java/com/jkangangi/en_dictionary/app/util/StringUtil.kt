package com.jkangangi.en_dictionary.app.util

/**
 * Check if string is one word;
 */
fun String.isWord(): Boolean {
    return !this.any { it == ' ' || it == '-' }
}


/**
 * (UK, US) IPA: /səkˈsɛs/ -> /səkˈsɛs/
 * extract Phonetics from API;
 */
fun String.phonetics(): String {
    return this.substringAfter(':')
}