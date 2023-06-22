package com.jkangangi.en_dictionary.word

import androidx.lifecycle.ViewModel
import com.jkangangi.en_dictionary.app.data.remote.network.DictionaryRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class WordViewModel @Inject constructor(
    private val wordRepository: DictionaryRepository
) : ViewModel() {


}