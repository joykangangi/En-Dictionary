package com.jkangangi.en_dictionary

import android.app.Application
import com.jkangangi.en_dictionary.app.di.AppModule
import com.jkangangi.en_dictionary.app.di.AppModuleImpl

class DictionaryApplication : Application() {

    companion object {
        lateinit var appModule: AppModule
        //lateinit var dictionaryDataStore: DictionaryDataStore;lateinit is another way of the di; currently lazy initialization is being used
        //lateinit var service: DictionaryService
    }

    override fun onCreate() {
        super.onCreate()
        appModule = AppModuleImpl(appContext = this)
        //dictionaryDataStore = DictionaryDataStoreImpl(appModule.dataStore)
       // service = DictionaryServiceImpl()

    }
}