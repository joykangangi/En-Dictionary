package com.jkangangi.en_dictionary.app.data.modell


import android.os.Parcelable
import androidx.compose.runtime.Stable
import androidx.room.Entity
import com.jkangangi.en_dictionary.app.data.remo.dto.Item
import com.jkangangi.en_dictionary.app.data.remo.dto.Pronunciation
import com.jkangangi.en_dictionary.app.data.remo.dto.WordFrequency
import kotlinx.parcelize.Parcelize


@Stable
@Parcelize
data class SearchResponse(
    val items: List<Item>,
    val pronunciations: List<Pronunciation>,
    val sentence: String,
    val target: String,
    val wordFrequencies: List<WordFrequency>
) : Parcelable