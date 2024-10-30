package com.jkangangi.en_dictionary.game.util

fun formatTimeInMinAndSeconds(totalSeconds: Int): String {
    val minutes = totalSeconds / 60
    val seconds = totalSeconds % 60
    val formattedMin = minutes.toString().padStart(2,'0')
    val formattedSec = seconds.toString().padStart(2,'0')
    return "$formattedMin min:$formattedSec sec"
}