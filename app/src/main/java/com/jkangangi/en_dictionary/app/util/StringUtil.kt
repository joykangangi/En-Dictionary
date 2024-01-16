package com.jkangangi.en_dictionary.app.util

/**
 * Check if string is one word;
 */
fun String.isWord(): Boolean {
    return !(this.any { (it == ' ') || (it == '-') })
}


/**
 * (UK, US) IPA: /səkˈsɛs/ becomes /səkˈsɛs/
 * extract Phonetics from API;
 */
fun String.phonetics(): String {
    return this.substringAfter(':')
}

/**
 * scramble a word ensuring no possibilities of
 * getting the same word after a shuffle
 */

fun String.scramble(): String {
   val stringCharacters = this.toCharArray()
    stringCharacters.shuffle()
    while (String(stringCharacters) == (this)) {
        stringCharacters.shuffle()
    }

    return String(stringCharacters)
}