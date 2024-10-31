package com.jkangangi.en_dictionary.game.util

object GameConstants {
    const val LETTER_INCREASE = 2
    const val SCORE_INCREASE = 5
    const val SKIP_DECREASE = 4
    const val HINT_DECREASE = 3
    const val MAX_WORDS = 6
    const val MAX_SCORE = 30
    const val TOTAL_WORD_TIME = 60 //60 seconds per word
    const val THREE_QUARTER_WORD_TIME = (TOTAL_WORD_TIME / 3) //20
    const val ANIM_WITH_INFO_MILLIS = 800
    const val ANIM_WITH_NO_INFO_MILLIS = 500
    const val MAX_WORD_LENGTH = 8 //to calculate text size and box size
    const val EXCELLENT_SCORE = 60
    /**
     * Pass mark -> Points per minute
     */
    const val EASY_MODE_PASSMARK = 30
    const val MEDIUM_MODE_PASSMARK = 40
    const val HARD_MODE_PASSMARK = 50
}