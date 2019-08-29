package com.pavelprymak.openweatherwellcom

import androidx.multidex.MultiDex
import androidx.multidex.MultiDexApplication
import com.facebook.stetho.Stetho
import com.pavelprymak.openweatherwellcom.data.data
import com.pavelprymak.openweatherwellcom.presentation.presentation
import org.koin.android.ext.android.startKoin
import timber.log.Timber

class App : MultiDexApplication(){
    override fun onCreate() {
        super.onCreate()
        startKoin(applicationContext, listOf(data, presentation))
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
        //MultiDex
        MultiDex.install(this)
        //Stetho
        Stetho.initializeWithDefaults(this)
    }
}
